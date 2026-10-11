package com.tui.stepdefinitions;

import org.testng.Assert;

import com.tui.pages.HomePage;

import io.cucumber.java.en.Then;

public class HomeSteps {

    private final HomePage homePage;

    public HomeSteps() {
        homePage = new HomePage();
    }

    // Verify that the Search Results screen is displayed
    @Then("the search results screen should be displayed")
    public void verifySearchResultsScreen() {
        Assert.assertTrue(
                homePage.isResultsScreenDisplayed(),
                "Search Results screen was not displayed."
        );
    }

    // Verify that all navigation tabs are displayed
    @Then("the search results navigation tabs should be displayed")
    public void verifyResultsNavigationTabs() {
        Assert.assertTrue(
                homePage.areNavigationTabsDisplayed(),
                "One or more navigation tabs are missing."
        );
    }

    // Verify the first hotel's details
    @Then("the first hotel details should be displayed")
    public void verifyFirstHotelDetails() {
        Assert.assertFalse(
                homePage.getFirstHotelName().isEmpty(),
                "First hotel name is empty."
        );

        Assert.assertFalse(
                homePage.getFirstHotelDestination().isEmpty(),
                "First hotel destination is empty."
        );

        Assert.assertFalse(
                homePage.getFirstHotelRating().isEmpty(),
                "First hotel rating is empty."
        );

        Assert.assertFalse(
                homePage.getFirstHotelBoardType().isEmpty(),
                "First hotel board type is empty."
        );
    }

    // Scenario 5: Scroll the Search Results screen once
    @Then("the search results should be scrolled")
    public void scrollSearchResults() {
        homePage.scrollResultsToQueensPark();
    }

    // Scenario 6: Find Queens Park by scrolling
    @Then("the Queens Park hotel should be displayed after scrolling")
    public void verifyQueensParkAfterScrolling() {
        Assert.assertTrue(
                homePage.scrollResultsToQueensPark(),
                "Queens Park hotel was not found after scrolling."
        );
    }
}