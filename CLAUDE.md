# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run all tests (JUnit + Cucumber)
mvn test

# Run only Cucumber scenarios
mvn test -Dtest=CucumberRunner

# Run by Cucumber tag
mvn test -Dtest=CucumberRunner "-Dcucumber.filter.tags=@smoke"
mvn test -Dtest=CucumberRunner "-Dcucumber.filter.tags=@regression"
mvn test -Dtest=CucumberRunner "-Dcucumber.filter.tags=@desktop"

# Run a single JUnit test class
mvn test -Dtest=TesterProductTest
mvn test -Dtest=VisualizerAppTest

# Run headless (required for CI)
mvn test -Dheadless=true

# Install Playwright browser (required once before first run)
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps chromium"

# Generate Allure report locally
mvn allure:report     # output: target/site/allure-maven-plugin/
mvn allure:serve      # serve live in browser
```

Default mode is **headed** (`headless=false`). Set `-Dheadless=true` or `HEADLESS=true` env var for CI.

## Architecture

The framework has two parallel test layers that share the same Page Object Model:

### 1. JUnit layer (`tests/`)
`BaseTest` manages static `Playwright` + `Browser` (shared across test methods via `@BeforeAll`/`@AfterAll`) and per-test `BrowserContext` + `Page` (created/closed per `@Test`). `setUpDesktop()` / `setUpMobile()` configure the viewport (1920×1080 or 375×667).

### 2. Cucumber BDD layer (`cucumber/`)
`CucumberContext` is the central shared state object — injected into all step definition classes via PicoContainer DI. It holds browser/page instances and exposes high-level business methods (e.g. `browseToShade`, `addTesterToBasket`). `CucumberHooks` initialises the browser in `@Before` and tears it down + attaches a failure screenshot in `@After`. `CucumberRunner` is the JUnit Platform suite entry point.

### Page Object Model (`page/`)
- `BasePage` — abstract base holding a `Page` reference; all page/component classes extend it
- `page/pom/` — full page objects: `HomePage`, `CartPage`, `ColorSelectionPage`
- `page/component/` — reusable UI components: `NavigationComponent` (nav, hamburger menu, search, cart), `AlertComponent` (banner dismissal)

### Key wiring
- `PlaywrightConfig.isHeadless()` reads the `-Dheadless` system property, falling back to the `HEADLESS` env var
- Cucumber glue package: `com.github.magdalena.cucumber`
- Feature files: `src/test/resources/features/`
- Allure results: `target/allure-results/` (raw); HTML: `target/allure-report/`

## Cucumber Tags

| Tag | Meaning |
|---|---|
| `@smoke` | Critical path — runs on every push/PR in CI |
| `@regression` | Full suite |
| `@desktop` | 1920×1080 viewport |
| `@mobile` | 375×667 viewport |
| `@purchase` | Add-to-cart feature |
| `@visualizer` | Visualizer app feature |

## CI (GitHub Actions)

Defined in `.github/workflows/e2e-tests.yml`. Runs `@smoke` on every push to `main`/`feature/**` and on PRs. Tag expression is configurable via `workflow_dispatch` input. Publishes the Allure report to GitHub Pages (`gh-pages` branch) after every push to `main`. Live report: https://magdau.github.io/playwright-e2e-test-dulux/
