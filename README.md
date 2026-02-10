# REST API Testing Framework

REST Assured-based API automation framework for testing RESTful web services.

[![API Tests](https://github.com/vidhyasai92/restassured-api-framework/actions/workflows/api-tests.yml/badge.svg)](https://github.com/vidhyasai92/restassured-api-framework/actions/workflows/api-tests.yml)

## Tech Stack

- Java 11
- REST Assured 5.4.0
- TestNG 7.9.0
- Maven
- ExtentReports 5.1.1
- Log4j2 2.22.1
- JavaFaker 1.0.2

## Setup

```bash
git clone https://github.com/vidhyasai92/restassured-api-framework.git
cd restassured-api-framework
mvn clean install
```

## Running Tests

```bash
mvn test
```

## API Under Test

JSONPlaceholder - Free fake REST API for testing

Base URL: `https://jsonplaceholder.typicode.com`

## Test Scenarios

1. Create User (POST)
2. Get User (GET)
3. Update User (PUT)
4. Delete User (DELETE)
5. Get User List (GET)
6. User Not Found (Negative Test)

## Project Structure

```
src/test/java/
├── endpoints/       # API request methods
├── payload/         # POJO classes
├── testCases/       # Test scenarios
└── utilities/       # Helper classes
```

## Features

- REST Assured for API testing
- POJO-based request/response
- TestNG for test management
- ExtentReports for HTML reporting
- Log4j2 for logging
- JavaFaker for dynamic test data
- CI/CD with GitHub Actions

## Reports

- ExtentReports: `reports/API-Test-Report-{timestamp}.html`
- Logs: `logs/api-automation.log`

## Author

Vidhyalakshmi Sambamurthy

