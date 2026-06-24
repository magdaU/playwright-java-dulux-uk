# 🧭 Test Strategy — Dulux UK E2E Automation

> Living document. It describes **what** we test, **why**, and **how** the automation
> framework in this repository is designed to deliver fast, trustworthy feedback on the
> [Dulux UK](https://www.dulux.co.uk) customer journeys.

| | |
|---|---|
| **Product under test** | Dulux UK e-commerce website (`https://www.dulux.co.uk`) |
| **Test type** | UI end-to-end (black-box, browser-driven) |
| **Framework** | Playwright for Java · Cucumber BDD · JUnit 5 · Allure |
| **Pipeline** | GitHub Actions → smoke suite on every push/PR, report published to GitHub Pages |
| **Owner** | QA / SDET |
| **Status** | Active |

---

## 1. Purpose & objectives

The goal of this automation is **not** to test everything the Dulux site does — it is to
continuously prove that the **highest-value customer journeys still work** across the
viewports our customers actually use.

Quality objectives, in priority order:

1. **Protect revenue paths** — a customer must always be able to find a colour and add a
   tester to the basket. This is the critical, money-making flow.
2. **Protect cross-device parity** — the same journey must work on desktop and mobile,
   which use *different* navigation (top nav vs. hamburger menu).
3. **Fast, actionable feedback** — every push runs the smoke suite headless in CI in
   minutes, with a screenshot on any failure and a published Allure report.
4. **Trustworthy results** — a red build means a real regression, not a flaky test. Test
   stability is treated as a first-class feature, not an afterthought.

---

## 2. System under test (SUT)

The SUT is the **live, public production** Dulux UK website. This is deliberate (it gives
realistic coverage) but it is also the single biggest source of risk (see §10).

Characteristics that shape the test design:

- JavaScript-heavy SPA-style storefront with client-side navigation.
- A cookie consent banner that blocks interaction until dismissed.
- "Find a colour" flow that triggers a **full page navigation** (not a dropdown).
- A Visualizer link that opens in a **new browser tab**.
- Responsive layouts: desktop exposes a top navigation bar; mobile collapses it behind a
  hamburger menu.
- Third-party dependencies (analytics, the Adjust-powered Visualizer) that can return
  environment-specific messages.

---

## 3. Scope

### ✅ In scope

| Area | Covered journeys |
|---|---|
| **Tester purchase** | Browse to a shade via the colour finder and add a tester to the basket (desktop + mobile) |
| **Visualizer experience** | Open the Visualizer from a selected shade page (desktop opens a new tab; mobile surfaces the store-data message) |
| **Cross-viewport** | Every journey runs at desktop `1920×1080` and mobile `375×667` |
| **Cookie consent** | Implicitly exercised — every journey rejects cookies before proceeding |

### ❌ Out of scope (for this suite)

- Checkout, payment and order fulfilment (no transactions against production).
- Account creation, login and profile management.
- API / service-level, contract, unit and component testing (the app is third-party; we
  own no production code to unit-test).
- Performance, load and stress testing.
- Accessibility (a11y) and full cross-browser matrix — **candidates for the roadmap (§13)**.
- Visual regression / pixel comparison.

> **Note on the test pyramid.** This repository is intentionally an **E2E layer only**,
> because we do not own the application code. We compensate for the known cost of E2E
> (slower, more brittle) by keeping the suite small, journey-focused, and tag-sliced so the
> critical `@smoke` set stays fast.

---

## 4. Test approach

### 4.1 Levels & style

- **Behaviour-Driven (BDD)** — journeys are described in business-readable Gherkin
  (`*.feature`) so intent is reviewable by non-engineers. Cucumber step definitions are the
  glue; the [`CucumberRunner`](../src/test/java/com/github/magdalena/cucumber/CucumberRunner.java)
  drives them via the JUnit Platform.
- **Parallel JUnit tests** — the same journeys also exist as plain JUnit 5 tests
  (`TesterProductTest`, `VisualizerAppTest`) annotated with rich Allure metadata
  (`@Epic`, `@Feature`, `@Story`, `@Severity`). This gives two entry points: BDD for
  living documentation, JUnit for fine-grained Allure storytelling.

### 4.2 Design principles

| Principle | How it's applied |
|---|---|
| **Page Object Model** | Each page (`HomePage`, `ColorSelectionPage`, `CartPage`) and reusable component (`NavigationComponent`, `AlertComponent`) extends a shared `BasePage`. UI locators live in one place. |
| **No assertions in page objects** | Pages only *act* and *expose* locators. All assertions live in the test/step layer (AssertJ + Playwright web-first assertions). |
| **Single shared state** | `CucumberContext` holds browser/viewport state and high-level business methods, injected into every step class via PicoContainer DI. |
| **Role-based locators** | Locators prefer `getByRole` / `getByLabel` / `getByText` over brittle CSS/XPath, matching how a user perceives the page and surviving DOM churn. |
| **Web-first waits** | Playwright's auto-waiting assertions replace manual sleeps; explicit `waitForLoadState()` is used only where a real navigation occurs. |

### 4.3 Test design techniques

- **Scenario / user-journey based** — each test mirrors a real customer task end to end.
- **Equivalence partitioning** — one representative shade (`Gentle Lavender` / `Violet`)
  stands in for the colour-finder space; the *flow*, not the data permutation, is the risk.
- **State verification** — basket starts empty → exactly 1 item with the right product and
  shade after adding (guards against silent over/under-counting).
- **Cross-configuration testing** — desktop vs. mobile as distinct navigation paths.

---

## 5. Test data strategy

- **Data is inline and self-describing** in the Gherkin and tests (shade name, colour
  family, expected product label) — no external fixtures to drift out of sync.
- **No persistent data is created** on production: the basket flow stops *before* checkout,
  and each scenario runs in a fresh, isolated `BrowserContext` (no shared cookies/storage),
  so runs never contaminate one another.
- **Self-cleaning** — because no order is placed and contexts are disposed after every
  scenario, there is no teardown/data-reset burden.

---

## 6. Environments

| Environment | Where | Purpose |
|---|---|---|
| **Local (headed)** | Developer machine, `headless=false` by default | Authoring and debugging — watch the journey run |
| **Local (headless)** | `-Dheadless=true` | Fast local verification before pushing |
| **Docker** | `docker compose up --build` | Reproducible run matching CI exactly (Java 21 + Chromium, `shm_size: 1gb`) |
| **CI** | GitHub Actions `ubuntu-latest`, headless | Gate on every push/PR; publishes the Allure report |

The `HEADLESS` flag is resolved by [`PlaywrightConfig`](../src/test/java/com/github/magdalena/support/PlaywrightConfig.java)
from the `headless` system property, falling back to the `HEADLESS` env var — so the same
code runs identically across all four environments.

---

## 7. Tooling

| Concern | Tool |
|---|---|
| Browser automation | Playwright for Java (Chromium) |
| Test runner | JUnit 5 (Jupiter + Platform Suite) |
| BDD | Cucumber 7 |
| Assertions | Playwright web-first assertions + AssertJ |
| Dependency injection | PicoContainer (Cucumber) |
| Reporting | Allure + Cucumber HTML/JSON/JUnit XML |
| CI/CD | GitHub Actions, GitHub Pages |
| Containerisation | Docker / Docker Compose |
| Build | Maven |

---

## 8. Test selection & tagging strategy

Tags are the contract between "what changed" and "what we run":

| Tag | Meaning | When it runs |
|---|---|---|
| `@smoke` | Minimal critical-path set, fast | **Every push & PR** (CI default) |
| `@regression` | Full journey coverage | On demand / scheduled / pre-release |
| `@desktop` | Desktop-viewport variant | Filtered as needed |
| `@mobile` | Mobile-viewport variant | Filtered as needed |
| `@purchase`, `@visualizer` | Feature grouping | Targeted debugging of one journey |

Selection is driven by `-Dcucumber.filter.tags=...` (CI defaults to `@smoke`, overridable
via the `workflow_dispatch` input or the `CUCUMBER_TAGS` env var in Docker).

---

## 9. CI/CD & reporting

**Pipeline** ([`.github/workflows/e2e-tests.yml`](../.github/workflows/e2e-tests.yml)):
checkout → JDK 21 → install Chromium → run smoke suite headless → generate Allure report →
upload artifacts → publish to GitHub Pages (on `main`).

**Reporting layers:**

- **Allure** — the primary dashboard, enriched with **Trend** (history carried across builds
  via `gh-pages`), **Categories** (failure buckets), **Executors** (the CI run that produced
  it) and **Environment** widgets.
- **Cucumber HTML/JSON/XML** — a standalone report plus machine-readable output.
- **Screenshot on failure** — a full-page screenshot is automatically attached to every
  failed scenario (`CucumberHooks`) and to the JUnit tests, making failures self-explanatory.

**Key metrics tracked:** pass/fail rate and trend, per-step duration, failure categories,
and flaky-test signals (a test that fails then passes on re-run without a code change).

---

## 10. Risk analysis & mitigations

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| **Testing against live production** — content/layout/availability can change at any time | High | High | Role/label/text-based locators that tolerate DOM change; small focused suite; treat unexpected failures as *signal* and triage fast |
| **Cookie banner / consent variations** block interaction | Medium | High | Cookies are explicitly rejected at the start of every journey |
| **Flakiness** from network, animations, third-party scripts | Medium | High | Playwright auto-waiting, isolated `BrowserContext` per scenario, `shm_size: 1gb` in Docker to prevent Chromium crashes, explicit `waitForLoadState()` on real navigations |
| **Third-party Visualizer/Adjust** returns environment-specific messages | Medium | Medium | Mobile scenario asserts the known store-data message rather than assuming success; behaviour is documented, not hidden |
| **New-tab handling** for the Visualizer | Low | Medium | `context.waitForPage(...)` captures the popup deterministically on desktop |
| **No test data isolation guarantees on prod** | Low | Medium | Flow stops before checkout; no orders created; fresh context per scenario |
| **Single browser (Chromium) only** | Medium | Low | Accepted for now; cross-browser is on the roadmap (§13) |

---

## 11. Entry & exit criteria

**Entry (a build is allowed to run the suite):**

- Code compiles (`mvn test-compile`) and the Chromium browser installs successfully.
- The target environment (`HEADLESS`, viewport) is resolvable.

**Exit (a build/release is considered green):**

- 100% of `@smoke` scenarios pass on both viewports.
- No new entries in the Allure **Categories** "Product defects" bucket.
- Any failure is triaged to a root cause (real regression vs. environment/flake) before the
  result is trusted.

---

## 12. Roles & responsibilities

| Role | Responsibility |
|---|---|
| **SDET / QA** | Own the framework, author scenarios, triage failures, keep the suite stable |
| **Reviewers** | Read Gherkin to confirm scenarios describe the *right* behaviour |
| **CI** | Run the smoke gate on every push/PR and publish the report |

---

## 13. Maintenance & roadmap

The framework is intentionally small and clean so it stays cheap to maintain. Planned
improvements, roughly in priority order:

- [ ] **Cross-browser** — add Firefox and WebKit projects to widen real coverage.
- [ ] **Accessibility checks** — integrate an a11y scan into the critical journeys.
- [ ] **Tablet viewport** — a third breakpoint between mobile and desktop.
- [ ] **Retry policy for known-flaky steps** — bounded, explicit, and reported (never silent).
- [ ] **Scheduled regression run** — nightly `@regression` against production to catch drift.
- [ ] **Centralised test data** — externalise shades/products if the data matrix grows.
- [ ] **Visual regression** — snapshot key pages once layouts stabilise.

---

> **Guiding principle:** keep the suite **small, fast, and trustworthy**. A test that is
> flaky or slow is worse than no test, because it erodes confidence in the green build.
