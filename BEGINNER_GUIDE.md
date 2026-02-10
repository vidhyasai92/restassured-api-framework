# 🎓 REST API Testing - Complete Beginner's Guide

## 📚 What is an API?

**API = Application Programming Interface**

Think of it like a waiter in a restaurant:
- You (client) tell the waiter what you want
- Waiter takes your order to the kitchen (server)
- Kitchen prepares food
- Waiter brings food back to you

**In technical terms:**
- Your app sends a **REQUEST** to the server
- Server processes it
- Server sends back a **RESPONSE**

---

## 🌐 What is REST API?

**REST = Representational State Transfer**

It's a way for applications to talk to each other over the internet using HTTP.

**Example:** When you use Instagram:
- You click "Like" → App sends POST request to server
- Server saves your like
- Server sends response "Like saved successfully"

---

## 🔧 HTTP Methods (CRUD Operations)

| Method | Purpose | Example |
|--------|---------|---------|
| **GET** | Read/Fetch data | Get user details |
| **POST** | Create new data | Create new user |
| **PUT** | Update existing data | Update user info |
| **DELETE** | Delete data | Delete user |

---

## 📦 What We're Testing

We're using **ReqRes.in** - a free fake API for practice.

**Base URL:** `https://reqres.in/api`

**Endpoints:**
- `POST /users` - Create user
- `GET /users/{id}` - Get user by ID
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user
- `GET /users?page=2` - Get list of users

---

## 🏗️ Framework Structure Explained

### 1️⃣ **payload/User.java** (POJO Class)

**What is POJO?** Plain Old Java Object - just a simple class with variables and getters/setters.

```java
public class User {
    private String name;
    private String job;
    
    // Getters and Setters
}
```

**Why?** This represents the JSON data we send/receive.

**Example JSON:**
```json
{
  "name": "John Doe",
  "job": "Software Engineer"
}
```

This JSON automatically converts to User object and vice versa!

---

### 2️⃣ **endpoints/UserEndpoints.java** (API Methods)

This class contains all API calls.

**Example - Create User:**
```java
public static Response createUser(User payload) {
    Response response = given()              // Start building request
        .contentType("application/json")     // Tell server we're sending JSON
        .body(payload)                       // Attach user data
    .when()
        .post(base_url + "/users");         // Send POST request
    
    return response;                         // Return server's response
}
```

**Breaking it down:**
1. `given()` - Start building the request
2. `.contentType()` - Tell server what format we're sending
3. `.body()` - Attach the data
4. `.when().post()` - Send POST request to URL
5. Return the response

---

### 3️⃣ **testCases/UserTests.java** (Test Cases)

This is where we write actual tests.

**Example - Test Create User:**
```java
@Test(priority = 1)
public void testCreateUser() {
    // 1. Send request
    Response response = UserEndpoints.createUser(userPayload);
    
    // 2. Print response (to see what server sent back)
    response.then().log().all();
    
    // 3. Validate response
    Assert.assertEquals(response.getStatusCode(), 201);  // Check status code
    Assert.assertNotNull(response.jsonPath().getString("id"));  // Check ID exists
}
```

**What happens:**
1. We call the API
2. Server responds
3. We check if response is correct

---

## 🎯 How REST Assured Works

### Step 1: Build Request
```java
given()
    .contentType("application/json")
    .body(userPayload)
```

### Step 2: Send Request
```java
.when()
    .post("https://reqres.in/api/users")
```

### Step 3: Validate Response
```java
.then()
    .statusCode(201)
    .body("name", equalTo("John"))
```

---

## 📊 Understanding HTTP Status Codes

| Code | Meaning | Example |
|------|---------|---------|
| **200** | OK | Request successful |
| **201** | Created | New resource created |
| **204** | No Content | Deleted successfully |
| **400** | Bad Request | Invalid data sent |
| **404** | Not Found | Resource doesn't exist |
| **500** | Server Error | Server crashed |

---

## 🧪 What Each Test Does

### Test 1: Create User (POST)
```
Send: { "name": "John", "job": "Engineer" }
Expect: Status 201, ID returned
```

