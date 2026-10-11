
package com.tui.driver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;
import com.tui.driver.DriverFactory;
import org.openqa.selenium.Capabilities;
import com.tui.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver> DRIVER =
            new ThreadLocal<>();

    private DriverFactory() {
        // Prevent object creation.
    }

    public static void initializeDriver(
            String platformName,
            String appiumServerUrl,
            Capabilities capabilities)
            throws MalformedURLException {

        if (DRIVER.get() != null) {
            throw new IllegalStateException(
                    "Driver is already initialized for this thread.");
        }

        Objects.requireNonNull(platformName, "Platform name cannot be null.");
        Objects.requireNonNull(
                capabilities, "Capabilities cannot be null.");

        if (appiumServerUrl == null || appiumServerUrl.isBlank()) {
            throw new IllegalArgumentException(
                    "Appium server URL cannot be empty.");
        }

        URL serverUrl = URI.create(appiumServerUrl).toURL();

        AppiumDriver driver;

        if ("Android".equalsIgnoreCase(platformName)) {
            driver = new AndroidDriver(serverUrl, capabilities);

        } else if ("iOS".equalsIgnoreCase(platformName)) {
            driver = new IOSDriver(serverUrl, capabilities);

        } else {
            throw new IllegalArgumentException(
                    "Unsupported platform: " + platformName);
        }

        DRIVER.set(driver);

        try {
            // Use explicit waits in page objects and utilities.
            driver.manage().timeouts()
                    .implicitlyWait(Duration.ZERO);

        } catch (RuntimeException e) {
            quitDriver();
            throw e;
        }
    }

    public static AppiumDriver getDriver() {
        AppiumDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "Driver is not initialized. Initialize it first.");
        }

        return driver;
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();

        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}