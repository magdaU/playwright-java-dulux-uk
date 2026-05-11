# 🎭 Playwright E2E Test Framework – Dulux (Java)

[![E2E Tests](https://github.com/magdaU/playwright-e2e-test-dulux/actions/workflows/e2e-tests.yml/badge.svg)](https://github.com/magdaU/playwright-e2e-test-dulux/actions/workflows/e2e-tests.yml)
[![Allure Report](https://img.shields.io/badge/Allure-Report-brightgreen?logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0xMiAyQzYuNDggMiAyIDYuNDggMiAxMnM0LjQ4IDEwIDEwIDEwIDEwLTQuNDggMTAtMTBTMTcuNTIgMiAxMiAyem0tMSAxNEg5VjhIMTF2OHptNCAwaC0yVjhoMnY4eiIvPjwvc3ZnPg==)](https://magdau.github.io/playwright-e2e-test-dulux/)

A UI end-to-end test automation framework built with **Java 21**, **Playwright**, **JUnit 5**, **Cucumber BDD**, and **Maven**, targeting the [Dulux UK](https://www.dulux.co.uk) website.

---

## 🧰 Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 21 | Language |
| Playwright | 1.50.0 | Browser automation |
| JUnit Jupiter | 5.11.1 | Test runner |
| Cucumber | 7.18.1 | BDD layer (Gherkin feature files) |
| PicoContainer | – | Dependency injection for Cucumber |
| AssertJ | 3.24.2 | Fluent assertions |
| Allure | 2.29.1 | Test reporting |
| Maven | 3.x | Build & dependency management |
| GitHub Actions | – | CI/CD pipeline |

---

## 🏗 Project Structure

```
src/
└── test/
    ├── java/
    │   └── com/github/magdalena/
    │       ├── cucumber/
    │       │   ├── steps/
    │       │   │   ├── CommonSteps.java           # Reusable Given/When steps (shared across features)
    │       │   │   ├── AddTesterToCartSteps.java  # Then steps for tester purchase
    │       │   │   └── VisualizerAppSteps.java    # Then steps for visualizer experience
    │       │   ├── CucumberContext.java           # Shared browser state + high-level business methods
    │       │   ├── CucumberHooks.java             # @Before / @After – screenshot on failure
    │       │   └── CucumberRunner.java            # JUnit Platform suite runner with Allure plugin
    │       ├── page/
    │       │   ├── component/
    │       │   │   ├── AlertComponent.java        # Handles alert/banner interactions
    │       │   │   └── NavigationComponent.java   # Top nav, hamburger menu, search
    │       │   └── pom/
    │       │       ├── CartPage.java              # Shopping cart page actions
    │       │       ├── ColorSelectionPage.java    # Colour picker & tester purchase
    │       │       └── HomePage.java              # Home page navigation & cookies
    │       ├── support/
    │       │   └── PlaywrightConfig.java          # Reads headless flag from system property / env var
    │       └── tests/
    │           ├── BaseTest.java                  # Shared JUnit setup / teardown
    │           ├── purchase/
    │           │   └── TesterProductTest.java     # Add colour tester to cart (desktop & mobile)
    │           └── visualizer/
    │               └── VisualizerAppTest.java     # Visualizer app new-tab flow (desktop & mobile)
    └── resources/
        └── features/
            ├── tester_purchase.feature            # BDD scenarios for TC-01 / TC-02
            └── visualizer_experience.feature      # BDD scenarios for TC-03 / TC-04
```

---

## ✅ Test Cases

---

### TC-01 · Add colour tester to cart – 🖥️ Desktop

1. Open the cart page
2. Reject cookies
3. Verify the basket is empty
4. Go to the home page
5. Open "Find a colour" from the top navigation
6. Select colour group **Violet**
7. Select shade **Gentle Lavender**
8. Click "Buy a Tester in this colour"
9. Close the confirmation alert
10. Open the shopping cart

✅ Cart contains **1x Dulux Colour Tester – Gentle Lavender**

---

### TC-02 · Add colour tester to cart – 📱 Mobile

1. Open the cart page
2. Reject cookies
3. Verify the basket is empty
4. Go to the home page
5. Open the **hamburger menu**
6. Open "Find a colour" from the mobile navigation
7. Select colour group **Violet**
8. Select shade **Gentle Lavender**
9. Click "Buy a Tester in this colour"
10. Close the confirmation alert
11. Open the shopping cart

✅ Cart contains **1x Dulux Colour Tester – Gentle Lavender**

---

### TC-03 · Visualizer App link – 🖥️ Desktop

1. Go to the home page
2. Reject cookies
3. Search for **Gentle Lavender**
4. Click "Try our Visualizer App"

✅ Visualizer App opens in a **new tab**

---

### TC-04 · Visualizer App link – 📱 Mobile

1. Go to the home page
2. Reject cookies
3. Search for **Gentle Lavender**
4. Click "Try our Visualizer App"

✅ Error message is displayed – app is **not supported** on mobile

---

## ▶️ Running the Tests

### Prerequisites
- Java 21+
- Maven 3.x
- Internet access (tests run against `dulux.co.uk`)

### Run all tests (JUnit + Cucumber)
```bash
mvn test
```

### Run only Cucumber scenarios
```bash
mvn test -Dtest=CucumberRunner
```

### Run by tag
```bash
# only smoke suite
mvn test -Dtest=CucumberRunner "-Dcucumber.filter.tags=@smoke"

# full regression
mvn test -Dtest=CucumberRunner "-Dcucumber.filter.tags=@regression"

# desktop scenarios only
mvn test -Dtest=CucumberRunner "-Dcucumber.filter.tags=@desktop"
```

### Run a single JUnit test class
```bash
mvn test -Dtest=TesterProductTest
mvn test -Dtest=VisualizerAppTest
```

### Run headless (for CI)
```bash
mvn test -Dheadless=true
```

> **Note:** By default tests run in **headed** mode (`headless=false`).  
> Pass `-Dheadless=true` or set the `HEADLESS=true` environment variable to run without a browser window.

---

## 🥒 Cucumber BDD Layer

Tests are written in Gherkin and live in `src/test/resources/features/`.

### Feature files

| File | Tag | Scenarios |
|---|---|---|
| `tester_purchase.feature` | `@purchase @regression` | Desktop & Mobile add-to-cart |
| `visualizer_experience.feature` | `@visualizer @regression` | Desktop opens new tab / Mobile error |

### Tags

| Tag | Meaning |
|---|---|
| `@smoke` | Fast critical path – runs on every push |
| `@regression` | Full regression suite |
| `@desktop` | Scenarios using 1920×1080 viewport |
| `@mobile` | Scenarios using 375×667 viewport |
| `@purchase` | Tester purchase feature |
| `@visualizer` | Visualizer experience feature |

### Architecture

```
CucumberContext          ← shared browser state (Page, BrowserContext) + business helper methods
      │
CucumberHooks            ← @Before initialises context, @After tears down + attaches screenshot on failure
      │
Step definitions         ← inject CucumberContext via PicoContainer DI
  ├── CommonSteps        ← Given / When steps (reused by both features)
  ├── AddTesterToCartSteps  ← Then assertions for purchase scenarios
  └── VisualizerAppSteps    ← Then assertions for visualizer scenarios
```

### Example feature file

```gherkin
@purchase @regression
Feature: Purchase a colour tester

  @smoke @desktop
  Scenario: Desktop customer adds a tester from the colour finder
    Given a desktop customer starts with an empty basket
    When the customer browses to shade "Gentle Lavender" from colour family "Violet"
    And the customer adds a tester to the basket
    Then the basket contains 1 item
    And the basket includes tester "Dulux Colour Tester" for shade "Gentle Lavender"
```

---

## 📊 Allure Report

Allure is integrated via `allure-cucumber7-jvm`. Results are written to `target/allure-results/`.

### 🌐 Live report (GitHub Pages)

After every push to `main` the report is automatically published and available at:

**➡️ https://magdau.github.io/playwright-e2e-test-dulux/**

> **One-time setup required** — enable GitHub Pages in the repository:
> 1. Go to **Settings** → **Pages**
> 2. Source: **Deploy from a branch**
> 3. Branch: **`gh-pages`** / `/ (root)`
> 4. Click **Save**
>
> The `gh-pages` branch is created automatically after the first successful CI run.

### Generate and open the report locally

```bash
# run tests
mvn test

# generate HTML report (output: target/site/allure-maven-plugin/)
mvn allure:report

# OR serve it live in the browser
mvn allure:serve
```

### What's recorded
- ✅ Passed / ❌ Failed / ⏭ Skipped scenarios
- Scenario steps with duration
- Screenshot attached to every **failed** scenario (visible in the Allure timeline)

---

## 📁 Screenshots

Automatically captured at the end of each JUnit test:

```
Screenshots/
├── TesterProductTest/
│   ├── LastScreenShootDesktop.png
│   └── LastScreenShootMobile.png
└── VisualizerAppTest/
    └── LastScreenShoot.png
```

For **Cucumber scenarios**, a screenshot is attached to the report automatically on failure (visible in Cucumber HTML report and Allure).

---

## ⚙️ GitHub Actions CI

The workflow is defined in `.github/workflows/e2e-tests.yml`.

### When it runs

| Trigger | Default tag |
|---|---|
| Push to `main`, `feature/**` or `fix/**` | `@smoke` |
| Pull Request to `main` | `@smoke` |
| Manual (`workflow_dispatch`) | configurable (see below) |

> **Why `fix/**`?** Without this pattern, branches named `fix/something` are silently skipped by CI — pushes land with no feedback until a PR is opened. Adding `fix/**` alongside `feature/**` ensures the smoke suite runs on every active branch regardless of its naming prefix.

### How to trigger manually with a custom tag

1. Go to your repository on GitHub
2. Click **Actions** → **E2E Tests**
3. Click **Run workflow**
4. Enter a tag expression in the `cucumber_tags` field, e.g.:
   - `@smoke` – only smoke tests
   - `@regression` – full regression
   - `@desktop` – desktop scenarios only
   - `@smoke and @desktop` – smoke AND desktop
5. Click **Run workflow**

### Artifacts uploaded after each run

| Artifact | Contents |
|---|---|
| `cucumber-reports` | HTML report, JSON, XML, surefire reports |
| `allure-results` | Raw Allure data (open with `allure serve`) |

To download: **Actions** → select a run → **Artifacts** section at the bottom.

### Workflow configuration overview

```yaml
on:
  push:
    branches: [main, feature/**, fix/**]
  pull_request:
    branches: [main]
  workflow_dispatch:
    inputs:
      cucumber_tags:
        description: "Cucumber tag expression to run"
        default: "@smoke"

permissions:
  contents: write   # required to push to gh-pages branch

jobs:
  cucumber-smoke:
    runs-on: ubuntu-latest
    env:
      HEADLESS: "true"
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: '21', distribution: temurin, cache: maven }
      - run: mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps chromium"
      - run: mvn -Dheadless=true -Dtest=CucumberRunner "-Dcucumber.filter.tags=${CUCUMBER_TAGS}" test
      - run: npm install -g allure-commandline          # installs Allure CLI
      - run: allure generate target/allure-results --clean -o target/allure-report
      - uses: actions/upload-artifact@v4                # uploads cucumber-reports
      - uses: actions/upload-artifact@v4                # uploads allure-results
      - uses: peaceiris/actions-gh-pages@v3             # publishes report to GitHub Pages
        if: github.ref == 'refs/heads/main'
        with:
          publish_dir: target/allure-report
```

---

## 🧩 Design Patterns Used

- **Page Object Model (POM)** – each page/component is encapsulated in its own class extending `BasePage`
- **Component Objects** – reusable UI components (`NavigationComponent`, `AlertComponent`) are separated from full-page objects
- **Base classes** – `BasePage` (shared `Page` field) and `BaseTest` (shared JUnit setup/teardown) eliminate code duplication
- **Behaviour-Driven Development (BDD)** – Cucumber Gherkin scenarios describe behaviour in plain business language
- **Dependency Injection** – PicoContainer injects `CucumberContext` into all step definition classes
- **Given / When / Then** – each test follows the GWT arrangement for readability

---

## 👉 Future Improvements

**TO DO:**

- ✅ ~~avoid repeating the code – methods like `tearDown`, `setupMobile/Desktop`, `createSetup` are repeated in both test classes and contain the same logic~~ → extracted to `BaseTest`
- ✅ ~~it would also be nice to use the **Page Object Pattern and inheritance** by extending Page classes~~ → extracted to `BasePage`
- ✅ ~~as for page object classes – it is good practice for them **not to contain assertions**; there is a test layer for that, or a higher layer~~ → all assertions moved to test classes
- ✅ ~~no AssertJ → tests become less readable and harder to maintain~~ → AssertJ now used for all value/string assertions
- ✅ ~~add Cucumber BDD layer~~ → Cucumber 7 with PicoContainer DI, clean Gherkin, shared `CucumberContext`
- ✅ ~~add @smoke / @regression tags~~ → all scenarios tagged, runnable by expression
- ✅ ~~add Allure reporting~~ → `allure-cucumber7-jvm` integrated, screenshot on failure, report published to GitHub Pages
- ✅ ~~set up GitHub Actions CI~~ → workflow runs smoke suite on every push/PR, artifacts uploaded
- ✅ ~~CI only triggers on `feature/**` branches~~ → added `fix/**` pattern so branches like `fix/something` also run the smoke suite automatically
