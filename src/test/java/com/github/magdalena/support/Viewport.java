package com.github.magdalena.support;

import java.util.Arrays;

public enum Viewport {

    DESKTOP(1920, 1080),
    MOBILE(375, 667);

    private final int width;
    private final int height;

    Viewport(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public static Viewport fromName(String name) {
        return Arrays.stream(values())
                .filter(viewport -> viewport.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown viewport '%s'. Supported: %s".formatted(name, Arrays.toString(values()))));
    }
}
