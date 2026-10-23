@cookies @regression
Feature: Cookie consent
  To stay in control of my data
  As a Dulux customer
  I want to be able to reject non-essential cookies

  @smoke @desktop
  Scenario: Desktop customer rejects all cookies
    Given a desktop customer opens the home page for the first time
    Then the cookie banner is shown
    When the customer rejects all cookies
    Then the cookie banner is hidden
