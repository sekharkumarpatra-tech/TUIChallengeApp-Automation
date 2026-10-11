# Design Decisions – TUI Challenge App Automation

## 1. Project Objective

The objective of this project is to provide a maintainable mobile automation framework for the TUI Challenge application. It uses Appium, Java, Maven, Cucumber BDD, TestNG, and the Page Object Model.

The framework separates test scenarios, step definitions, page interactions, configuration, and test data to improve readability and maintainability.

## 2. Technology and Architecture Decisions

### Appium and Java

Appium was selected for mobile UI automation, while Java provides a structured programming model and integration with Maven, TestNG, and Cucumber.

### Cucumber BDD

Cucumber allows scenarios to be expressed in readable Gherkin syntax. This makes the test intent easier for QA engineers, developers, and other stakeholders to understand.

### Page Object Model

Page classes keep locators and page-specific actions separate from scenario steps. This reduces duplication and makes locator updates easier to maintain.

### Maven

Maven manages project dependencies and provides a standard command for building and executing the test suite.

### External Test Data

Test data should be maintained separately from scenario logic, using JSON or another suitable external format. Jackson is included in the project for JSON processing.

## 3. Test Scenario Selection

The final assessment suite should contain exactly five distinct scenarios. Selection should prioritize release confidence by covering high-value user journeys, critical validations, and important failure conditions.

The final five scenarios and their rationale must match the actual feature files and execution results. Avoid duplicate scenarios that validate the same behavior without providing additional coverage.

## 4. Reporting and Failure Analysis

Cucumber HTML reporting is configured through Maven Surefire. The report is generated at:

`target/cucumber-reports/cucumber.html`

Execution output should be reviewed alongside the HTML report. Screenshots and logs should be captured for failures where the framework supports them.

## 5. AI-Assisted Development

AI assistance was used to support framework design, documentation, and troubleshooting. Suggestions were reviewed against the project's actual implementation and test results.

AI-generated code or recommendations should not be treated as verified automatically. Dependencies, locators, assertions, and execution behavior must be checked through compilation and test runs.

## 6. Future Enhancements

Potential future improvements include:

- Parallel execution with isolated driver instances.
- Android and iOS execution through platform-specific configuration and page locators.
- CI execution using GitHub Actions or Jenkins.
- Cloud device execution using a compatible device-testing provider.
- Improved failure screenshots, structured logs, and report retention.
- Better configuration management for different environments.
- Additional API and accessibility validation where appropriate.

These are proposed enhancements and should not be considered implemented until they are configured and verified.

## 7. Execution

The project can be executed using:

`mvn clean test`

The Appium server, Android device or emulator, and application configuration must be available before execution.

## 8. Limitations and Verification

The current implementation and final test results should be reviewed before submission. In particular, verify that the suite contains exactly five scenarios, test data is externalized, retry handling is connected to execution if required, and the documented commands and paths match the repository.
