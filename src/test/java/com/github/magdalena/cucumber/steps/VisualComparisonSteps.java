package com.github.magdalena.cucumber.steps;

import com.github.magdalena.support.VisualComparisonUtil;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class VisualComparisonSteps {

    private static final Path BASELINE_DIR = Paths.get("src", "test", "resources", "visual-baselines");
    private static final String SNAPSHOT = "offline-scenario-snapshot";
    private static final int WHITE = 0xFFFFFF;
    private static final int BLACK = 0x000000;

    private AssertionError comparisonFailure;

    @Given("a baseline screenshot of {int} by {int} pixels")
    public void aBaselineScreenshotOf(int width, int height) throws IOException {
        Files.createDirectories(BASELINE_DIR);
        ImageIO.write(whiteImage(width, height), "png", BASELINE_DIR.resolve(SNAPSHOT + ".png").toFile());
    }

    @When("a screenshot of the same size with {int} changed pixels is compared")
    public void aScreenshotWithChangedPixelsIsCompared(int changedPixels) throws IOException {
        BufferedImage baseline = ImageIO.read(BASELINE_DIR.resolve(SNAPSHOT + ".png").toFile());
        BufferedImage screenshot = whiteImage(baseline.getWidth(), baseline.getHeight());
        for (int i = 0; i < changedPixels; i++) {
            screenshot.setRGB(i % screenshot.getWidth(), i / screenshot.getWidth(), BLACK);
        }
        compare(screenshot);
    }

    @When("a screenshot of {int} by {int} pixels is compared")
    public void aScreenshotOfSizeIsCompared(int width, int height) {
        compare(whiteImage(width, height));
    }

    @Then("the screenshot matches the baseline")
    public void theScreenshotMatchesTheBaseline() {
        assertThat(comparisonFailure).isNull();
    }

    @Then("the screenshot is rejected as a visual regression")
    public void theScreenshotIsRejectedAsAVisualRegression() {
        assertThat(comparisonFailure).hasMessageContaining(SNAPSHOT);
    }

    @After("@visual-comparison")
    public void deleteBaseline() throws IOException {
        Files.deleteIfExists(BASELINE_DIR.resolve(SNAPSHOT + ".png"));
    }

    private void compare(BufferedImage screenshot) {
        try {
            VisualComparisonUtil.assertMatchesBaseline(png(screenshot), SNAPSHOT);
        } catch (AssertionError e) {
            comparisonFailure = e;
        }
    }

    private static BufferedImage whiteImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, WHITE);
            }
        }
        return image;
    }

    private static byte[] png(BufferedImage image) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
