package com.github.magdalena.support;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Route;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stands in for www.dulux.co.uk inside a browser context so offline scenarios never touch
 * the network. Every request is intercepted: known paths are fulfilled with the stub HTML
 * from {@code src/test/resources/stubs}, other dulux.co.uk paths get a 404 and anything
 * else is aborted. Create it before the first navigation so no request can slip through.
 */
public final class OfflineSite {

    private static final String SITE_HOST = "www.dulux.co.uk";
    private static final String STUB_DIR = "stubs/";

    private final Map<String, String> stubsByPath = new ConcurrentHashMap<>(Map.of(
            "/", "home",
            "/en/store/cart", "cart-empty",
            "/en/search-results", "search-results-empty"
    ));

    public OfflineSite(BrowserContext context) {
        context.route("**/*", this::handle);
    }

    /** Serves the named stub (file name without extension) for the path from now on. */
    public void serve(String path, String stubName) {
        stubsByPath.put(path, stubName);
    }

    private void handle(Route route) {
        URI uri = URI.create(route.request().url());
        if (!SITE_HOST.equals(uri.getHost())) {
            route.abort();
            return;
        }

        String stubName = stubsByPath.get(uri.getPath());
        if (stubName == null) {
            route.fulfill(new Route.FulfillOptions().setStatus(404));
            return;
        }
        route.fulfill(new Route.FulfillOptions().setContentType("text/html").setBody(readStub(stubName)));
    }

    private static String readStub(String stubName) {
        try (InputStream in = OfflineSite.class.getClassLoader().getResourceAsStream(STUB_DIR + stubName + ".html")) {
            if (in == null) {
                throw new IllegalStateException("Missing stub: " + stubName);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read stub " + stubName, e);
        }
    }
}
