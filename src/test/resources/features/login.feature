Feature: TUI Challenge login form

  @smoke @loginTitle
  Scenario: Verify Login screen title
    Given the TUI Challenge app is launched
    Then the Login screen title should be displayed

  @smoke @validation
  Scenario: Validate all required fields
    Given the TUI Challenge app is launched
    When the user clears all login fields
    And the user submits the login form
    Then required validation errors should be displayed for all fields

  @regression @calendar
  Scenario: Cancel date selection without changing the original DOB
    Given the TUI Challenge app is launched
    When the user remembers the original date of birth
    And the user opens the date of birth calendar
    And the user selects day 10 and cancels the date selection
    Then the original date of birth should remain unchanged

  @regression @calendar
  Scenario: Confirm a new date of birth
    Given the TUI Challenge app is launched
    When the user remembers the original date of birth
    And the user opens the date of birth calendar
    And the user selects day 2 and confirms the date selection
    Then the selected date of birth should be updated

  @regression @password
  Scenario: Toggle password visibility
    Given the TUI Challenge app is launched
    When the user enters a password from test data
    And the user toggles password visibility
    Then the password visibility control should respond correctly

  @smoke @login
  Scenario: Submit valid details and navigate to the destination screen
    Given the TUI Challenge app is launched
    When the user enters valid login details from test data
    And the user submits the login form
    Then the home screen should be displayed