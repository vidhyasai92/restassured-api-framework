# 📚 REST API Framework - Complete Explanation

## 🎯 What is API Testing?

### **UI Testing vs API Testing**

**UI Testing (Project 1 - OpenCart):**
```
User → Browser → Website → Server → Database
         ↓
    We test HERE (what user sees)
```
- Opens browser
- Clicks buttons
- Fills forms
- Checks what appears on screen

**API Testing (Project 2 - This Project):**
```
Application → API → Server → Database
              ↓
         We test HERE (backend communication)
```
- No browser
- Sends HTTP requests directly
- Checks server responses
- Tests backend logic

### **Real-World Example:**

**When you use Instagram:**
- **UI**: You see photos, tap like button, see heart turn red
- **API**: Behind the scenes, app sends `POST /like` request to server

**We're testing the API part** - the communication between app and server.

---

## 🏗️ Framework Architecture (How Everything Connects)

```
┌─────────────────────────────────────────────────────────────┐
│                    TEST EXECUTION FLOW                       │
└─────────────────────────────────────────────────────────────┘

1. TestNG reads testng.xml
   ↓
2. Runs UserTestsWithReporting.java
   ↓
3. @BeforeClass: Setup
   - JavaFaker generates random data (name, email, job)
   - Logger starts
   - ExtentReports initializes
   ↓
4. Test Method: testCreateUser()
   - Creates User object with fake data
   - Calls UserEndpoints.createUser(user)
   ↓
5. UserEndpoints.createUser()
   - Reads URL from Routes.java
   - Uses REST Assured to send POST request
   - Returns Response
   ↓
6. Back to Test Method
   - Validates response status code (201)
   - Validates response body (id, createdAt)
   - Logs results to ExtentReports
   - Logs to Log4j2
   ↓
7. @AfterClass: Cleanup
   - Generates HTML report
   - Saves logs
```

---

## 📁 File-by-File Explanation

### **1. pom.xml** - The Foundation

**What it does:** Tells Maven what libraries to download

**Key Dependencies:**
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
</dependency>
```

**Think of it as:** A shopping list for your project
- "I need REST Assured library"
- "I need TestNG library"
- Maven downloads them automatically

---

### **2. payload/User.java** - Data Structure

**What it does:** Defines what a "User" looks like

```java
public class User {
    private String name;
    private String job;
    private String email;
    
    // Getters and Setters
}
```

**Real-world analogy:**
- Like a form template
- Has fields: name, job, email
- Can fill it with data and send to API

**Why we need it:**
- REST Assured converts this Java object to JSON automatically
- Clean way to handle request/response data

**Example:**
```java
User user = new User();
user.setName("John Doe");
user.setJob("Engineer");

// REST Assured converts to:
{
  "name": "John Doe",
  "job": "Engineer"
}
```

---

### **3. endpoints/Routes.java** - URL Storage

**What it does:** Stores all API endpoint URLs in one place

```java
public class Routes {
    public static String base_url = "https://reqres.in/api";
    public static String post_url = base_url + "/users";
    public static String get_url = base_url + "/users/{id}";
}
```

**Why we need it:**
- If API URL changes, update in ONE place only
- Easy to maintain
- No hardcoded URLs scattered everywhere

**Real-world analogy:**
- Like a phone book
- All addresses in one place
- If someone moves, update once

---

### **4. endpoints/UserEndpoints.java** - API Request Methods

**What it does:** Contains methods to send HTTP requests

```java
public static Response createUser(User payload) {
    Response response = given()
        .contentType("application/json")
        .body(payload)
    .when()
        .post(Routes.post_url);
    
    return response;
}
```

**Breaking it down:**

1. **given()** - Setup phase
   - `contentType("application/json")` - Tell server we're sending JSON
   - `body(payload)` - Attach User data

2. **when()** - Action phase
   - `.post(Routes.post_url)` - Send POST request to URL

3. **Return response** - Get server's reply

**Real-world analogy:**
- Like a mail service
- You give them a letter (payload)
- They send it to an address (URL)
- They bring back the reply (response)

**Why separate file:**
- Tests don't need to know HOW to send requests
- Tests just call: `UserEndpoints.createUser(user)`
- If API changes, update endpoints only, not tests

---

### **5. testCases/UserTests.java** - Basic Tests

**What it does:** Contains test scenarios

```java
@Test(priority = 1)
public void testCreateUser() {
    // 1. Send request
    Response response = UserEndpoints.createUser(userPayload);
    
    // 2. Print response
    response.then().log().all();
    
    // 3. Validate
    Assert.assertEquals(response.getStatusCode(), 201);
    Assert.assertNotNull(response.jsonPath().getString("id"));
}
```

**Step-by-step:**

1. **Send Request:** Call endpoint method
2. **Log Response:** Print what server sent back
3. **Validate:** Check if response is correct
   - Status code should be 201 (Created)
   - Response should have "id" field

**Real-world analogy:**
- Like checking if your online order worked
- Did you get confirmation? (status 201)
- Did you get order number? (id field)

---

### **6. testCases/UserTestsWithReporting.java** - Enhanced Tests

**What's different from UserTests.java:**

```java
@BeforeClass
public void setupData() {
    // Initialize Faker
    faker = new Faker();
    
    // Generate random data
    userPayload.setName(faker.name().fullName());
    userPayload.setJob(faker.job().position());
    
    // Setup reporting
    extent = ExtentReportManager.setupExtentReport();
    logger = LogManager.getLogger(this.getClass());
}

