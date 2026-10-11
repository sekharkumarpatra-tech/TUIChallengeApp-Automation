
package com.tui.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIRECTORY = "target/screenshots";

    private ScreenshotUtils() {
        // Utility class: prevent object creation.
    }

    public static String captureScreenshot(
            WebDriver driver, String screenshotName) {

        if (driver == null) {
            throw new IllegalArgumentException(
                    "WebDriver cannot be null when capturing a screenshot.");
        }

        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalArgumentException(
                    "The current driver does not support screenshots.");
        }

        try {
            Path directory = Paths.get(SCREENSHOT_DIRECTORY);
            Files.createDirectories(directory);

            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

            String safeName = screenshotName.replaceAll(
                    "[^a-zA-Z0-9._-]", "_");

            Path destination = directory.resolve(
                    safeName + "_" + timestamp + ".png");

            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);

            return destination.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save screenshot: " + screenshotName, e);
        }
    }
}