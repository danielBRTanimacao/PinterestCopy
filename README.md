# PinStack API 📌

O **PinStack** é uma API de backend performática, escalável e moderna construída para simular as principais funcionalidades do Pinterest. O projeto utiliza uma arquitetura baseada em gráficos (GraphQL) para otimização de requisições, banco de dados NoSQL para persistência flexível, validações robustas na camada de entrada e cache em memória para garantir baixa latência no feed principal.

---

## 🛠️ Tecnologias Utilizadas

O projeto foi desenhado utilizando o que há de mais moderno no ecossistema Java e engenharia de software:

- **Java 21**: Utilização de recursos modernos da linguagem, como _Java Records_ para a criação de DTOs (Data Transfer Objects) imutáveis e limpos.
- **Spring Boot 3.x**: Framework base para gerenciamento de injeção de dependências, segurança e inicialização do projeto.
- **Spring GraphQL**: Camada de API que substitui o REST tradicional, permitindo que o cliente (front-end) requisite exatamente os campos que deseja, evitando _overfetching_ e reduzindo o payload da rede.
- **MongoDB**: Banco de dados NoSQL orientado a documentos, ideal para armazenar entidades flexíveis e escaláveis como as postagens (Pins).
- **Spring Data MongoDB & Auditing**: Abstração de repositórios para comunicação com o banco e preenchimento automático de metadados como datas de criação (`createdAt`).
- **Jakarta Bean Validation**: Mecanismo de proteção _Fail-Fast_ que valida as regras de negócio dos dados recebidos (ex: tamanho de título, URLs válidas) antes de processar a requisição.
- **Spring Data Redis**: Camada de cache distribuído em memória para otimização extrema de performance nas consultas mais quentes do sistema (Feed Principal).
- **Docker & Docker Compose**: Ferramentas de containerização para isolar e rodar o MongoDB e o Redis localmente de forma idêntica ao ambiente de produção.

---

## 📐 Arquitetura de Tratamento de Erros

A aplicação implementa um padrão unificado de respostas de erro para o GraphQL utilizando as anotações `@ControllerAdvice` e `@GraphQlExceptionHandler`. Isso garante que exceções internas do Java sejam convertidas em respostas semânticas padronizadas pelo protocolo GraphQL:

- `NotFoundException` ➡️ Mapeada para o tipo `NOT_FOUND`.
- `IllegalArgumentException` e falhas de validação ➡️ Mapeadas para o tipo `BAD_REQUEST`.
- Erros inesperados de infraestrutura ➡️ Mapeados para o tipo `INTERNAL_ERROR`.

Essa abordagem garante previsibilidade para aplicações consumidoras da API e facilita a observabilidade durante o desenvolvimento e manutenção.

---

## 🏗️ Arquitetura da Aplicação

O projeto segue uma arquitetura em camadas visando separação de responsabilidades, escalabilidade e facilidade de manutenção.

```text
GraphQL Layer
     │
     ▼
Controllers / Resolvers
     │
     ▼
Services
     │
     ▼
Repositories
     │
 ┌───┴────────────┐
 ▼                ▼
MongoDB         Redis
```

### Principais Componentes

#### GraphQL Schema First

A API utiliza a abordagem **Schema First**, onde todas as operações são definidas previamente no arquivo:

```text
src/main/resources/graphql/schema.graphqls
```

O schema atua como contrato entre frontend e backend, facilitando versionamento e evolução da API.

#### DTOs com Java Records

Exemplo:

```java
public record RequestPinDTO(
        String title,
        String description,
        String imageUrl
) {}
```

Os Records oferecem:

- Imutabilidade.
- Menos código boilerplate.
- Melhor legibilidade.
- Performance otimizada.

#### Mongo Auditing

O projeto utiliza:

```java
@EnableMongoAuditing
```

para preenchimento automático do campo:

```java
createdAt
```

em todas as entidades persistidas.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos

- Java 21
- Maven 3.9+
- Docker
- Docker Compose

---

## 1. Subir Apenas a Infraestrutura

O projeto disponibiliza um compose dedicado para os bancos.

### Serviços

- MongoDB
- Redis

Execute:

```bash
docker compose -f docker-compose.db.yml up -d
```

Verifique os containers:

```bash
docker ps
```

