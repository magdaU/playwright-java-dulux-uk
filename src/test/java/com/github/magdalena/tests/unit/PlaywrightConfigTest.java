package com.github.magdalena.tests.unit;

import com.github.magdalena.support.PlaywrightConfig;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ISTQB equivalence partitioning on the {@code headless} setting: one representative per
 * partition (valid "true", invalid free text). No browser or network involved.
 */
@Epic("Quality")
@Feature("Test configuration")
@Story("Headless switch")
@Owner("Magdalena")
@Tag("unit")
class PlaywrightConfigTest {

    private static final String HEADLESS_PROPERTY = "headless";

    @AfterEach
    void clearProperty() {
        System.clearProperty(HEADLESS_PROPERTY);
    }

    @Test
    void givenHeadlessPropertyIsTrue_whenQueried_thenHeadlessIsEnabled() {
        // arrange
        System.setProperty(HEADLESS_PROPERTY, "true");

        // act
        boolean headless = PlaywrightConfig.isHeadless();

        // assert
        assertThat(headless).isTrue();
    }

    @Test
    void givenHeadlessPropertyIsNotABoolean_whenQueried_thenHeadlessIsDisabled() {
        // arrange
        System.setProperty(HEADLESS_PROPERTY, "not-a-boolean");

        // act
        boolean headless = PlaywrightConfig.isHeadless();

        // assert
        assertThat(headless).isFalse();
    }
}
