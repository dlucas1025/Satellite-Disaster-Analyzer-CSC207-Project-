package data_access;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

/**
 * Reads API keys and URLs without putting them in the code.
 *
 * <p>Looks first for an environment variable (e.g. {@code FIRMS_MAP_KEY} for
 * {@code firms.map.key}), then for the key in {@code config.properties} in the working
 * directory. That file is in .gitignore; copy {@code config.example.properties} to create it.
 * Only data access objects should use this class.</p>
 */
public final class ApiConfig {

    private static final String CONFIG_FILE = "config.properties";

    private ApiConfig() {
    }

    /**
     * Looks up a setting.
     *
     * @param key a property name such as "firms.map.key"
     * @return the value, or empty if it is not set
     */
    public static Optional<String> get(String key) {
        final String envName = key.toUpperCase(Locale.ROOT).replace('.', '_');
        final String fromEnv = System.getenv(envName);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return Optional.of(fromEnv);
        }
        final Path file = Path.of(CONFIG_FILE);
        if (!Files.exists(file)) {
            return Optional.empty();
        }
        final Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            properties.load(in);
        }
        catch (IOException ex) {
            return Optional.empty();
        }
        final String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(value.trim());
    }
}