@Test(priority = 1)
public void testCreateUser() {
    test = extent.createTest("Create User Test");
    logger.info("Creating User");
    
    // Send request
    Response response = UserEndpoints.createUser(userPayload);
    
    // Validate
    Assert.assertEquals(response.getStatusCode(), 201);
    test.pass("Status code validation passed: 201");
    logger.info("User created successfully");
}
```

**Added features:**
1. **JavaFaker** - Generates random realistic data
2. **ExtentReports** - Creates HTML report
3. **Log4j2** - Logs execution details

**Why this is better:**
- Professional HTML reports
- Detailed logs for debugging
- Unique data every run (no conflicts)

---

### **7. utilities/ExtentReportManager.java** - Report Generator

**What it does:** Creates beautiful HTML reports

```java
public static ExtentReports setupExtentReport() {
    String reportName = "API-Test-Report-" + timestamp + ".html";
    sparkReporter = new ExtentSparkReporter("./reports/" + reportName);
    
    sparkReporter.config().setDocumentTitle("REST API Automation Report");
    sparkReporter.config().setTheme(Theme.DARK);
    
    extent = new ExtentReports();
    extent.attachReporter(sparkReporter);
    
    return extent;
}
```

**What happens:**
1. Creates HTML file with timestamp
2. Sets report title and theme
3. Configures system info (OS, Java version, etc.)

**Result:** Beautiful HTML report with:
- Test pass/fail status
- Execution time
- Detailed logs
- Charts and graphs

---

### **8. utilities/ExcelUtility.java** - Excel Operations

**What it does:** Reads/writes Excel files for data-driven testing

```java
public String getCellData(String sheetName, int rownum, int colnum) {
    // Open Excel file
    // Go to specific sheet
    // Read cell at row, column
    // Return data
}
```

**Use case:**
```
Excel file:
| Name      | Job       | Email           |
|-----------|-----------|-----------------|
| John Doe  | Engineer  | john@test.com   |
| Jane Smith| Manager   | jane@test.com   |

