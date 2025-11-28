# ToDoSpring — API REST com Spring Boot

Este repositório contém uma API REST simples desenvolvida em Spring Boot para gerenciamento de usuários e tarefas (To-Do). O objetivo deste README é documentar o desenvolvimento da aplicação, a arquitetura usada e as possibilidades de chamadas à API (endpoints, exemplos e instruções para execução).

**Stack**:
- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

**Visão geral do projeto**:
- `com.github.enzodelmachio.api.model` — classes de domínio (`User`, `Task`).
- `com.github.enzodelmachio.api.repository` — interfaces do Spring Data (`UserRepository`, `TaskRepository`).
- `com.github.enzodelmachio.api.service` — regras de negócio e orquestração (`UserService`, `TaskService`).
- `com.github.enzodelmachio.api.controller` — endpoints REST (`UserController`, `TaskController`).

Arquitetura simples em camadas (Controller -> Service -> Repository) para separar responsabilidades e facilitar testes.

**Modelos (resumo)**
- `User`:
  - `id` (Long) — identificador.
  - `name` (String) — nome do usuário.
  - `email` (String) — email (único).
  - `tasks` (List<Task>) — tarefas associadas.
- `Task`:
  - `id` (Long) — identificador.
  - `name` (String) — título da tarefa.
  - `description` (String) — descrição detalhada.
  - `date` (LocalDateTime) — data/hora da tarefa (formato ISO-8601, ex.: `2025-11-28T15:30:00`).
  - `user` (User) — usuário responsável (associação ManyToOne).

Configuração do banco

```
spring.datasource.url=jdbc:mysql://localhost:3306/todolist_db
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

Altere essas propriedades conforme seu ambiente (por exemplo: usar H2 em testes locais ou configurar um container MySQL).

---
**Endpoints disponíveis**

Base: `http://localhost:8080/api`

1) Usuários (`/api/users`)
- `GET /api/users` — listar todos os usuários.
- `GET /api/users/{id}` — obter usuário por id.
- `POST /api/users` — criar usuário. Body JSON:

```json
{
  "name": "Enzo",
  "email": "enzo@example.com"
}
```

- `PUT /api/users/{id}` — atualizar usuário. Body JSON igual ao POST (nome/email).
- `DELETE /api/users/{id}` — deletar usuário (retorna 204 se deletado).

2) Tarefas (`/api/tasks`)
- `GET /api/tasks` — listar todas as tasks.
- `GET /api/tasks/{id}` — obter task por id.
- `GET /api/tasks/user/{userId}` — listar tasks FUTURAS (com `date` após agora) de um usuário.
- `POST /api/tasks/user/{userId}` — criar nova task associada ao usuário `{userId}`. Body JSON exemplo:

```json
{
  "name": "Comprar leite",
  "description": "Levar na volta para casa",
  "date": "2025-11-28T18:00:00"
}
```

Observação: o `userId` é passado na URL; o objeto `user` não precisa (nem deve) ser enviado no body ao criar.

- `PUT /api/tasks/{id}` — atualizar task (envie `name`, `description`, `date`).
- `DELETE /api/tasks/{id}` — deletar task.

---
**Exemplos de chamadas**

Recomendo usar `Invoke-RestMethod` no PowerShell (evita conflitos com alias `curl`). Exemplos em PowerShell:

- Criar usuário:

```powershell
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/users' -ContentType 'application/json' -Body ('{"name":"Enzo","email":"enzo@example.com"}')
```

- Criar task para o usuário com id 1:

```powershell
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/tasks/user/1' -ContentType 'application/json' -Body ('{"name":"Comprar leite","description":"Supermercado","date":"2025-11-28T18:00:00"}')
```

- Buscar tasks futuras do usuário 1 (GET):

```powershell
Invoke-RestMethod -Method Get -Uri 'http://localhost:8080/api/tasks/user/1'
```

Se preferir `curl.exe` (linha de comando tradicional):

```powershell
curl.exe -X POST "http://localhost:8080/api/users" -H "Content-Type: application/json" -d '{"name":"Enzo","email":"enzo@example.com"}'
```

---
**Comportamento das camadas**
- `Controller` — mapeia as rotas REST e transforma requisições HTTP em chamadas aos serviços.
- `Service` — contém lógica de negócio. Ex.: `TaskService.save(userId, task)` busca o `User` pelo `userId` e associa antes de salvar; `findFutureTasksByUserId` usa `LocalDateTime.now()` para filtrar apenas tarefas futuras.
- `Repository` — interfaces `CrudRepository` com métodos prontos; `TaskRepository` define `findByUserIdAndDateAfter(Long userId, LocalDateTime date)` para filtrar por usuário e data.

---

### To-Do List Spring

- Scoped in SpringBoot UC.