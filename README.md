# Algebraic Equation Solver — Spring Boot

A Spring Boot REST API that stores, parses, and evaluates algebraic equations using a postfix expression tree.  
Equations are submitted in infix notation, then parsed into a tree structure for storage and evaluation with variable substitution.

---

## Purpose

- Accept algebraic equations in infix form (e.g. `3 * x + 2 * y - z`).
- Parse them into a postfix expression tree.
- Store both the original infix string and the parsed expression tree in memory.
- Allow users to retrieve stored equations.
- Allow users to evaluate an equation by providing variable values.
- All operations use in-memory storage — no external database.

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Web (REST APIs)
- Lombok (to reduce boilerplate)
- JUnit 5 (unit tests)

---

## Project Structure

```
src/main/java/com/algebra/assignment/algebraic_equation_solver
├── controller/      # REST endpoints (EquationController)
├── service/         # Core business logic (EquationService)
├── repository/      # In-memory storage (EquationRepository)
├── utility/         # Parser and tree builder (EquationParser)
├── model/           # Node, ExpressionTree, Equation classes
├── dto/             # Request and Response DTOs
├── exception/       # Global exception handler
```

---

## Setup Instructions

### 1. Clone the project

```bash
git clone <YOUR_REPO_URL>
cd algebraic_equation_solver
```

### 2. Build the project

Using Gradle:

```bash
./gradlew build
```

Using Maven:

```bash
mvn clean install
```

### 3. Run the application

Using Gradle:

```bash
./gradlew bootRun
```

Using Maven:

```bash
mvn spring-boot:run
```

---

## Base URL

```
http://localhost:8080/api/equations
```

---

## API Endpoints

### POST `/api/equations/store`

Store a new algebraic equation

**Request Body:**
```json
{
  "equation": "3 * x + 2 * y - z"
}
```

**Response:**
```json
{
  "message": "Equation stored successfully",
  "equationId": "1"
}
```

---

### GET `/api/equations`

Retrieve all stored equations

**Response:**
```json
[
  {
    "equationId": "1",
    "equation": "3 * x + 2 * y - z"
  }
]
```

---

### POST `/api/equations/{id}/evaluate`

Evaluate an equation by ID

**Request Body:**
```json
{
  "variables": {
    "x": 2,
    "y": 3,
    "z": 1
  }
}
```

**Response:**
```json
{
  "equationId": "1",
  "result": 11.0,
  "variables": {
    "x": 2,
    "y": 3,
    "z": 1
  }
}
```

---

## How to Test with Postman

1. **Store an equation**

   - POST to `/api/equations/store`
   - Body: `{ "equation": "3 * x + 2 * y - z" }`

2. **List stored equations**

   - GET `/api/equations`

3. **Evaluate an equation**

   - POST `/api/equations/{id}/evaluate`
   - Body: `{ "variables": { "x": 2, "y": 3, "z": 1 } }`

---

## Running Unit Tests

To run all unit tests:

```bash
./gradlew test
```

or

```bash
mvn test
```

**Tests include:**

- Storing valid equations.
- Retrieving multiple equations.
- Evaluating correct results.
- Edge cases: invalid syntax, missing operators, missing variables.

---

## Notes

- Equations are stored in-memory — restarting the app clears them.
- The system uses a postfix expression tree internally — but keeps the original infix string for display.

---

