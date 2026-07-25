# Spring AI MCP Server with H2 Database

A sample **Spring AI Model Context Protocol (MCP) Server** built with **Spring Boot 3.5** and **Spring AI 1.0.0-M8**. The server exposes database operations as MCP tools using the `@Tool` annotation and an in-memory H2 database.

This project demonstrates how to:

- Build an MCP Server using Spring AI
- Expose Java methods as MCP Tools
- Query a relational database using Spring JDBC/JPA
- Test the MCP server using any MCP-compatible client
- Use Server-Sent Events (SSE) transport

---

## Features

- Spring Boot 3.5.3
- Spring AI MCP Server
- H2 In-Memory Database
- Spring JDBC
- Spring Data JPA
- Tool registration using `ToolCallbacks`
- Server-Sent Events (SSE) transport
- JSON-RPC compliant MCP endpoints

---

## Technology Stack

| Technology | Version |
|------------|----------|
| Java | 21 |
| Spring Boot | 3.5.3 |
| Spring AI | 1.0.0-M8 |
| H2 Database | Latest |
| Maven | 3.x |

---

## Project Structure

```
src
├── main
│   ├── java
│   │   └── org.mcp
│   │       ├── McpServerApplication.java
│   │       ├── DataAnalyticsTools.java
│   │       ├── UserActivitySummary.java
│   │       ├── UsersSummary.java
│   │       └── ...
│   └── resources
│       ├── application.yml
│       ├── schema.sql
│       └── data.sql
```

---

## Available MCP Tools

### 1. Get User Activity Summary

Returns the total number of activities and accumulated score for a user within a date range.

**Tool Name**

```
getUserActivity
```

**Parameters**

| Name | Type |
|------|------|
| userId | String |
| startDate | String |
| endDate | String |

---

### 2. Get All User Activities

Returns every activity stored in the database.

**Tool Name**

```
getUserAllActivity
```

---

### 3. Get User Activities By User ID

Returns all activity records for a specific user.

**Tool Name**

```
getUserActivityById
```

**Parameters**

| Name | Type |
|------|------|
| userId | String |

---

### 4. Get User By ID

Returns a user's profile.

**Tool Name**

```
getUsersById
```

**Parameters**

| Name | Type |
|------|------|
| userId | String |

---

### 5. Get All Users

Returns every user stored in the database.

**Tool Name**

```
getUsers
```

---

### 6. Get Current Time

Returns the current server time.

**Tool Name**

```
getCurrentTime
```

**Parameters**

| Name | Type |
|------|------|
| format | String |

Example:

```
yyyy-MM-dd HH:mm:ss
```

---

## Running the Application

Clone the repository

```bash
git clone https://github.com/<your-username>/<repository>.git
```

Navigate to the project

```bash
cd mcp-server
```

Run the application

```bash
mvn spring-boot:run
```

The server starts on

```
http://localhost:8081
```

---

## MCP Endpoints

### Open SSE Connection

```
GET /sse
```

Example

```
http://localhost:8081/sse
```

The server returns an MCP session endpoint similar to

```
/mcp/message?sessionId=<session-id>
```

Keep the SSE connection open.

---

### List Available Tools

POST

```
/mcp/message?sessionId=<session-id>
```

Body

```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "tools/list",
  "params": {}
}
```

---

### Call a Tool

```json
{
  "jsonrpc": "2.0",
  "id": 2,
  "method": "tools/call",
  "params": {
    "name": "getUserActivity",
    "arguments": {
      "userId": "123",
      "startDate": "2026-07-01",
      "endDate": "2026-07-31"
    }
  }
}
```

---

## Database

The project uses an in-memory H2 database.

Datasource

```
jdbc:h2:mem:testdb
```

SQL initialization is enabled via

```yaml
spring:
  sql:
    init:
      mode: always
```

Example tables

### users

| Column | Type |
|---------|------|
| user_id | VARCHAR |
| name | VARCHAR |
| age | INT |

---

### user_activity

| Column | Type |
|---------|------|
| user_id | VARCHAR |
| score | INT |
| created_date | DATE |

---

## Registering MCP Tools

Spring AI automatically discovers methods annotated with `@Tool`.

```java
@Bean
public List<ToolCallback> toolCallbacks(DataAnalyticsTools tools) {
    return List.of(ToolCallbacks.from(tools));
}
```

Example tool

```java
@Tool(description = "Get user activity summary")
public UserActivitySummary getUserActivity(
        String userId,
        String startDate,
        String endDate) {
    ...
}
```

---

## Configuration

Example `application.yml`

```yaml
server:
  port: 8081

spring:
  ai:
    mcp:
      server:
        enabled: true
        name: mcp-server

  datasource:
    url: jdbc:h2:mem:testdb

  sql:
    init:
      mode: always
```

---

## Logging

Enable detailed MCP logs

```yaml
logging:
  level:
    org.springframework.ai: TRACE
    org.springframework.ai.mcp: TRACE
    io.modelcontextprotocol: TRACE
```

---

## Testing

The server can be tested using:

- Postman
<img width="812" height="615" alt="image" src="https://github.com/user-attachments/assets/9d75647e-726d-45e0-b018-19e3e2e2358c" />
<img width="1887" height="870" alt="image" src="https://github.com/user-attachments/assets/73c6d880-6d5f-462f-a7c5-1fb502144c0c" />

- Custom MCP Clients (for a sample SpringAI MCP Client, please refer to this repo: https://github.com/kgalal88/SpringAI-MCP-Client/)
  
- Claude Desktop
<img width="1174" height="646" alt="image" src="https://github.com/user-attachments/assets/64825449-61d4-4527-b883-69181a04394e" />
<img width="1106" height="968" alt="image" src="https://github.com/user-attachments/assets/0bc48d7f-11ac-445f-97be-1adaccefe49a" />
<img width="1106" height="979" alt="image" src="https://github.com/user-attachments/assets/5677a336-61b5-4d24-a78b-48025d520076" />

- Cursor
  
- VS Code MCP Extensions

---

## Future Enhancements

- PostgreSQL/MySQL support
- Authentication & Authorization
- Pagination
- Tool result streaming
- Docker support
- Kubernetes deployment
- OpenTelemetry integration

---

## License

This project is licensed under the MIT License.

---

## Contributing

Contributions, issues, and feature requests are welcome.

If you find this project useful, consider giving it a ⭐ on GitHub.
