const GRAPHQL_URL = "http://localhost:8080/graphql";
let createOpen = false;
let darkMode = false;

function toggleTheme() {
    darkMode = !darkMode;
    document.documentElement.dataset.theme = darkMode ? "dark" : "";
    document.getElementById("themeIcon").className = darkMode
        ? "ph ph-sun"
        : "ph ph-moon";
}

function toggleCreate() {
    createOpen = !createOpen;
    document.getElementById("createBody").className =
        "create-body" + (createOpen ? " open" : "");
    document.getElementById("chevron").className =
        "chevron" + (createOpen ? " open" : "");
}

function toast(msg, type) {
    type = type || "ok";
    var area = document.getElementById("toastArea");
    var el = document.createElement("div");
    el.className = "toast " + type;
    var icon = type === "ok" ? "ph-check-circle" : "ph-warning-circle";
    el.innerHTML = '<i class="ph ' + icon + '"></i><span>' + msg + "</span>";
    area.appendChild(el);
    requestAnimationFrame(function () {
        requestAnimationFrame(function () {
            el.classList.add("show");
        });
    });
    setTimeout(function () {
        el.classList.remove("show");
        setTimeout(function () {
            el.remove();
        }, 300);
    }, 3000);
}

function pinHeight(seed) {
    var heights = [150, 190, 220, 170, 200, 240, 160, 210];
    var n = 0;
    for (var i = 0; i < seed.length; i++) n += seed.charCodeAt(i);
    return heights[n % heights.length];
}

function renderSkeletons() {
    var heights = [190, 150, 220, 170, 200, 160, 210, 180];
    var html = '<div class="skel-feed">';
    heights.forEach(function (h) {
        html +=
            '<div class="skel-card">' +
            '<div class="skel-img" style="height:' +
            h +
            'px"></div>' +
            '<div class="skel-body">' +
            '<div class="skel-line" style="width:68%"></div>' +
            '<div class="skel-line" style="width:38%"></div>' +
            "</div>" +
            "</div>";
    });
    html += "</div>";
    return html;
}

function renderFeed(pins) {
    if (!pins || !pins.length) {
        return (
            '<div class="empty">' +
            '<i class="ph ph-push-pin"></i>' +
            "<p>Nenhum pin ainda. Crie o primeiro.</p>" +
            "</div>"
        );
    }
    var html = '<div class="feed">';
    pins.forEach(function (p) {
        var h = pinHeight(p.imageUrl || p.id || p.title || "pin");
        var img = p.imageUrl
            ? '<img src="' +
              p.imageUrl +
              '" alt="' +
              p.title +
              '" style="height:' +
              h +
              'px" loading="lazy" ' +
              "onerror=\"this.outerHTML='<div class=pin-ph style=height:" +
              h +
              "px><i class=ph.ph-image-broken></i></div>'\">"
            : '<div class="pin-ph" style="height:' +
              h +
              'px"><i class="ph ph-image"></i></div>';
        html +=
            '<div class="pin">' +
            img +
            '<div class="pin-body">' +
            '<p class="pin-title">' +
            p.title +
            "</p>" +
            '<span class="pin-likes"><i class="ph ph-heart"></i>' +
            p.likesCount +
            "</span>" +
            "</div>" +
            "</div>";
    });
    html += "</div>";
    return html;
}

function loadFeed() {
    var c = document.getElementById("feedContainer");
    c.innerHTML = renderSkeletons();
    fetch(GRAPHQL_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            query: "query { mainFeed { id title imageUrl likesCount } }",
        }),
    })
        .then(function (r) {
            return r.json();
        })
        .then(function (d) {
            c.innerHTML = renderFeed(d.data && d.data.mainFeed);
        })
        .catch(function () {
            c.innerHTML =
                '<div class="empty">' +
                '<i class="ph ph-wifi-slash"></i>' +
                "<p>Não foi possível conectar ao servidor GraphQL.</p>" +
                "</div>";
        });
}

function criarPin() {
    var title = document.getElementById("titleInput").value.trim();
    var desc = document.getElementById("descInput").value.trim();
    var url = document.getElementById("urlInput").value.trim();

    if (title.length < 3) {
        toast("Título precisa de pelo menos 3 letras.", "err");
        return;
    }

    var mutation =
        'mutation { createPin(data: { title: "' +
        title +
        '", description: "' +
        desc +
        '", imageUrl: "' +
        url +
        '" }) { id title } }';
    fetch(GRAPHQL_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ query: mutation }),
    })
        .then(function (r) {
            return r.json();
        })
        .then(function (d) {
            if (d.errors) {
                toast(d.errors[0].message, "err");
                return;
            }
            toast("Pin criado.");
            ["titleInput", "descInput", "urlInput"].forEach(function (id) {
                document.getElementById(id).value = "";
            });
            toggleCreate();
            loadFeed();
        })
        .catch(function () {
            toast("Erro ao criar pin.", "err");
        });
}

loadFeed();