Test reads each row and creates users
```

**Why useful:**
- Test with multiple data sets
- Non-technical people can add test data
- No code changes needed for new test cases

---

### **9. utilities/DataProviders.java** - Data Provider

**What it does:** Feeds data from Excel to tests

```java
@DataProvider(name = "UserData")
public String[][] getUserData() throws IOException {
    // Read Excel file
    // Return 2D array of data
}
```

**How tests use it:**
```java
@Test(dataProvider = "UserData")
public void testWithExcelData(String name, String job, String email) {
    // This test runs once for each row in Excel
}
```

**Real-world analogy:**
- Like a waiter bringing multiple orders
- Each order (row) is served one by one
- Test runs multiple times with different data

---

## 🔄 Complete Test Execution Example

Let's trace what happens when you run `testCreateUser()`:

### **Step 1: Test Starts**
```java
@Test(priority = 1)
public void testCreateUser() {
```

### **Step 2: Create Test in Report**
```java
test = extent.createTest("Create User Test");
logger.info("Creating User");
```
- ExtentReports creates new test entry
- Log4j2 writes to log file

### **Step 3: Send API Request**
```java
Response response = UserEndpoints.createUser(userPayload);
```

**What happens inside UserEndpoints.createUser():**
```java
Response response = given()
    .contentType("application/json")  // Header: Content-Type: application/json
    .body(userPayload)                // Body: {"name":"John","job":"Engineer"}
.when()
    .post(Routes.post_url);           // POST https://reqres.in/api/users
```

**HTTP Request sent:**
```
POST https://reqres.in/api/users
Content-Type: application/json

{
  "name": "John Doe",
  "job": "Software Engineer"
}
```

### **Step 4: Server Responds**
```json
{
  "name": "John Doe",
  "job": "Software Engineer",
  "id": "123",
  "createdAt": "2024-02-09T10:30:00.000Z"
}
```

### **Step 5: Validate Response**
```java
Assert.assertEquals(response.getStatusCode(), 201);
test.pass("Status code validation passed: 201");
```
- Checks status code is 201 (Created)
- Logs success to report

```java
Assert.assertNotNull(response.jsonPath().getString("id"));
test.pass("User ID is not null");
```
- Checks "id" field exists
- Logs success to report

### **Step 6: Test Completes**
```java
logger.info("User Created Successfully");
```
- Logs completion
- ExtentReports marks test as PASSED

---

## 🎯 Key Concepts Explained

### **1. REST Assured Syntax**

```java
given()     // Setup (headers, body, auth)
.when()     // Action (GET, POST, PUT, DELETE)
.then()     // Validation (status, body, headers)
```

**Example:**
```java
given()
    .contentType("application/json")
    .body(user)
.when()
    .post("/users")
.then()
    .statusCode(201)
    .body("id", notNullValue());
```

### **2. HTTP Methods**

| Method | Purpose | Example |
|--------|---------|---------|
| GET | Fetch data | Get user details |
| POST | Create data | Create new user |
| PUT | Update data | Update user info |
| DELETE | Remove data | Delete user |

### **3. Status Codes**

| Code | Meaning | When it happens |
|------|---------|-----------------|
| 200 | OK | Request successful |
| 201 | Created | New resource created |
| 204 | No Content | Deleted successfully |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Server crashed |

### **4. JSON Path**

**Response:**
```json
{
  "data": {
    "id": 2,
    "email": "janet@test.com",
    "first_name": "Janet"
  }
}
```

**Extract values:**
```java
response.jsonPath().getString("data.id")         // "2"
response.jsonPath().getString("data.email")      // "janet@test.com"
response.jsonPath().getString("data.first_name") // "Janet"
```

---

## 🎤 Interview Explanation (Simple Version)

**"Explain your API framework"**

**Answer:**
> "I built a REST API testing framework using REST Assured and TestNG. The framework has a modular structure with four main layers:
>
> 1. **Payload layer** - POJO classes that represent request/response data
> 2. **Endpoints layer** - Methods that send HTTP requests (GET, POST, PUT, DELETE)
> 3. **Test layer** - TestNG test cases that validate API responses
> 4. **Utilities layer** - Helper classes for reporting and data management
>
> I used JavaFaker to generate dynamic test data, so each test run uses unique data. For reporting, I integrated ExtentReports which creates interactive HTML reports with test results, and Log4j2 for detailed execution logs.
>
> The framework tests the ReqRes.in API, covering all CRUD operations - Create, Read, Update, and Delete users. I validate status codes, response bodies using JSON path, and include negative test scenarios like testing for 404 errors.
>
> I also set up CI/CD with GitHub Actions, so tests run automatically on every code push."

---

## 🔍 Common Questions

### **Q: Why separate endpoints from tests?**
**A:** Reusability and maintainability. If API changes, update endpoints only. Multiple tests can use same endpoint methods.

### **Q: Why use POJO classes?**
**A:** REST Assured automatically converts Java objects to JSON. Cleaner code, type-safe, easier to maintain.

### **Q: What's the difference between UserTests and UserTestsWithReporting?**
**A:** UserTests is basic (just assertions). UserTestsWithReporting adds ExtentReports, Log4j2, and JavaFaker for professional execution.

### **Q: Do I need Excel for this framework?**
**A:** No, it's optional. ExcelUtility is there if you want data-driven testing. JavaFaker works without Excel.

### **Q: Why no browser opens?**
**A:** API testing is backend testing. We send HTTP requests directly to server, no UI involved.

---

## 🎯 What Makes This Framework Professional?

1. **Separation of Concerns** - Endpoints, payloads, tests, utilities in separate packages
2. **Reusability** - Endpoint methods used by multiple tests
3. **Maintainability** - URLs in Routes.java, change once affects all
4. **Reporting** - Triple reporting (ExtentReports + TestNG + Log4j2)
5. **Dynamic Data** - JavaFaker generates unique data every run
6. **Negative Testing** - Not just happy path, includes error scenarios
7. **CI/CD** - Automated testing on every push
8. **Documentation** - Clear README and structure docs
9. **Industry Standard** - Follows REST Assured best practices

---

**You now understand the complete framework!** 🎉

Read this document, then try running the tests to see it in action!
