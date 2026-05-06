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

- ✅ ~~avoid repeating the code – methods like `tearDown`, `setupMobile/Desktop`, `createSetup` are repeated in both test classes and contain the same logic~~ → extracted to `BaseTest`
- ✅ ~~it would also be nice to use the **Page Object Pattern and inheritance** by extending Page classes~~ → extracted to `BasePage`
- ✅ ~~as for page object classes – it is good practice for them **not to contain assertions**; there is a test layer for that, or a higher layer~~ → all assertions moved to test classes
- no AssertJ → tests become less readable and harder to maintain
