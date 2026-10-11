
package com.tui.stepdefinitions;

import org.testng.Assert;

import com.tui.pages.LoginPage;
import com.tui.utils.TestDataReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class LoginSteps {

    private LoginPage loginPage;

    // =========================================================
    // COMMON SETUP - VERIFY LOGIN PAGE
    // =========================================================

    @Given("the TUI Challenge app is launched")
    public void appIsLaunched() {

        loginPage = new LoginPage();

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page was not displayed."
        );
    }

    // =========================================================
    // SCENARIO 1 - REQUIRED-FIELD VALIDATION
    // =========================================================

    @When("the user clears all login fields")
    public void clearLoginFields() {

        String username = TestDataReader.getString(
                "login-data.json", "emptyLogin", "username"
        );

        String password = TestDataReader.getString(
                "login-data.json", "emptyLogin", "password"
        );

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user submits the login form")
    public void submitLoginForm() {
        loginPage.submitLoginForm();
    }

    @Then("required validation errors should be displayed for all fields")
    public void verifyRequiredErrors() {

        Assert.assertTrue(
                loginPage.areAllRequiredErrorsDisplayed(),
                "Required validation errors were not displayed."
        );
    }

    // =========================================================
    // SCENARIO 2 - CANCEL DOB SELECTION
    // =========================================================

    @When("the user remembers the original date of birth")
    public void rememberOriginalDOB() {
        loginPage.rememberOriginalDateOfBirth();
    }

    @When("the user opens the date of birth calendar")
    public void openDOBCalendar() {
        loginPage.openDOBCalendar();
    }

    @When("the user selects day 10 and cancels the date selection")
    public void selectDay10AndCancel() {
        loginPage.selectDay10();
        loginPage.cancelDateSelection();
    }

    @Then("the original date of birth should remain unchanged")
    public void verifyOriginalDOB() {

        Assert.assertTrue(
                loginPage.isOriginalDateOfBirthUnchanged(),
                "Date of birth changed after cancellation."
        );
    }

    // =========================================================
    // SCENARIO 3 - CONFIRM DOB SELECTION
    // =========================================================

    @When("the user selects day 2 and confirms the date selection")
    public void selectDay2AndConfirm() {
        loginPage.selectDay2();
        loginPage.confirmDOB();
    }

    @Then("the selected date of birth should be updated")
    public void verifyUpdatedDOB() {

        Assert.assertTrue(
                loginPage.isDateOfBirthUpdated(),
                "Date of birth was not updated."
        );
    }

    // =========================================================
    // SCENARIO 4 - PASSWORD VISIBILITY
    // =========================================================

    @When("the user enters a password from test data")
    public void enterPassword() {

        String password = TestDataReader.getString(
                "login-data.json", "validLogin", "password"
        );

        loginPage.enterPassword(password);
    }

    @When("the user toggles password visibility")
    public void togglePasswordVisibility() {
        loginPage.togglePasswordVisibility();
    }

    @Then("the password visibility control should respond correctly")
    public void verifyPasswordVisibility() {

        String expectedPassword = TestDataReader.getString(
                "login-data.json", "validLogin", "password"
        );

        Assert.assertEquals(
                loginPage.getPasswordValue(),
                expectedPassword,
                "Displayed password does not match the JSON test data."
        );
    }

    // =========================================================
    // SCENARIO 5 - ENTER VALID LOGIN DETAILS
    // =========================================================

    @When("the user enters valid login details from test data")
    public void enterValidLoginDetails() {

        String username = TestDataReader.getString(
                "login-data.json", "validLogin", "username"
        );

        String password = TestDataReader.getString(
                "login-data.json", "validLogin", "password"
        );

        String dob = TestDataReader.getString(
                "login-data.json", "validLogin", "dob"
        );

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.enterDOB(dob);
    }

    @Then("the Login screen title should be displayed")
    public void verifyLoginScreenTitle() {

        Assert.assertTrue(
                loginPage.isLoginTitleDisplayed(),
                "Login screen title was not displayed."
        );
    }
}