---

## 2. Executar a Aplicação Localmente

Após subir MongoDB e Redis:

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

---

## 3. Acessar o GraphiQL

O projeto disponibiliza uma interface gráfica para testes GraphQL.

Acesse:

```text
http://localhost:8080/graphiql
```

Exemplo de Query:

```graphql
query {
    pins {
        id
        title
        imageUrl
        createdAt
    }
}
```

Exemplo de Mutation:

```graphql
mutation {
    createPin(
        request: {
            title: "Minha Foto"
            description: "Descrição da imagem"
            imageUrl: "https://site.com/imagem.jpg"
        }
    ) {
        id
        title
    }
}
```

---

## 🐳 Executando Tudo com Docker Compose

O projeto também disponibiliza um ambiente completo contendo:

- Aplicação Spring Boot
- MongoDB
- Redis

Execute:

```bash
docker compose up --build -d
```

Serviços iniciados:

```text
app    -> Spring Boot
db     -> MongoDB
cache  -> Redis
```

Logs da aplicação:

```bash
docker compose logs -f app
```

Parar ambiente:

```bash
docker compose down
```

---

## 🐳 Dockerfile Multi-Stage

A aplicação utiliza uma estratégia de build otimizada para reduzir o tamanho da imagem final.

### Stage 1 — Build

Imagem:

```text
maven:3.9.16-amazoncorretto-25-alpine
```

Responsável por:

```bash
mvn clean package -DskipTests
```

Gerando o artefato final da aplicação.

### Stage 2 — Runtime

Imagem:

```text
eclipse-temurin:26.0.1_8-jre-ubi10-minimal
```

Responsável por:

- Copiar o JAR gerado.
- Expor a porta 8080.
- Executar a aplicação.

Essa abordagem reduz significativamente o tamanho da imagem final e melhora a segurança do ambiente de execução.

---

## ⚡ Cache com Redis

O Redis é utilizado como camada de cache para consultas frequentemente acessadas, reduzindo a carga sobre o MongoDB e melhorando o tempo de resposta da API.

Benefícios:

- Menor latência.
- Menor consumo de recursos do banco principal.
- Melhor experiência para o usuário final.
- Maior escalabilidade horizontal.

---

## 🔍 Validações

As entradas da API são protegidas utilizando **Jakarta Bean Validation**.

Exemplos:

- Campos obrigatórios.
- Tamanho mínimo e máximo.
- URLs válidas.
- Dados inválidos bloqueados antes da execução das regras de negócio.

Essa estratégia segue o conceito **Fail Fast**, interrompendo requisições inconsistentes o mais cedo possível.

---

## 📂 Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   ├── controllers
│   │   ├── services
│   │   ├── repositories
│   │   ├── entities
│   │   ├── DTOs
│   │   ├── config
│   │   └── exceptions
│   │
│   └── resources
│       ├── graphql
│       │   └── schema.graphqls
│       └── application.yml
│
└── test
```

---

## 🎯 Objetivos do Projeto

O PinStack foi criado com foco em:

- Arquiteturas modernas baseadas em GraphQL.
- Aplicação de boas práticas do ecossistema Spring.
- Persistência NoSQL com MongoDB.
- Cache distribuído utilizando Redis.
- Containerização completa com Docker.
- Código limpo, legível e escalável.

---

## 🛣️ Próximos Passos

### Redis Cache Completo

- Implementar cache das consultas do feed principal.
- Estratégias de invalidação automática.
- Cache distribuído para ambientes escaláveis.

### Testes Automatizados

Implementar uma suíte robusta de testes seguindo TDD:

- Testes unitários.
- Testes de integração.
- Testes GraphQL.
- Testcontainers para MongoDB e Redis.
- Cobertura automatizada em pipeline CI/CD.

### Evoluções Futuras

- Sistema de usuários.
- Curtidas e comentários.
- Sistema de seguidores.
- Upload de imagens.
- Feed personalizado.
- Observabilidade com métricas e tracing.
- Pipeline CI/CD automatizado.

---

## 👨‍💻 Autor

Desenvolvido por **Daniel Tenório**.

Projeto criado com foco em estudo de arquitetura moderna, GraphQL, MongoDB, Redis, Docker e boas práticas do ecossistema Spring Boot.
