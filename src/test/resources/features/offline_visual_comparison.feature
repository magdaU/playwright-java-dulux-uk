@offline @visual
Feature: Visual regression threshold
  To catch layout regressions without failing on rendering noise
  As a test engineer
  I want a screenshot to match its baseline only within the allowed number of changed pixels

  # The allowance is 0.5% of a 200 x 200 baseline, which is 200 pixels.
  # ISTQB: boundary value analysis (just below, at and just above the allowance)
  @boundary @visual-comparison
  Scenario Outline: Screenshot is judged against the changed pixel allowance
    Given a baseline screenshot of 200 by 200 pixels
    When a screenshot of the same size with <changed pixels> changed pixels is compared
    Then the screenshot <outcome>

    Examples:
      | changed pixels | outcome                            |
      | 199            | matches the baseline               |
      | 200            | matches the baseline               |
      | 201            | is rejected as a visual regression |

  # ISTQB: negative testing - a screenshot of a different size can never match
  @negative @visual-comparison
  Scenario: Screenshot of a different size is rejected
    Given a baseline screenshot of 200 by 200 pixels
    When a screenshot of 200 by 201 pixels is compared
    Then the screenshot is rejected as a visual regression
