# Documentation images

Drop the screenshots referenced by the main `README.md` here. Filenames are
already wired up in the README, so once you add the PNGs they render on GitHub.

Expected files:

| File | What to capture |
|---|---|
| `allure-overview.png` | Allure report landing page (the Overview dashboard with pass/fail donut) |
| `allure-suites.png` | Allure → Suites/Behaviors view showing the scenarios and steps |
| `cucumber-report.png` | The Cucumber HTML report (`target/cucumber-reports/report.html`) |
| `ci-pipeline.png` | A green GitHub Actions run (Actions → E2E Tests → a passing run) |

How to produce them:

```bash
mvn test                       # generates target/allure-results + cucumber report
mvn allure:serve               # opens the live Allure report in the browser
```

Then take a screenshot of each view and save it here with the filename above.
PNGs in this `docs/` folder are whitelisted in `.gitignore`, so remember to
`git add docs/images/*.png`.
