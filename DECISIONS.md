# Technical Decisions – TUI Challenge App Automation

## 1. Project Structure

The framework follows a layered design to separate test scenarios, application interactions, driver management, configuration, and reusable utilities.

- **base:** Contains shared test setup and teardown logic.
- **driver:** Creates and manages Appium driver instances.
- **pages:** Encapsulates screen locators and application interactions using the Page Object Model.
- **stepdefinitions:** Connects readable Cucumber steps to page methods and test assertions.
- **runners:** Configures Cucumber execution through TestNG.
- **listeners:** Implements retry behavior for failed tests.
- **utils:** Contains reusable helpers for configuration-dependent tasks, waits, locators, screenshots, and test data.
- **features:** Stores readable Gherkin scenarios.
- **testdata:** Stores external JSON test data.
- **locators:** Separates platform-specific locator definitions.
- **config:** Stores environment and device configuration.

This separation improves maintainability, reusability, and readability. It also makes the framework easier to extend to other applications.

## 2. Technology Choices

### Appium and Java

Appium supports mobile UI automation, while Java provides a mature ecosystem of automation libraries and test frameworks.

### Cucumber BDD

Cucumber expresses test behavior in business-readable Given/When/Then steps. This makes scenarios easier to review and discuss with both technical and non-technical stakeholders.

### Maven

Maven manages dependencies and provides a standard terminal command for test execution:

`mvn clean test`

### Page Object Model

Page classes own application locators and actions. Step definitions coordinate test behavior without duplicating low-level UI interaction code.

### TestNG

TestNG integrates with the Cucumber runner and provides support for listeners, retry handling, and execution configuration.

### JSON Test Data

External JSON keeps test inputs separate from step definitions. This makes it easier to maintain and extend test coverage without hardcoding values throughout the tests.

## 3. Driver Management and Parallel Execution

`DriverFactory` uses `ThreadLocal<AppiumDriver>` to maintain a separate driver reference for each Java thread.

This design is intended to support parallel test execution, provided each parallel worker also receives an independent Appium session and device or simulator.

The current setup has been validated for Android execution. Parallel execution must be tested with separate device sessions before it is considered production-ready. The current `BaseTest` implementation explicitly restricts execution to Android.

### Proposed Android and iOS CI Strategy

Given more time, I would extend the CI pipeline to run Android and iOS jobs independently:

1. Define separate Android and iOS CI jobs.
2. Provision independent Android and iOS devices, simulators, or cloud-device sessions.
3. Supply platform-specific capabilities and configuration to each job.
4. Run the relevant tagged scenarios on each platform.
5. Keep driver instances isolated per test thread and device session.
6. Generate separate reports and artifacts for each platform.
7. Publish the reports and combine the results in a final CI summary.

Parallel execution would be enabled only after validating device isolation, session management, reporting, and cleanup.

## 4. Scenario Selection

The assessment requests exactly five scenarios that provide strong release confidence.

The final five should be selected from the implemented application flows, prioritizing:

1. Successful login or the primary supported entry flow.
2. Invalid-input or authentication error handling, if supported.
3. Navigation to a key application screen.
4. A critical user action on that screen.
5. A validation or recovery path that protects against a meaningful user-facing defect.

The final selection must reflect the actual application behavior and existing feature file. Scenarios should not be invented for functionality that the application does not provide.

The selection prioritizes critical user journeys, meaningful assertions, and distinct defect coverage over a larger number of repetitive tests. Duplicate scenarios should be removed.

## 5. AI Assistance and Corrections

AI tools were used as development assistance for reviewing framework structure, discussing reusable Appium patterns, improving documentation, and identifying potential configuration or implementation issues.

AI-generated suggestions were reviewed against the actual code and execution results. Changes were retained only when they were appropriate for the project.

For example, the framework review highlighted that using `ThreadLocal<AppiumDriver>` is a foundation for thread isolation but does not, by itself, guarantee safe parallel execution. Separate device sessions and validation are also necessary.

All final implementation claims should be supported by the actual source code and test evidence. AI assistance was not treated as a substitute for execution or verification.

## 6. Future Improvements

Given additional time, I would consider the following enhancements without including them in the current implementation:

- Complete and validate iOS support.
- Run Android and iOS jobs in parallel on dedicated devices or a cloud-device platform.
- Improve structured logging and failure diagnostics.
- Publish reports and screenshots as CI artifacts.
- Expand test coverage based on risk and application requirements.
- Improve test-data management for multiple environments.
- Add accessibility checks where the application and assessment scope support them.
- Integrate cloud-device execution after credentials, device availability, and configuration are established.

## 7. Retry Strategy

The framework registers a TestNG retry listener and a retry analyzer configured for one retry.

Retries may help recover from transient infrastructure issues. They must not hide application defects or replace root-cause analysis. Retry behavior should be verified with the Cucumber/TestNG integration before being relied upon in parallel execution.

## 8. Evidence and Validation

The final submission should include:

- Successful Maven test execution.
- The Cucumber HTML report.
- Relevant execution logs and screenshots.
- GitHub Actions execution evidence.
- The final source code and setup documentation.

The final evidence must be captured after the required five scenarios have been selected and executed successfully.

## 9. Known Scope Limitations

The current validated setup uses Android with a self-hosted Windows runner and a connected device.

The following must not be represented as completed until independently implemented and validated:

- iOS test execution.
- Parallel Android/iOS execution.
- Cloud-device execution.
- Parallel execution across independent devices.

These are documented as planned extensions rather than claimed as working capabilities.