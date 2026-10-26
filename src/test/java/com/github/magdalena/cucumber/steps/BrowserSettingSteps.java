package com.github.magdalena.cucumber.steps;

import com.github.magdalena.support.PlaywrightConfig;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class BrowserSettingSteps {

    private static final String HEADLESS_PROPERTY = "headless";

    private final String originalHeadless = System.getProperty(HEADLESS_PROPERTY);

    @Given("the headless setting is {string}")
    public void theHeadlessSettingIs(String value) {
        System.setProperty(HEADLESS_PROPERTY, value);
    }

    @Then("the browser runs in {word} mode")
    public void theBrowserRunsInMode(String mode) {
        assertThat(PlaywrightConfig.isHeadless()).isEqualTo("headless".equals(mode));
    }

    @After("@headless-setting")
    public void restoreHeadlessSetting() {
        if (originalHeadless == null) {
            System.clearProperty(HEADLESS_PROPERTY);
            return;
        }
        System.setProperty(HEADLESS_PROPERTY, originalHeadless);
    }
}
