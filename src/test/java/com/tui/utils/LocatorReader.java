
package com.tui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;

public final class LocatorReader {

    private final Properties properties = new Properties();

    public LocatorReader(String platformName) {

        String fileName;

        if ("Android".equalsIgnoreCase(platformName)) {
            fileName = "locators/android.properties";
        } else if ("iOS".equalsIgnoreCase(platformName)) {
            fileName = "locators/ios.properties";
        } else {
            throw new IllegalArgumentException(
                "Unsupported platform: " + platformName
            );
        }

        try (InputStream input =
                 getClass().getClassLoader()
                     .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new IllegalStateException(
                    "Locator file not found: " + fileName
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                "Unable to load locator file: " + fileName, e
            );
        }
    }

    /**
     * Reads an XPath expression from the platform properties file.
     *
     * Example:
     * getLocator("login.username")
     */
    public By getLocator(String key) {

        String xpath = properties.getProperty(key);

        if (xpath == null || xpath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Missing XPath locator '" + key
                    + "' in the platform properties file."
            );
        }

        return By.xpath(xpath.trim());
    }
}