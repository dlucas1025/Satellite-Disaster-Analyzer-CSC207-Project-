package data_access;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.json.JSONException;
import org.json.JSONObject;

import entity.Watchlist;
import use_case.save_community.WatchlistDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Keeps watchlists in a JSON file on this computer. Owner: Shreyas.
 *
 * <p>One file can hold several users' watchlists:</p>
 * <pre>
 * {"watchlists": {"shreyas": [ ...communities... ], "lucas": [ ... ]}}
 * </pre>
 * <p>Saving writes to a temporary file first and then replaces the real file, so a crash
 * mid-save never leaves a half-written watchlist.</p>
 */
public class FileWatchlistDataAccessObject implements WatchlistDataAccessInterface {

    private static final String WATCHLISTS = "watchlists";
    private static final int INDENT = 2;

    private final Path file;

    /**
     * Creates the store.
     *
     * @param file the JSON file, e.g. ~/.northwatch/watchlist.json
     */
    public FileWatchlistDataAccessObject(Path file) {
        this.file = file;
    }

    @Override
    public Watchlist load(String owner) {
        try {
            final JSONObject watchlists = readAll().getJSONObject(WATCHLISTS);
            if (!watchlists.has(owner)) {
                return new Watchlist(owner);
            }
            return WatchlistJsonMapper.fromJson(owner, watchlists.getJSONArray(owner));
        }
        catch (JSONException | IllegalArgumentException ex) {
            throw new DataUnavailableException("The saved watchlist in " + file + " is damaged", ex);
        }
    }

    @Override
    public void save(Watchlist watchlist) {
        final JSONObject root = readAll();
        root.getJSONObject(WATCHLISTS).put(watchlist.getOwner(), WatchlistJsonMapper.toJson(watchlist));
        try {
            final Path folder = file.toAbsolutePath().getParent();
            Files.createDirectories(folder);
            final Path temp = Files.createTempFile(folder, "watchlist", ".tmp");
            Files.writeString(temp, root.toString(INDENT), StandardCharsets.UTF_8);
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex) {
            throw new DataUnavailableException("Cannot write " + file, ex);
        }
    }

    /**
     * Reads the whole file, or an empty structure if it does not exist yet.
     *
     * @return the root object, always with a "watchlists" object inside
     */
    private JSONObject readAll() {
        if (!Files.exists(file)) {
            return new JSONObject().put(WATCHLISTS, new JSONObject());
        }
        try {
            final JSONObject root = new JSONObject(Files.readString(file, StandardCharsets.UTF_8));
            if (!root.has(WATCHLISTS)) {
                root.put(WATCHLISTS, new JSONObject());
            }
            return root;
        }
        catch (IOException ex) {
            throw new DataUnavailableException("Cannot read " + file, ex);
        }
        catch (JSONException ex) {
            throw new DataUnavailableException("The saved watchlist in " + file + " is damaged", ex);
        }
    }
}
