🎭 Playwright UI Automation Framework (Java)

This is a UI test automation framework built with Java, Playwright, JUnit 5, and Maven.
It is designed for reliable end-to-end testing of modern web applications.

🧰 Tech Stack
Java 21
Playwright
JUnit 5
Maven
AssertJ
🏗 Project Structure

This framework is used for:  End-to-end UI testing, Regression testing
Cross-browser validation
Automating critical user flows


👉 Dependencies are needed because each one has a specific role:

JUnit (junit-jupiter) → runs the tests
AssertJ (assertj-core) → provides readable assertions
Playwright → performs browser-based E2E actions

Additional Info:
https://playwright.dev/


👉 In short:
Maven doesn’t include these tools by default — you must declare them so your project can compile and run tests properly.
*Take some dependencies to pom file

👉 Without them:

no JUnit → tests won’t run
no AssertJ → tests become less readable and harder to maintain
no Playwright → no UI/E2E tests at all
