# Order Management API

Sistema backend para gerenciamento de pedidos, usuários e processos internos, desenvolvido com **Java** e **Spring Boot**, seguindo boas práticas de arquitetura em camadas e foco em escalabilidade, segurança e manutenção.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Swagger / OpenAPI
- Docker
- JUnit 5
- Mockito
- Maven
- Git / GitHub

---

## 📌 Funcionalidades

### 👤 Usuários
- Cadastro de usuários
- Login com autenticação JWT
- Controle de acesso por perfil/permissão
- Atualização e remoção de usuários

### 📦 Pedidos
- Criação de pedidos
- Listagem de pedidos
- Atualização de status
- Consulta por ID
- Remoção de pedidos

### 🛠 Administração
- Gerenciamento de processos internos
- Organização modular da aplicação
- Regras de negócio centralizadas na camada Service

---

## 🧱 Arquitetura do Projeto

O projeto segue o padrão de arquitetura em camadas:

```text
controller -> service -> repository -> database
