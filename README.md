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

👉 In short:
Maven doesn’t include these tools by default — you must declare them so your project can compile and run tests properly.
*Take some dependencies to pom file

👉 Without them:

no JUnit → tests won’t run
no AssertJ → tests become less readable and harder to maintain
no Playwright → no UI/E2E tests at all


👉Future Improvements

TO DO:

-avoid repeating the code, methods like tearDown, setupMobile/Desktop, createSetup are repeated in both test classes and contain the same logic
-as for page object classes - it is good practice for them not to contain assertions, there is a test for that, or a higher layer
-it would also be nice to use the Page Object Pattern and inheritance by extending Page classes
no AssertJ → test


