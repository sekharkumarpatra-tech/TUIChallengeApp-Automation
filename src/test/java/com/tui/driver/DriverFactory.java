
package com.tui.driver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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
            DesiredCapabilities capabilities) throws MalformedURLException {

        if (DRIVER.get() != null) {
            throw new IllegalStateException(
                    "Driver is already initialized for this thread.");
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

        driver.manage().timeouts()
                .implicitlyWait(Duration.ZERO);

        DRIVER.set(driver);
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