package com.tui.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.tui.driver.DriverFactory;

public class BaseTest {

    private static final String CONFIG_FILE =
            "config/config.properties";

    public void setUp()
            throws IOException, MalformedURLException {

        Properties properties = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new IOException(
                        "Configuration file not found: "
                                + CONFIG_FILE);
            }

            properties.load(input);
        }

        String platformName =
                getRequiredProperty(properties, "platformName");

        String serverUrl =
                getRequiredProperty(properties, "appiumServerUrl");

        if (!"Android".equalsIgnoreCase(platformName)) {
            throw new IllegalArgumentException(
                    "This assessment setup currently supports Android.");
        }

        DesiredCapabilities capabilities =
                new DesiredCapabilities();

        capabilities.setCapability(
                "platformName", platformName);

        capabilities.setCapability(
                "appium:automationName",
                getRequiredProperty(
                        properties, "androidAutomationName"));

        capabilities.setCapability(
                "appium:deviceName",
                getRequiredProperty(
                        properties, "androidDeviceName"));

        capabilities.setCapability(
                "appium:appPackage",
                getRequiredProperty(
                        properties, "androidAppPackage"));

        capabilities.setCapability(
                "appium:appActivity",
                getRequiredProperty(
                        properties, "androidAppActivity"));

        String platformVersion =
                properties.getProperty(
                        "androidPlatformVersion", "").trim();

        if (!platformVersion.isEmpty()) {
            capabilities.setCapability(
                    "appium:platformVersion", platformVersion);
        }

        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability("appium:fullReset", false);
        capabilities.setCapability(
                "appium:newCommandTimeout", 3600);
        capabilities.setCapability(
                "appium:autoGrantPermissions", true);

        DriverFactory.initializeDriver(
                platformName, serverUrl, capabilities);
    }

    public void tearDown() {
        DriverFactory.quitDriver();
    }

    private String getRequiredProperty(
            Properties properties, String key) {

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing required configuration property: "
                            + key);
        }

        return value.trim();
    }
}