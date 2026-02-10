# REST API Framework - Complete Structure

## 📁 Project Directory Structure

```
RestAssuredAPIFramework/
│
├── src/
│   └── test/
│       ├── java/
│       │   ├── endpoints/
│       │   │   ├── Routes.java                    # All API endpoint URLs
│       │   │   └── UserEndpoints.java             # User CRUD operations
│       │   │
│       │   ├── payload/
│       │   │   └── User.java                      # User POJO class
│       │   │
│       │   ├── testCases/
│       │   │   ├── UserTests.java                 # Basic API tests
│       │   │   └── UserTestsWithReporting.java    # Tests with ExtentReports
│       │   │
│       │   └── utilities/
│       │       ├── ExtentReportManager.java       # Report configuration
│       │       ├── ExcelUtility.java              # Excel read/write operations
│       │       └── DataProviders.java             # TestNG data providers
│       │
│       └── resources/
│           ├── config.properties                  # Configuration settings
│           └── log4j2.xml                         # Logging configuration
│
├── testData/
│   └── UserTestData.xlsx                          # Test data (to be created)
│
├── reports/                                       # ExtentReports (auto-generated)
├── logs/                                          # Log files (auto-generated)
├── test-output/                                   # TestNG reports (auto-generated)
│
├── .github/
│   └── workflows/
│       └── api-tests.yml                          # GitHub Actions CI/CD
│
├── .gitignore                                     # Git ignore rules
├── pom.xml                                        # Maven dependencies
├── testng.xml                                     # TestNG suite configuration
├── README.md                                      # Project documentation
└── PROJECT_STRUCTURE.md                           # This file
```

## 📝 File Descriptions

### **1. endpoints/** - API Request Layer
- **Routes.java**: Centralized location for all API endpoint URLs
- **UserEndpoints.java**: Contains methods for all User API operations (POST, GET, PUT, DELETE)

### **2. payload/** - Data Models
- **User.java**: POJO (Plain Old Java Object) class representing User entity with getters/setters

### **3. testCases/** - Test Layer
- **UserTests.java**: Basic test cases without reporting
- **UserTestsWithReporting.java**: Enhanced tests with ExtentReports and Log4j integration

### **4. utilities/** - Helper Classes
- **ExtentReportManager.java**: Manages ExtentReports setup and logging
- **ExcelUtility.java**: Reads/writes data from Excel files
- **DataProviders.java**: Provides test data for data-driven testing

### **5. resources/** - Configuration Files
- **config.properties**: Environment and test configuration
- **log4j2.xml**: Log4j2 logging configuration

### **6. CI/CD**
- **.github/workflows/api-tests.yml**: GitHub Actions workflow for automated testing

## 🔄 Test Flow

```
1. TestNG reads testng.xml
2. @BeforeClass: Setup test data (JavaFaker generates random data)
3. Test Execution:
   - UserEndpoints methods send HTTP requests
   - Response received from API
   - Assertions validate response
   - ExtentReports logs results
   - Log4j2 logs execution details
4. @AfterClass: Generate reports
```

## 🎯 Key Features

✅ **Modular Architecture** - Separation of concerns (endpoints, tests, utilities)  
✅ **POJO-based** - Clean request/response handling  
✅ **Comprehensive Reporting** - ExtentReports + TestNG + Log4j2  
✅ **Data-Driven Testing** - Excel integration ready  
✅ **Dynamic Test Data** - JavaFaker for realistic data  
✅ **CI/CD Ready** - GitHub Actions workflow  
✅ **Configurable** - Properties file for easy configuration  
✅ **Professional Structure** - Industry-standard framework design  

## 🚀 How to Use

1. **Run Basic Tests**: Execute `UserTests.java`
2. **Run with Reporting**: Execute `UserTestsWithReporting.java`
3. **Run All Tests**: `mvn test` or run `testng.xml`
4. **View Reports**: Open `reports/API-Test-Report-{timestamp}.html`
5. **Check Logs**: Open `logs/api-automation.log`

## 📊 Test Coverage

- ✅ POST - Create User
- ✅ GET - Fetch User
- ✅ PUT - Update User
- ✅ DELETE - Remove User
- ✅ GET - Fetch User List
- ✅ Negative Test - User Not Found (404)

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| REST Assured | API testing framework |
| TestNG | Test management & execution |
| ExtentReports | HTML test reporting |
| Log4j2 | Logging framework |
| Apache POI | Excel operations |
| JavaFaker | Test data generation |
| Maven | Build & dependency management |
| GitHub Actions | CI/CD automation |

---

**Framework Status**: ✅ Production Ready
