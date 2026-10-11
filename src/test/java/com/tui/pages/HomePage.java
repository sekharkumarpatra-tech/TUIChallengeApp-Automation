
package com.tui.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import com.tui.driver.DriverFactory;
import com.tui.utils.LocatorReader;
import com.tui.utils.WaitUtils;

public class HomePage {

    private final WebDriver driver;
    private final LocatorReader locatorReader;
    private final WaitUtils waitUtils;

    // Constructor
    public HomePage() {

        this.driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                    "Driver is not initialized."
            );
        }

        this.locatorReader = new LocatorReader(
                System.getProperty("platformName", "Android")
        );

        this.waitUtils = new WaitUtils(driver, 20);
    }

    // Get locator from the platform-specific properties file
    private By get(String key) {
        return locatorReader.getLocator(key);
    }

    // Verify Search Results screen
    public boolean isResultsScreenDisplayed() {
        return waitUtils.isDisplayed(get("home.screen"));
    }

    // Verify All, Hotels and Holidays tabs
    public boolean areNavigationTabsDisplayed() {

        return waitUtils.isDisplayed(get("home.allTab"))
                && waitUtils.isDisplayed(get("home.hotelsTab"))
                && waitUtils.isDisplayed(get("home.holidaysTab"));
    }

    // Get first hotel details
    public String getFirstHotelName() {
        return waitUtils.getText(get("home.hotelName0"));
    }

    public String getFirstHotelDestination() {
        return waitUtils.getText(get("home.hotelDestination0"));
    }

    public String getFirstHotelRating() {
        return waitUtils.getText(get("home.hotelRating0"));
    }

    public String getFirstHotelBoardType() {
        return waitUtils.getText(get("home.hotelBoardType0"));
    }

    // Verify that the results container is scrollable
    public boolean isResultsContentScrollable() {

        return "true".equals(
                driver.findElement(get("home.resultsContent"))
                        .getAttribute("scrollable")
        );
    }

    // Check whether Queens Park is displayed
    // public boolean isQueensParkHotelDisplayed() {
    //     return waitUtils.isDisplayed(get("home.queensParkHotel"));
    // }
    //
    // Perform one upward swipe in Search Results
    // public void scrollResults() {
    //
    //     By resultsLocator = get("home.resultsContent");
    //
    //     WebElement results = driver.findElement(resultsLocator);
    //
    //     ((JavascriptExecutor) driver).executeScript(
    //             "mobile: scrollGesture",
    //             java.util.Map.of(
    //                     "elementId",
    //                     ((RemoteWebElement) results).getId(),
    //                     "direction", "down",
    //                     "percent", 0.98
    //             )
    //     );
    // }

    // Check whether Queens Park is displayed
 // Check whether Queens Park is displayed
    public boolean isQueensParkHotelDisplayed() {

        for (WebElement hotel :
                driver.findElements(get("home.queensParkHotel"))) {

            if (hotel.isDisplayed()) {
                return true;
            }
        }

        return false;
    }


    // Perform one swipe in Search Results using W3C touch actions
    public void scrollResults() {

        By resultsLocator = get("home.resultsContent");
        WebElement results = driver.findElement(resultsLocator);

        Point location = results.getLocation();
        Dimension size = results.getSize();

        int startX = location.getX() + (size.getWidth() / 2);
        int startY = location.getY() + (int) (size.getHeight() * 0.85);
        int endY = location.getY() + (int) (size.getHeight() * 0.15);

        PointerInput finger =
                new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                )
        );

        swipe.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(500),
                        PointerInput.Origin.viewport(),
                        startX,
                        endY
                )
        );

        swipe.addAction(
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        ((RemoteWebDriver) driver).perform(List.of(swipe));

        System.out.println(
                "One swipe performed. StartY=" + startY
                        + ", EndY=" + endY
        );
    }


    // Scroll repeatedly until Queens Park is found
    public boolean scrollResultsToQueensPark() {

        int maxScrolls = 15;

        for (int i = 0; i <= maxScrolls; i++) {

            // Check before scrolling
            if (isQueensParkHotelDisplayed()) {
                System.out.println(
                        "Queens Park found after " + i + " scroll(s)."
                );
                return true;
            }

            if (i == maxScrolls) {
                break;
            }

            // Perform one swipe
            scrollResults();

            System.out.println("Scroll attempt: " + (i + 1));
        }

        // Final check
        boolean found = isQueensParkHotelDisplayed();

        if (!found) {
            System.out.println(
                    "Queens Park was not found after "
                            + maxScrolls + " scroll attempts."
            );
        }

        return found;
    }
}