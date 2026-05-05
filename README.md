# 🎭 Playwright E2E Test Framework – Dulux (Java)

A UI end-to-end test automation framework built with **Java 21**, **Playwright**, **JUnit 5**, and **Maven**, targeting the [Dulux UK](https://www.dulux.co.uk) website.

---

## 🧰 Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 21 | Language |
| Playwright | 1.50.0 | Browser automation |
| JUnit Jupiter | 5.11.1 | Test runner |
| AssertJ | 3.24.2 | Fluent assertions |
| Maven | 3.x | Build & dependency management |

---

## 🏗 Project Structure

```
src/
└── test/
    └── java/
        └── com/github/magdalena/
            ├── page/
            │   ├── component/
            │   │   ├── AlertComponent.java       # Handles alert/banner interactions
            │   │   └── NavigationComponent.java  # Top nav, hamburger menu, search
            │   └── pom/
            │       ├── CartPage.java             # Shopping cart page actions
            │       ├── ColorSelectionPage.java   # Colour picker & tester purchase
            │       └── HomePage.java             # Home page navigation & cookies
            └── tests/
                ├── purchase/
                │   └── TesterProductTest.java    # Add colour tester to cart (desktop & mobile)
                └── visualizer/
                    └── VisualizerAppTest.java    # Visualizer app new-tab flow (desktop & mobile)
```

---

## ✅ Test Scenarios

### `TesterProductTest`
Tests that a user can add a **Dulux Colour Tester** product to the shopping cart.

| Test | Viewport |
|---|---|
| `whenDesktop_thenShouldAddTesterToCart` | 1920 × 1080 |
| `whenMobile_thenShouldAddTesterToCart` | 375 × 667 |

**Flow:** Open cart → reject cookies → browse to colour (*Violet / Gentle Lavender*) → buy tester → verify quantity & product name in cart.

---

### `VisualizerAppTest`
Tests the **Dulux Visualizer App** link behaviour across devices.

| Test | Viewport | Expected result |
|---|---|---|
| `whenDesktop_thenOpenVisualizerAppNewTab` | 1920 × 1080 | Opens Visualizer page in a new tab |
| `whenMobile_thenShowContactSupport` | 375 × 667 | Shows "contact support" error message |

---

## ▶️ Running the Tests

### Prerequisites
- Java 21+
- Maven 3.x
- Internet access (tests run against `dulux.co.uk`)

### Run all tests
```bash
mvn test
```

### Run a single test class
```bash
mvn test -Dtest=TesterProductTest
mvn test -Dtest=VisualizerAppTest
```

> **Note:** Tests run in **headed** mode by default (`setHeadless(false)`).  
> Screenshots are saved to `Screenshots/<TestClassName>/` after each run.

---

## 📁 Screenshots

Automatically captured at the end of each test:

```
Screenshots/
├── TesterProductTest/
│   ├── LastScreenShootDesktop.png
│   └── LastScreenShootMobile.png
└── VisualizerAppTest/
    └── LastScreenShoot.png
```

---

## 🧩 Design Patterns Used

- **Page Object Model (POM)** – each page/component is encapsulated in its own class
- **Component Objects** – reusable UI components (`NavigationComponent`, `AlertComponent`) are separated from full-page objects
- **Given / When / Then** – each test follows the GWT arrangement for readability

---

## 👉 Future Improvements

**TO DO:**

- avoid repeating the code – methods like `tearDown`, `setupMobile/Desktop`, `createSetup` are repeated in both test classes and contain the same logic
- as for page object classes – it is good practice for them **not to contain assertions**; there is a test layer for that, or a higher layer
- it would also be nice to use the **Page Object Pattern and inheritance** by extending Page classes
- no AssertJ → tests become less readable and harder to maintain
