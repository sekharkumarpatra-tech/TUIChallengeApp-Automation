# TUI Challenge App – Mobile Test Automation

## 1. Project Overview

This project automates selected mobile application scenarios for the TUI Challenge application using Appium, Java, Maven, Cucumber BDD, and the Page Object Model (POM).

The framework is designed to support maintainable, reusable, and readable mobile automation tests.

## 2. Technology Stack

- Java 21
- Maven
- Appium Java Client
- Appium Server
- Cucumber BDD
- TestNG
- Selenium
- Android Debug Bridge (ADB)
- Git and GitHub

## 3. Framework Features

- Page Object Model for separating page actions from test logic
- Cucumber feature files written in Gherkin
- Reusable step definitions and utility methods
- Maven-based test execution
- Externalized test data
- Cucumber HTML reporting
- Test categorization using Cucumber tags
- Failure screenshots and execution logs, where configured
- Retry support for transient failures, where configured

## 4. Project Structure

```text
TUIChallengeApp-Automation/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/tui/
│   │   │       ├── runners/
│   │   │       ├── stepdefinitions/
│   │   │       ├── pages/
│   │   │       ├── hooks/
│   │   │       └── utilities/
│   │   └── resources/
│   │       ├── features/
│   │       └── testdata/
├── pom.xml
├── README.md
├── DECISIONS.md
└── .gitignore
```

*Note: This is the intended structure. Update the folder names to match the actual project.*

## 5. Prerequisites

Install and configure:

1. Java JDK 21
2. Maven
3. Node.js and Appium
4. Appium's UiAutomator2 driver
5. Android SDK and ADB
6. An Android emulator or a connected Android device
7. Git

Verify the installations:

```bash
java -version
mvn -version
adb devices
appium --version
```

## 6. Application Configuration

Configure the application package, activity, device details, and Appium server URL using the project's configuration files.

Ensure that:
- The Android device or emulator is running.
- USB debugging is enabled for a physical device.
- The device appears in `adb devices`.
- The Appium server is running.
- The application APK is available at the configured path, if the framework installs it automatically.

Do not commit credentials, access tokens, or other secrets.

## 7. How to Run the Tests

Clone the repository:

```bash
git clone https://github.com/sekharkumarpatra-tech/TUIChallengeApp-Automation.git
cd TUIChallengeApp-Automation
```

Start Appium in a separate terminal:

```bash
appium
```

Run the test suite:

```bash
mvn clean test
```

Run tests by Cucumber tag, if tag filtering is configured:

```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

## 8. Test Scenarios

The automated scenarios cover selected high-value application workflows. The final suite should contain exactly five distinct scenarios chosen to maximize release confidence.

Document the five scenarios here after confirming the final feature file.

## 9. Test Reports and Evidence

After execution, review the Maven output and the generated test reports.

The expected Cucumber HTML report location is:

```text
target/cucumber-reports/cucumber.html
```

Confirm that the configured reporting plugin generates the report at this location. Capture screenshots and logs for failed tests where supported.

## 10. Troubleshooting

- **Device not detected:** Run `adb devices` and verify USB debugging or emulator status.
- **Appium connection failure:** Confirm that the Appium server is running at the configured URL and port.
- **Session creation failure:** Verify device capabilities, app package, activity, and installed Appium drivers.
- **Test compilation failure:** Check Java and Maven versions and run `mvn clean test`.
- **Missing report:** Review the Cucumber reporting configuration and Maven test output.

## 11. Design and Future Enhancements

Refer to `DECISIONS.md` for architecture decisions, scenario selection rationale, AI-assisted development notes, and future improvements.

## 12. Repository

GitHub: https://github.com/sekharkumarpatra-tech/TUIChallengeApp-Automation
