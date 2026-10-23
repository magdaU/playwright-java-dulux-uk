package com.github.magdalena.tests.unit;

import com.github.magdalena.support.VisualComparisonUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * ISTQB boundary value analysis (2-value) on the 0.5% different-pixel allowance used by
 * {@link VisualComparisonUtil}. Pure in-memory images: no browser, no network, so the
 * production site is never touched.
 */
@Epic("Quality")
@Feature("Visual Regression")
@Story("Pixel-difference threshold")
@Owner("Magdalena")
@Tag("unit")
class VisualComparisonUtilBoundaryTest {

    private static final Path BASELINE_DIR = Paths.get("src", "test", "resources", "visual-baselines");
    private static final String SNAPSHOT = "unit-boundary-snapshot";
    private static final int SIZE = 200;
    // 0.5% of 200 x 200 pixels
    private static final int ALLOWED_DIFFERENT_PIXELS = 200;

    @AfterEach
    void deleteBaseline() throws IOException {
        Files.deleteIfExists(BASELINE_DIR.resolve(SNAPSHOT + ".png"));
    }

    @Test
    @Description("Exactly at the allowance (0.5%) is still a match")
    void givenDifferenceAtAllowance_whenCompared_thenMatches() throws IOException {
        // arrange
        writeBaseline(blankImage());
        byte[] actual = png(imageWithDifferentPixels(ALLOWED_DIFFERENT_PIXELS));

        // act & assert
        assertThatCode(() -> VisualComparisonUtil.assertMatchesBaseline(actual, SNAPSHOT))
                .doesNotThrowAnyException();
    }

    @Test
    @Description("One pixel beyond the allowance (0.5% + 1 pixel) is a mismatch")
    void givenDifferenceJustAboveAllowance_whenCompared_thenFails() throws IOException {
        // arrange
        writeBaseline(blankImage());
        byte[] actual = png(imageWithDifferentPixels(ALLOWED_DIFFERENT_PIXELS + 1));

        // act & assert
        assertThatThrownBy(() -> VisualComparisonUtil.assertMatchesBaseline(actual, SNAPSHOT))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(SNAPSHOT);
    }

    private static void writeBaseline(BufferedImage image) throws IOException {
        Files.createDirectories(BASELINE_DIR);
        ImageIO.write(image, "png", BASELINE_DIR.resolve(SNAPSHOT + ".png").toFile());
    }

    private static BufferedImage blankImage() {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                image.setRGB(x, y, 0xFFFFFF);
            }
        }
        return image;
    }

    private static BufferedImage imageWithDifferentPixels(int count) {
        BufferedImage image = blankImage();
        for (int i = 0; i < count; i++) {
            image.setRGB(i % SIZE, i / SIZE, 0x000000);
        }
        return image;
    }

    private static byte[] png(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        return out.toByteArray();
    }
}
