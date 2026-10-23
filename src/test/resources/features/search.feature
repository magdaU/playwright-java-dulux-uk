@search @regression
Feature: Search for a shade
  To find a colour quickly
  As a Dulux customer
  I want to search the site by shade name

  # TC-13 - ISTQB: equivalence partitioning, valid partition (existing shade name)
  @smoke @desktop
  Scenario: Desktop customer searches for an existing shade
    Given a desktop customer is on the home page
    When the customer searches for "Violet Morning"
    Then the search results page is shown for "Violet Morning"

  # TC-14 - ISTQB: equivalence partitioning, invalid partition (shade that does not exist)
  @negative @desktop
  Scenario: Desktop customer searches for a shade that does not exist
    Given a desktop customer is on the home page
    When the customer searches for "Zzxqvbn Nonexistent"
    Then the search results page is shown for "Zzxqvbn Nonexistent"
    And the customer is told no results were found for "Zzxqvbn Nonexistent"
