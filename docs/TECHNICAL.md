# 📖 Documentação Técnica do PManager

Este documento contém as instruções técnicas detalhadas para configuração, build e uso da API do PManager.

## 📋 Sumário

-   [Pré-requisitos](#pré-requisitos)
-   [Configuração](#configuração)
-   [Build e Execução](#build-e-execução)
-   [Endpoints da API](#endpoints-da-api)
-   [Exemplos do Postman](#exemplos-do-postman)

## ⚙️ Pré-requisitos

-   Java 21 (JDK 21)
-   Maven (o projeto inclui `mvnw`/`mvnw.cmd` — wrapper)
-   MySQL rodando e acessível
-   MongoDB (para armazenamento de ApiKeys)

## 🔧 Configuração

### Banco de Dados

1. Criar o banco MySQL:

```powershell
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS pmanagerdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

2. Configurar `application.yml`:

-   Copie o arquivo `application.yml.example` para `application.yml`
-   Atualize as credenciais conforme seu ambiente

Exemplo do `application.yml`:

```yaml
server:
    port: 8080

spring:
    datasource:
        url: jdbc:mysql://localhost:3306/pmanagerdb
        driverClassName: com.mysql.cj.jdbc.Driver
        username: root
        password: your_db_password
    jpa:
        show-sql: true
        hibernate:
            ddl-auto: update
        properties:
            hibernate:
                dialect: org.hibernate.dialect.MySQL8Dialect

app:
    general:
        pageSize: 3
    security:
        masterApiKey: thekey
        expirationDays: 2
```

## 🚀 Build e Execução

### Build do Projeto

```powershell
# Na raiz do projeto, execute:
./mvnw.cmd clean package
```

### Executar a Aplicação

```powershell
# Na raiz do projeto, execute:
./mvnw.cmd spring-boot:run
```

## 🌐 Endpoints da API

### 1. Projetos

-   `POST /projects` - Criar projeto
-   `GET /projects/{id}` - Detalhes do projeto
-   `PUT /projects/{id}` - Atualizar projeto
-   `DELETE /projects/{id}` - Remover projeto

### 2. Membros

-   `POST /members` - Criar membro
-   `GET /members/{id}` - Detalhes do membro
-   `PUT /members/{id}` - Atualizar membro
-   `DELETE /members/{id}` - Remover membro

### 3. Tarefas

-   `POST /tasks` - Criar tarefa
-   `GET /tasks/{id}` - Detalhes da tarefa
-   `PUT /tasks/{id}` - Atualizar tarefa
-   `DELETE /tasks/{id}` - Remover tarefa

### 4. API Keys

-   `POST /apiKeys` - Gerar nova API Key
-   `PUT /apiKeys/{id}/revoke` - Revogar API Key

## 📱 Exemplos do Postman

### Criar Projeto

```http
POST http://localhost:8080/projects
Content-Type: application/json
X-API-Key: sua-api-key-aqui

{
    "name": "Project Y",
    "description": "description XYZ",
    "initialDate": "2025-04-10",
    "finalDate": "2026-04-10",
    "memberIds": ["{{member_id}}"]
}
```

### Criar Membro

```http
POST http://localhost:8080/members
Content-Type: application/json
X-API-Key: sua-api-key-aqui

{
    "name": "Isaac",
    "email": "isaac@email.com",
    "projectId":["{{project_id}}"]
}
```

### Criar Tarefa

```http
POST http://localhost:8080/tasks
Content-Type: application/json
X-API-Key: sua-api-key-aqui

{
    "title": "Task A",
    "description": "description XYZ",
    "numberOfDays": 7,
    "projectId": "{{project_id}}",
    "memberId": "{{member_id}}"
}
```

### Gerar API Key

```http
POST http://localhost:8080/apiKeys
Content-Type: application/json

# Não requer corpo na requisição
```

### Revogar API Key

```http
PUT http://localhost:8080/apiKeys/{id}/revoke
X-API-Key: sua-api-key-aqui

# Não requer corpo na requisição
```

### Atualizar Projeto

```http
PUT http://localhost:8080/projects/{id}
Content-Type: application/json
X-API-Key: sua-api-key-aqui

{
    "name": "Project Y",
    "description": "description ABC",
    "initialDate": "2025-04-10",
    "finalDate": "2026-04-10",
    "status": "IN_PROGRESS",
    "memberIds": ["{{member_id}}"]
}
```

### Atualizar Membro

```http
PUT http://localhost:8080/members/{id}
Content-Type: application/json
X-API-Key: sua-api-key-aqui

{
    "name": "Luiza",
    "email": "luiza@email.com",
    "projectId":["{{project_id}}"]
}
```

### Atualizar Tarefa

```http
PUT http://localhost:8080/tasks/{id}
Content-Type: application/json
X-API-Key: sua-api-key-aqui

{
    "title": "Task X",
    "description": "description ABC",
    "numberOfDays": 5,
    "status": "IN_PROGRESS",
    "projectId": "{{project_id}}",
    "memberId": "{{member_id}}"
}
```

## 📌 Observações Importantes

1. **Autenticação**

    - Todas as requisições precisam incluir o header `x-api-key` value:`thekey`
    - Gere uma API Key antes de começar a usar a API
    - API Keys podem ser revogadas quando necessário

2. **IDs**

    - Todos os IDs são strings no formato UUID
    - Exemplo: "123e4567-e89b-12d3-a456-426614174000"

3. **Status**

    - Projetos: "PEDDING", "IN_PROGRESS", "FINISHED"
    - Tarefas: "PEDDING", "IN_PROGRESS", "FINISHED"

4. **Respostas**
    - Criação (POST): 201 Created com Location header
    - Consulta (GET): 200 OK
    - Remoção (DELETE): 204 No Content
    - Erro: 400 Bad Request ou 404 Not Found
