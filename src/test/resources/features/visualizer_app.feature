Feature: Dulux Visualizer App link
  As a customer viewing a colour on the Dulux website
  I want to click the Visualizer App link
  So that I can see how the colour looks in my room

  @desktop
  Scenario: Visualizer App opens in a new tab on desktop
    Given the viewport is desktop
    And the customer is on the home page
    And cookies are rejected
    And the customer searches for "Gentle Lavender"
    When the customer clicks "Try our Visualizer App"
    Then the Visualizer App opens in a new tab
    And the new tab URL is "https://www.dulux.co.uk/en/articles/dulux-visualizer-app"

  @mobile
  Scenario: Error is shown when opening Visualizer App on mobile
    Given the viewport is mobile
    And the customer is on the home page
    And cookies are rejected
    And the customer searches for "Gentle Lavender"
    When the customer clicks "Try our Visualizer App"
    Then an error message is displayed
    And the error contains "Inconsistent store data, contact support@adjust.com"

