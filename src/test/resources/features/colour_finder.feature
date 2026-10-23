@colour-finder @regression
Feature: Find a colour
  To start choosing a paint colour
  As a Dulux customer
  I want to open the colour finder and see the available colour families

  @smoke @desktop
  Scenario: Desktop customer opens the colour finder
    Given a desktop customer is on the home page
    When the customer opens the colour finder
    Then the colour family "Violet" is available
