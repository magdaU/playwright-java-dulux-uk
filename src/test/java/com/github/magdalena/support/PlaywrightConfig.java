package com.github.magdalena.support;

public final class PlaywrightConfig {

    private static final String DEFAULT_HEADLESS = "false";

    private PlaywrightConfig() {
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty(
                "headless",
                System.getenv().getOrDefault("HEADLESS", DEFAULT_HEADLESS)
        ));
    }
}

