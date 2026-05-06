Feature: Add colour tester to cart
  As a customer browsing Dulux colours
  I want to add a colour tester to my cart
  So that I can try the colour before buying a full tin

  Background:
    Given the cart page is open
    And cookies are rejected
    And the basket is empty

  @desktop
  Scenario: Customer adds a colour tester on desktop
    When the customer navigates to the home page on desktop
    And opens "Find a colour" from the top navigation
    And selects colour group "Violet"
    And selects shade "Gentle Lavender"
    And clicks "Buy a Tester in this colour"
    And closes the confirmation alert
    And opens the shopping cart
    Then the cart contains 1 item
    And the cart contains product "Dulux Colour Tester"
    And the cart contains shade "Gentle Lavender"

  @mobile
  Scenario: Customer adds a colour tester on mobile
    When the customer navigates to the home page on mobile
    And opens the hamburger menu
    And opens "Find a colour" from the mobile navigation
    And selects colour group "Violet"
    And selects shade "Gentle Lavender"
    And clicks "Buy a Tester in this colour"
    And closes the confirmation alert
    And opens the shopping cart
    Then the cart contains 1 item
    And the cart contains product "Dulux Colour Tester"
    And the cart contains shade "Gentle Lavender"

