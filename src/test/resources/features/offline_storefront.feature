@offline @storefront
Feature: Storefront behaviour on a stubbed site
  To trust the page objects without touching the live shop
  As a test engineer
  I want basket and search behaviour checked against local stub pages

  # ISTQB: boundary value analysis on quantity, minimum = 1 (values 1 and 2, decrease guarded at 1)
  @boundary @desktop
  Scenario: Customer cannot reduce the tester quantity below the minimum
    Given a desktop customer has one tester in the basket
    Then the quantity is 1
    And the quantity cannot be decreased
    When the customer increases the quantity
    Then the quantity is 2
    When the customer decreases the quantity
    Then the quantity is 1
    And the quantity cannot be decreased

  # ISTQB: state transition (one item -> empty)
  @state-transition @desktop
  Scenario: Customer removes the only tester from the basket
    Given a desktop customer has one tester in the basket
    When the customer removes the tester from the basket
    Then the basket is empty

  # ISTQB: negative testing - input made of symbols only
  @negative @desktop
  Scenario: Customer searches for a term made only of symbols
    Given a desktop customer is on the home page
    When the customer searches for "%$#@!"
    Then the search results page is shown for "%$#@!"
    And the customer is told no results were found for "%$#@!"
