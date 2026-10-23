@basket @regression
Feature: Manage the basket
  To control what I am about to buy
  As a Dulux customer
  I want to change the quantity of a tester or remove it from my basket

  # TC-17 - ISTQB: state transition (empty -> one item -> empty)
  @smoke @desktop
  Scenario: Desktop customer removes the only item from the basket
    Given a desktop customer starts with an empty basket
    When the customer browses to shade "Violet Morning" from colour family "Violet"
    And the customer adds a tester to the basket
    And the customer removes the tester from the basket
    Then the basket is empty

  # TC-18 - ISTQB: boundary value analysis on quantity, minimum = 1 (values 1 and 2, decrease guarded at 1)
  @boundary @desktop
  Scenario: Desktop customer cannot reduce the tester quantity below the minimum
    Given a desktop customer starts with an empty basket
    When the customer browses to shade "Violet Morning" from colour family "Violet"
    And the customer adds a tester to the basket
    Then the quantity is 1
    And the quantity cannot be decreased
    When the customer increases the quantity
    Then the quantity is 2
    When the customer decreases the quantity
    Then the quantity is 1
    And the quantity cannot be decreased

  @smoke @desktop
  Scenario: Desktop customer continues shopping from an empty basket
    Given a desktop customer starts with an empty basket
    When the customer continues shopping
    Then the product listing page is shown
