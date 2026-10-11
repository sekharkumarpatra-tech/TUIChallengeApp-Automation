
package com.tui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.tui.driver.DriverFactory;
import com.tui.utils.LocatorReader;
import com.tui.utils.WaitUtils;

public class LoginPage {

	private final WebDriver driver;
	private final LocatorReader locators;
	private final WaitUtils wait;
	private String originalDOB;

	public LoginPage() {
		driver = DriverFactory.getDriver();

		if (driver == null) {
			throw new IllegalStateException("Driver is not initialized.");
		}

		locators = new LocatorReader(System.getProperty("platformName", "Android"));

		wait = new WaitUtils(driver, 20);
	}

	private By get(String key) {
		return locators.getLocator(key);
	}

	// LOGIN PAGE
	public boolean isLoginPageDisplayed() {
		return wait.isDisplayed(get("login.username")) && wait.isDisplayed(get("login.password"))
				&& wait.isDisplayed(get("login.dobField")) && wait.isDisplayed(get("login.submitButton"));
	}

	public void enterUsername(String username) {
		wait.sendKeys(get("login.username"), username);
	}

	public void enterPassword(String password) {
		wait.sendKeys(get("login.password"), password);
	}

	public void submitLoginForm() {
		wait.click(get("login.submitButton"));
	}

	// REQUIRED-FIELD VALIDATION
	public boolean areAllRequiredErrorsDisplayed() {
		return wait.isDisplayed(get("login.usernameError")) && wait.isDisplayed(get("login.passwordError"))
				&& wait.isDisplayed(get("login.dobError"));
	}

	// DATE OF BIRTH
	public void openDOBCalendar() {
		wait.click(get("login.dobCalendar"));
	}

	public void rememberOriginalDateOfBirth() {
		originalDOB = wait.getText(get("login.dobField"));
	}

	public void selectDay10() {
		wait.click(get("login.day10"));
	}

	public void selectDay2() {
		wait.click(get("login.day2"));
	}

	public void cancelDateSelection() {
		wait.click(get("login.dobCancel"));
	}

	public void confirmDOB() {
		wait.click(get("login.dobConfirm"));
	}
	/*
	 * public void enterDOB(String dob) { wait.sendKeys(get("login.dobField"), dob);
	 * }
	 */
	public void enterDOB(String dob) {
	    wait.click(get("login.dobCalendar"));
	    wait.click(get("login.dobEditMode"));
	    wait.sendKeys(get("login.dobInput"), dob);
	    wait.click(get("login.dobConfirm"));
	}

	public boolean isOriginalDateOfBirthUnchanged() {
		return originalDOB != null && originalDOB.equals(wait.getText(get("login.dobField")));
	}

	public boolean isDateOfBirthUpdated() {
		return originalDOB != null && !originalDOB.equals(wait.getText(get("login.dobField")));
	}

	// PASSWORD VISIBILITY
	public void togglePasswordVisibility() {
		wait.click(get("login.passwordVisibility"));
	}

	public String getPasswordValue() {
		return wait.waitForVisibility(get("login.password")).getAttribute("text");
	}

	// DESTINATION SCREEN
	public boolean isLoginTitleDisplayed() {
	    return wait.isDisplayed(get("login.loginText"));
	}
}