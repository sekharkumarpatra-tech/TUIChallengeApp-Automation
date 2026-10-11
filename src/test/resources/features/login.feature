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

  @regression @password
  Scenario: Toggle password visibility
    Given the TUI Challenge app is launched
    When the user enters a password from test data
    And the user toggles password visibility
    Then the password visibility control should respond correctly

  @smoke @validLogin
  Scenario: Successful login displays search results and hotel details
    Given the TUI Challenge app is launched
    When the user enters valid login details from test data
    And the user submits the login form
    Then the search results screen should be displayed
    And the search results navigation tabs should be displayed
    And the first hotel details should be displayed

  @smoke @hotelSearch
  Scenario: Scroll through search results
    Given the TUI Challenge app is launched
    When the user enters valid login details from test data
    And the user submits the login form
    Then the search results screen should be displayed
    And the search results should be scrolled

  @smoke @hotelSearch
  Scenario: Find Queens Park hotel by scrolling
    Given the TUI Challenge app is launched
    When the user enters valid login details from test data
    And the user submits the login form
    Then the search results screen should be displayed
    And the Queens Park hotel should be displayed after scrolling