# ✅ Test Cases

> Individual test case specifications — the automated ones map 1:1 to the Gherkin
> scenarios in [`features/`](../src/test/resources/features) and the `VisualRegressionTest`
> JUnit tests (§3.1 of the [Test Strategy](TEST_STRATEGY.md#3-scope)); a further set are
> documented as **manual/candidate** cases that close gaps identified in
> [Test Strategy §14](TEST_STRATEGY.md#14-coverage-gaps--improvement-opportunities) but
> aren't automated today. See the [Features Guide](FEATURES_GUIDE.md) for what each area
> under test actually does. TC-27 – TC-34 are the offline (`@offline`) scenarios that run
> against local stub pages.

**Legend — Priority:** P1 critical path · P2 important · P3 nice-to-have.
**Legend — Automation:** ✅ automated (linked) · 🟡 candidate for automation · ⚪ manual/exploratory only.

---

## Purchase journey

### TC-01 — Desktop customer adds a tester from the colour finder
| | |
|---|---|
| **Priority** | P1 |
| **Automation** | ✅ [`tester_purchase.feature` — Scenario 1](../src/test/resources/features/tester_purchase.feature); also `TesterProductTest` |
| **Preconditions** | Desktop viewport (`1920×1080`); basket is empty; cookie banner not yet dismissed |
| **Steps** | 1. Open the home page and reject cookies.<br>2. Open "Find a colour" from the top nav.<br>3. Select colour family "Violet".<br>4. Select shade "Violet Morning".<br>5. Click "Buy a Tester in this colour".<br>6. Dismiss the confirmation alert.<br>7. Open the shopping cart. |
| **Expected result** | Basket contains exactly 1 item; basket shows tester "Dulux Colour Tester" for shade "Violet Morning" |

### TC-02 — Mobile customer adds a tester from the colour finder
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ [`tester_purchase.feature` — Scenario 2](../src/test/resources/features/tester_purchase.feature); also `TesterProductTest` |
| **Preconditions** | Mobile viewport (`375×667`); basket is empty |
| **Steps** | Same as TC-01, but "Find a colour" is reached via the hamburger menu, not the top nav |
| **Expected result** | Same as TC-01 |

### TC-08 — Basket increment/decrement/remove *(superseded by TC-17 and TC-18)*
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ split into [TC-17](#tc-17--removing-the-only-item-returns-the-basket-to-the-empty-state) and [TC-18](#tc-18--quantity-cannot-go-below-the-minimum-of-1) |
| **Preconditions** | Basket already contains 1 tester (result of TC-01) |
| **Steps** | 1. Increase quantity via the quantity control.<br>2. Decrease quantity back to 1.<br>3. Remove the item entirely. |
| **Expected result** | Quantity updates correctly at each step; basket shows the "empty" state after removal |

### TC-09 — Add-to-basket failure is surfaced to the customer *(manual/candidate)*
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | 🟡 candidate — via `page.route()` interception, see [Test Strategy §14](TEST_STRATEGY.md#14-coverage-gaps--improvement-opportunities) |
| **Preconditions** | Customer is on a shade page; the add-to-basket network call can be intercepted |
| **Steps** | 1. Force the add-to-basket request to fail (mocked error response).<br>2. Click "Buy a Tester in this colour". |
| **Expected result** | The customer sees a visible error/failure indication — not a silent no-op |

### TC-10 — Shade with no tester option *(manual/exploratory)*
| | |
|---|---|
| **Priority** | P3 |
| **Automation** | ⚪ manual — data-dependent on which shades currently lack a tester |
| **Preconditions** | A shade known to expose only "Find Products in this colour", no "Buy a Tester" button — catalogue can drift; the [Python sibling](https://github.com/magdaU/playwright-python-dulux-uk) has hit exactly this class of issue |
| **Steps** | 1. Navigate to that shade's page.<br>2. Confirm no "Buy a Tester" control is present. |
| **Expected result** | Confirms the assumption behind the pinned-shade decision still holds; re-run whenever choosing a new pinned shade |

---

## Visualizer journey

### TC-03 — Desktop customer opens the Visualizer for a shade
| | |
|---|---|
| **Priority** | P1 |
| **Automation** | ✅ [`visualizer_experience.feature` — Scenario 1](../src/test/resources/features/visualizer_experience.feature); also `VisualizerAppTest` |
| **Preconditions** | Desktop viewport; customer is viewing shade "Violet Morning" (reached via search) |
| **Steps** | 1. Click "Try our Visualizer App". |
| **Expected result** | Visualizer opens in a **new browser tab**, at `https://www.dulux.co.uk/en/articles/dulux-visualizer-app` |

### TC-04 — Mobile customer tries to open the Visualizer for a shade
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ [`visualizer_experience.feature` — Scenario 2](../src/test/resources/features/visualizer_experience.feature); also `VisualizerAppTest` |
| **Preconditions** | Mobile viewport; customer is viewing shade "Violet Morning" |
| **Steps** | 1. Click "Try our Visualizer App". |
| **Expected result** | No new tab; the page shows the message "Inconsistent store data, contact support@adjust.com" — documented, observed behaviour (see [Features Guide](FEATURES_GUIDE.md#visualizer-app)) |

---

## Visual appearance

### TC-05 — Empty cart page matches its approved baseline
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ `VisualRegressionTest.emptyCartPage_shouldMatchBaseline` |
| **Preconditions** | Desktop viewport; cart is empty |
| **Steps** | 1. Open the cart page and reject cookies.<br>2. Capture a full-page screenshot.<br>3. Compare against `cart-page-empty.png`. |
| **Expected result** | No pixel-diff beyond the tolerance configured in `VisualComparisonUtil`; job is non-blocking so a genuine layout change surfaces as an artifact, not a red build |

### TC-06 — Colour finder landing page matches its approved baseline
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ `VisualRegressionTest.colourFinderPage_shouldMatchBaseline` |
| **Preconditions** | Desktop viewport |
| **Steps** | 1. Open the home page, reject cookies, navigate to "Find a colour".<br>2. Capture and compare against `colour-finder-page.png`. |
| **Expected result** | Same as TC-05 |

### TC-07 — Violet shade grid matches its approved baseline
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ `VisualRegressionTest.shadeSelectionPage_shouldMatchBaseline` |
| **Preconditions** | Desktop viewport |
| **Steps** | 1. Navigate to "Find a colour", select "Violet".<br>2. Capture and compare against `violet-shade-selection-page.png`. |
| **Expected result** | Same as TC-05 |

---

## Navigation & search

### TC-11 — Site search returns the searched shade *(superseded by TC-13 and TC-14)*
| | |
|---|---|
| **Priority** | P2 |
| **Automation** | ✅ see [TC-13](#tc-13--search-for-an-existing-shade) and [TC-14](#tc-14--search-for-a-shade-that-does-not-exist) |
| **Preconditions** | On the home page |
| **Steps** | 1. Open search.<br>2. Enter a known shade name.<br>3. Press Enter. |
| **Expected result** | Navigates to that shade's detail page |

### TC-12 — Cookie banner blocks interaction until dismissed *(manual/exploratory)*
| | |
|---|---|
| **Priority** | P3 |
| **Automation** | ⚪ manual — implicitly covered by every scenario rejecting cookies first, but never asserted as a standalone case |
| **Preconditions** | Fresh browser context, cookie banner not yet interacted with |
| **Steps** | 1. Attempt to interact with the page behind the banner before dismissing it. |
| **Expected result** | The banner blocks the intended interaction until "Reject All" is clicked |

### TC-13 — Search for an existing shade
| | |
|---|---|
| **Priority** | P1 · `@smoke` |
| **ISTQB technique** | Equivalence partitioning — valid partition (existing shade name) |
| **Automation** | ✅ [`search.feature` — Scenario 1](../src/test/resources/features/search.feature) |
| **Steps** | 1. Open the home page, reject cookies.<br>2. Search for "Violet Morning". |
| **Expected result** | Customer lands on `/en/search-results?search=…` with the heading "search for 'Violet Morning'" |
| **Production load** | 1 navigation + 1 search request |

### TC-14 — Search for a shade that does not exist
| | |
|---|---|
| **Priority** | P2 · `@negative` |
| **ISTQB technique** | Equivalence partitioning — invalid partition (no such shade) |
| **Automation** | ✅ [`search.feature` — Scenario 2](../src/test/resources/features/search.feature) |
| **Steps** | 1. Open the home page, reject cookies.<br>2. Search for "Zzxqvbn Nonexistent". |
| **Expected result** | Results page for that term shows "Sorry, we couldn't find any results for '…'" — no error page |
| **Production load** | 1 navigation + 1 search request. One representative value only: no hostile input, no long strings |

### TC-17 — Removing the only item returns the basket to the empty state
| | |
|---|---|
| **Priority** | P1 · `@smoke` |
| **ISTQB technique** | State transition — empty → one item → empty |
| **Automation** | ✅ [`basket.feature` — Scenario 1](../src/test/resources/features/basket.feature) |
| **Steps** | As TC-01, then click "Remove" on the basket item |
| **Expected result** | "Your basket is empty" is shown and the quantity control is gone |
| **Production load** | Same journey as TC-01 plus one remove action |

### TC-18 — Quantity cannot go below the minimum of 1
| | |
|---|---|
| **Priority** | P2 · `@boundary` |
| **ISTQB technique** | Boundary value analysis — quantity minimum is 1: values 1 (min) and 2 (min + 1); the decrease control is disabled at the minimum |
| **Automation** | ✅ [`basket.feature` — Scenario 2](../src/test/resources/features/basket.feature) |
| **Steps** | As TC-01, then: increase to 2, decrease back to 1 |
| **Expected result** | At 1 the "Decrease quantity" button is disabled; quantity shows 2 after increase and 1 after decrease |
| **Production load** | Same journey as TC-01 plus two single clicks. The upper boundary is deliberately not probed on production |

### TC-26 — Colour finder opens and lists colour families
| | |
|---|---|
| **Priority** | P1 · `@smoke` |
| **ISTQB technique** | Smoke test of the entry point to the main customer journey |
| **Automation** | ✅ [`colour_finder.feature`](../src/test/resources/features/colour_finder.feature) |
| **Steps** | 1. Open the home page, reject cookies.<br>2. Open "Find a colour" from the top nav. |
| **Expected result** | The colour family "Violet" is available on the colour finder page |
| **Production load** | 2 navigations, no basket changes |

### TC-24 — Visual comparison threshold boundary
| | |
|---|---|
| **Priority** | P3 · `@unit` |
| **ISTQB technique** | Boundary value analysis (2-value) on the 0.5 % different-pixel allowance |
| **Automation** | ✅ `VisualComparisonUtilBoundaryTest` |
| **Expected result** | A difference exactly at the allowance (200 of 40 000 px) matches; one pixel more fails |
| **Production load** | None — in-memory images |

### TC-25 — Headless switch partitions
| | |
|---|---|
| **Priority** | P3 · `@unit` |
| **ISTQB technique** | Equivalence partitioning — valid value ("true") and invalid free text |
| **Automation** | ✅ `PlaywrightConfigTest` |
| **Expected result** | "true" enables headless; a non-boolean value falls back to headed |
| **Production load** | None |

---

## Offline stubbed checks

These scenarios are tagged `@offline`: an `OfflineSite` route handler serves the pages from
[`src/test/resources/stubs/`](../src/test/resources/stubs) so nothing reaches the live site
(see [Architecture](architecture.md)). They are not tagged `@smoke`, so the default CI run
does not execute them; run them with `-Dcucumber.filter.tags=@offline`.

### TC-27 — Stubbed basket enforces the quantity minimum
| | |
|---|---|
| **Priority** | P3 · `@boundary` |
| **ISTQB technique** | Boundary value analysis — quantity minimum is 1, decrease guarded at 1 |
| **Automation** | ✅ [`offline_storefront.feature` — Scenario 1](../src/test/resources/features/offline_storefront.feature) |
| **Steps** | Basket with one tester: increase to 2, decrease back to 1 |
| **Expected result** | At 1 the decrease control is disabled; quantity is 2 after increase and 1 after decrease |
| **Production load** | None — served from local stubs |

### TC-28 — Stubbed basket: removing the only tester empties it
| | |
|---|---|
| **Priority** | P3 · `@state-transition` |
| **ISTQB technique** | State transition — one item → empty |
| **Automation** | ✅ [`offline_storefront.feature` — Scenario 2](../src/test/resources/features/offline_storefront.feature) |
| **Steps** | Basket with one tester: remove the tester |
| **Expected result** | The empty-basket message is shown |
| **Production load** | None — served from local stubs |

### TC-29 — Stubbed basket: tester removed after the quantity was increased
| | |
|---|---|
| **Priority** | P3 · `@state-transition` |
| **ISTQB technique** | State transition — one item → two items → empty |
| **Automation** | ✅ [`offline_storefront.feature` — Scenario 3](../src/test/resources/features/offline_storefront.feature) |
| **Steps** | Basket with one tester: increase quantity to 2, remove the tester |
| **Expected result** | Quantity shows 2 after the increase; the empty-basket message is shown after removal |
| **Production load** | None — served from local stubs |

### TC-30 — Stubbed search for a term made only of symbols
| | |
|---|---|
| **Priority** | P3 · `@negative` |
| **ISTQB technique** | Negative testing — symbol-only input |
| **Automation** | ✅ [`offline_storefront.feature` — Scenario 4](../src/test/resources/features/offline_storefront.feature) |
| **Steps** | Open the home page, search for `%$#@!` |
| **Expected result** | The results page is shown for the term with the "no results found" message |
| **Production load** | None — served from local stubs |

### TC-31 — Unrecognised headless setting opens a visible browser
| | |
|---|---|
| **Priority** | P3 · `@negative` `@headless-setting` |
| **ISTQB technique** | Equivalence partitioning — invalid partitions ("yes" and an empty value) |
| **Automation** | ✅ [`offline_configuration.feature` — Scenario Outline](../src/test/resources/features/offline_configuration.feature) |
| **Steps** | Set the headless setting to each example value |
| **Expected result** | The browser runs in headed mode |
| **Production load** | None — served from local stubs |

### TC-32 — Upper case headless setting runs the browser headless
| | |
|---|---|
| **Priority** | P3 · `@equivalence` `@headless-setting` |
| **ISTQB technique** | Equivalence partitioning — valid partition, case insensitive ("TRUE") |
| **Automation** | ✅ [`offline_configuration.feature` — Scenario 2](../src/test/resources/features/offline_configuration.feature) |
| **Steps** | Set the headless setting to "TRUE" |
| **Expected result** | The browser runs in headless mode |
| **Production load** | None — served from local stubs |

### TC-33 — Screenshot is judged against the changed-pixel allowance
| | |
|---|---|
| **Priority** | P3 · `@boundary` `@visual-comparison` |
| **ISTQB technique** | Boundary value analysis — 199, 200 and 201 changed pixels of a 200 × 200 baseline (allowance 200 px) |
| **Automation** | ✅ [`offline_visual_comparison.feature` — Scenario Outline](../src/test/resources/features/offline_visual_comparison.feature) |
| **Steps** | Compare a same-size screenshot with each example's number of changed pixels |
| **Expected result** | 199 and 200 match the baseline; 201 is rejected as a visual regression |
| **Production load** | None — served from local stubs |

### TC-34 — Screenshot of a different size is rejected
| | |
|---|---|
| **Priority** | P3 · `@negative` `@visual-comparison` |
| **ISTQB technique** | Negative testing — size mismatch |
| **Automation** | ✅ [`offline_visual_comparison.feature` — Scenario 2](../src/test/resources/features/offline_visual_comparison.feature) |
| **Steps** | Compare a 200 × 201 screenshot with a 200 × 200 baseline |
| **Expected result** | Rejected as a visual regression |
| **Production load** | None — served from local stubs |

---

## See also

- [Test Strategy §3](TEST_STRATEGY.md#3-scope) — scenario-level tags and viewport matrix.
- [Test Results](TEST_RESULTS.md) — latest execution status per test case.
- [Test Summary Report](TEST_SUMMARY_REPORT.md) — cycle-level outcome and sign-off.
