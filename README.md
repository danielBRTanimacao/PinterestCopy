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
- **Docker & Docker Compose**: Ferramentas de containerização para isolar e rodar o MongoDB e o Redis localmente de forma idêntica ao ambiente de produção, configurados para rodar puramente em memória RAM durante o desenvolvimento.

---

## 📐 Arquitetura de Tratamento de Erros

A aplicação implementa um padrão unificado de respostas de erro para o GraphQL utilizando as anotações `@ControllerAdvice` e `@GraphQlExceptionHandler`. Isso garante que exceções internas do Java sejam convertidas em respostas semânticas padronizadas pelo protocolo GraphQL:

- `NotFoundException` ➡️ Mapeada para o tipo `NOT_FOUND`.
- `IllegalArgumentException` & Falhas de Validação (`WebExchangeBindException`) ➡️ Mapeadas para o tipo `BAD_REQUEST`.
- Erros inesperados de infraestrutura ➡️ Mapeados para o tipo `INTERNAL_ERROR`.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos

- Java 21 instalado.
- Docker e Docker Compose instalados.
- Maven instalado (ou uso do `mvnw`).

### 1. Subir a Infraestrutura (MongoDB & Redis)

Na raiz do projeto, onde se encontra o arquivo `docker-compose.yml`, execute o comando para iniciar os bancos em segundo plano:

```bash
docker compose up -d
```