### Test 2: Get User (GET)
```
Send: GET /users/2
Expect: Status 200, User data returned
```

### Test 3: Update User (PUT)
```
Send: { "name": "Jane", "job": "Manager" }
Expect: Status 200, Updated timestamp
```

### Test 4: Delete User (DELETE)
```
Send: DELETE /users/2
Expect: Status 204 (No content)
```

### Test 5: Get User List (GET)
```
Send: GET /users?page=2
Expect: Status 200, List of users
```

---

## 🔍 JSON Path Explained

**JSON Response:**
```json
{
  "data": {
    "id": 2,
    "email": "janet@reqres.in",
    "first_name": "Janet"
  }
}
```

**How to extract values:**
```java
response.jsonPath().getString("data.id")          // Returns "2"
response.jsonPath().getString("data.email")       // Returns "janet@reqres.in"
response.jsonPath().getString("data.first_name")  // Returns "Janet"
```

---

## 🎲 Java Faker - Random Data Generation

Instead of hardcoding test data, we generate random data:

```java
Faker faker = new Faker();

userPayload.setName(faker.name().fullName());        // "John Smith"
userPayload.setJob(faker.job().position());          // "Software Engineer"
userPayload.setEmail(faker.internet().emailAddress()); // "john@example.com"
```

**Why?** Every test run uses different data, making tests more robust.

---

## 🚀 How to Run Tests

### Option 1: From Eclipse
1. Right-click on `testng.xml`
2. Select "Run As" → "TestNG Suite"
3. See results in Console

### Option 2: From Terminal
```bash
cd RestAssuredAPIFramework
mvn clean test
```

---

## 📝 What You'll See When Tests Run

```
Request:
POST https://reqres.in/api/users
Body: {"name":"John Doe","job":"Engineer"}

Response:
Status: 201
Body: {
  "name": "John Doe",
  "job": "Engineer",
  "id": "123",
  "createdAt": "2024-01-15T10:30:00.000Z"
}

✅ User created successfully!
```

---

## 🎤 Interview Questions You Can Answer

**Q: What is REST Assured?**
> "REST Assured is a Java library for testing REST APIs. It provides a simple way to send HTTP requests and validate responses."

**Q: What HTTP methods did you use?**
> "I used GET to fetch data, POST to create new records, PUT to update existing records, and DELETE to remove records."

**Q: How do you validate API responses?**
> "I validate status codes using assertions, check response body using JSON path, and verify response time. I also validate that required fields are not null."

**Q: What is the difference between PUT and POST?**
> "POST creates a new resource, while PUT updates an existing resource. POST is used when you don't know the resource ID, PUT is used when you know the ID."

**Q: How do you handle test data?**
> "I use Java Faker library to generate random test data like names, emails, and job titles. This ensures each test run uses unique data."

---

## 🎯 Key Concepts to Remember

1. **API** = Way for applications to communicate
2. **REST** = Architecture style using HTTP
3. **CRUD** = Create, Read, Update, Delete
4. **JSON** = Data format (like XML but simpler)
5. **Status Code** = Server's response code (200, 404, etc.)
6. **Endpoint** = URL where API is accessed
7. **Payload** = Data sent in request body
8. **Response** = Data received from server

---

## ✅ What Makes This Framework Good

1. ✅ **Organized Structure** - Separate classes for endpoints, payloads, tests
2. ✅ **Reusable Code** - Endpoint methods can be used in multiple tests
3. ✅ **Random Data** - Using Faker for dynamic test data
4. ✅ **Proper Validations** - Checking status codes and response data
5. ✅ **TestNG Integration** - Test management and reporting
6. ✅ **Maven Support** - Easy dependency management

---

## 🎓 Next Steps

After mastering this, you can:
1. Add more complex validations (JSON schema validation)
2. Add authentication (Bearer tokens, OAuth)
3. Add data-driven testing (read from Excel)
4. Add ExtentReports for better reporting
5. Integrate with CI/CD (GitHub Actions)

---

**Remember:** API testing is about sending requests and validating responses. That's it! Everything else is just making it organized and automated.

Good luck! 🚀
