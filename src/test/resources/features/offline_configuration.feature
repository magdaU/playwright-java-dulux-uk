@offline @configuration
Feature: Browser mode setting
  To run the suite the way I expect
  As a test engineer
  I want the headless setting to be read safely, whatever value it is given

  # ISTQB: equivalence partitioning on the headless setting - invalid partitions fall back to a visible browser
  @negative @headless-setting
  Scenario Outline: Unrecognised headless setting opens a visible browser
    Given the headless setting is "<value>"
    Then the browser runs in headed mode

    Examples:
      | value |
      | yes   |
      |       |

  # ISTQB: equivalence partitioning, valid partition - the value is not case sensitive
  @equivalence @headless-setting
  Scenario: Upper case headless setting runs the browser headless
    Given the headless setting is "TRUE"
    Then the browser runs in headless mode
