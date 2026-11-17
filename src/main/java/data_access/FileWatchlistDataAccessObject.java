package data_access;

import java.nio.file.Path;

import entity.Watchlist;
import use_case.save_community.WatchlistDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Keeps watchlists in a JSON file on this computer. Owner: Member A.
 */
public class FileWatchlistDataAccessObject implements WatchlistDataAccessInterface {

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
        // TODO Member A: return an empty Watchlist if the file does not exist yet
        throw new DataUnavailableException("Cannot read " + file + ": not implemented yet");
    }

    @Override
    public void save(Watchlist watchlist) {
        // TODO Member A: create parent folders, write JSON
        throw new DataUnavailableException("Cannot write " + file + ": not implemented yet");
    }
}
