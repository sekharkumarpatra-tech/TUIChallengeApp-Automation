# TUI Challenge App – Mobile Test Automation Framework

## 1. Project Overview

This project contains a mobile test automation framework for the TUI Challenge Android application. It uses Appium, Java, Maven, Cucumber BDD, TestNG, and the Page Object Model (POM).

The framework is designed to provide reusable mobile automation components, readable BDD scenarios, externalized test data, automated test execution, and HTML reporting.

## 2. Technology Stack

- Java 21
- Maven
- Appium Java Client
- Selenium WebDriver
- Cucumber BDD
- TestNG
- Jackson for JSON test data
- Git and GitHub
- GitHub Actions with a self-hosted Windows runner

## 3. Framework Structure

```text
TUIChallengeApp-Automation/
├── .github/
│   └── workflows/
│       └── maven-tests.yml
├── src/
│   └── test/
│       ├── java/
│       │   └── com/tui/
│       │       ├── base/
│       │       ├── driver/
│       │       ├── listeners/
│       │       ├── pages/
│       │       ├── runners/
│       │       ├── stepdefinitions/
│       │       └── utils/
│       └── resources/
│           ├── config/
│           ├── features/
│           ├── locators/
│           └── testdata/
├── DECISIONS.md
├── README.md
├── pom.xml
└── testng.xml
```

## 4. Prerequisites

Install and configure the following:

1. Java Development Kit (JDK) 21.
2. Apache Maven.
3. Node.js and Appium Server.
4. Appium's UiAutomator2 driver for Android.
5. Android SDK Platform Tools, including ADB.
6. An Android device or emulator with USB debugging enabled where applicable.
7. Git.

Verify the installations:

```bash
java -version
mvn -version
node --version
appium --version
adb version
```

Install the Android driver if it is not already installed:

```bash
appium driver install uiautomator2
```

Use compatible versions of Appium, its driver, and the Appium Java client.

## 5. Clone the Repository

Clone the repository:

```bash
git clone https://github.com/sekharkumarpatra-tech/TUIChallengeApp-Automation.git
cd TUIChallengeApp-Automation
```

## 6. Configure the Android Device

Connect the Android device using USB and enable Developer Options and USB debugging.

Verify that ADB recognizes the device:

```bash
adb devices
```

The device should appear with the status `device`.

Obtain the application's package and launchable activity using Android SDK tools or the application's supplied details. Configure the correct values for the installed APK.

Start Appium:

```bash
appium --address 127.0.0.1 --port 4723
```

Keep the Appium server running while executing tests.

## 7. Configure the Framework

Edit:

`src/test/resources/config/config.properties`

Provide the required configuration values for the target environment, including:

- `platformName`
- `appiumServerUrl`
- `androidAutomationName`
- `androidDeviceName`
- `androidAppPackage`
- `androidAppActivity`
- `androidPlatformVersion` (optional)

Use the exact property names expected by `BaseTest.java`. Do not commit credentials, access tokens, or other secrets.

The current `BaseTest` implementation supports Android execution. iOS execution requires additional configuration and validation before it can be used.

## 8. Execute the Tests

Run the complete suite from the project root:

```bash
mvn clean test
```

Maven compiles the project, executes the configured TestNG/Cucumber tests, and generates the configured test reports.

For additional Maven output:

```bash
mvn clean test -e
```

## 9. Cucumber HTML Report

After a successful execution, open:

`target/cucumber-reports/cucumber.html`

The report contains the Cucumber execution results for the run. The `target` directory is generated output and may be recreated by `mvn clean test`.

For submission evidence, copy the final report to the designated evidence folder after the final test run.

## 10. Framework Design

### Page Object Model

Page classes encapsulate screen locators and user interactions. Step definitions call page methods instead of duplicating locator logic.

### Driver Management

`DriverFactory` creates and manages Appium drivers using `ThreadLocal<AppiumDriver>`. This supports separate driver references per thread, but parallel execution must also use independent device sessions and be validated before it is considered supported.

### Reusable Utilities

The framework includes utilities for locator reading, test-data reading, waits, and screenshots. Each utility should be maintained only if it is used by the framework.

### Retry Handling

TestNG retry handling is configured through `RetryListener` and `RetryAnalyzer`. Retries should be used for controlled recovery from transient failures, not to hide genuine defects.

### External Test Data

JSON test data is maintained under:

`src/test/resources/testdata/login-data.json`

Test steps should load and use this data through `TestDataReader`, with meaningful assertions against expected application behavior.

## 11. GitHub Actions CI

The project contains a workflow at:

`.github/workflows/maven-tests.yml`

The current CI setup uses a self-hosted Windows runner because Android automation requires access to the Android device and local Appium environment.

To execute the workflow in the current setup:

1. Connect the Android device and confirm `adb devices` shows it as `device`.
2. Start the Appium server.
3. Start the configured self-hosted runner from its runner directory using `run.cmd`.
4. Trigger the GitHub Actions workflow.
5. Review the workflow logs and test results.

The Windows runner must remain available during the job. CI success on this setup does not, by itself, demonstrate execution on a cloud device or parallel Android/iOS execution.

## 12. Execution Evidence

Store final submission evidence under `evidence/`, where practical:

- Final Cucumber HTML report.
- Test execution logs.
- Screenshots relevant to test results or failures.
- GitHub Actions workflow result or screenshot.

Capture evidence after the final scenario selection and successful test execution. Do not include passwords, tokens, or other sensitive information.

## 13. Current Scope and Limitations

- Android execution is the current implemented platform.
- The framework must be verified against exactly five selected scenarios for the final assessment submission.
- Parallel execution requires separate device sessions and successful concurrency validation.
- iOS and cloud-device execution must not be claimed as implemented until they have been configured and tested.

## 14. Troubleshooting

### Device not detected

```bash
adb devices
```

Check the USB cable, debugging authorization, and device status.

### Appium connection failure

Confirm that Appium is running at the configured URL and port.

### Application launch failure

Validate the package name, activity name, installed APK, and device compatibility.

### Configuration error

Confirm that `config/config.properties` is present on the classpath and that the required property names and values are correct.

### Report not found

Review the Maven output and confirm that the configured Cucumber HTML report path matches the location you are checking.

## 15. Maintainer

Sekhar Patra

Repository: https://github.com/sekharkumarpatra-tech/TUIChallengeApp-Automation