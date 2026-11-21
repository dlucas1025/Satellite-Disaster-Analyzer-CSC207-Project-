package data_access;

import java.util.List;
import java.util.Optional;

import entity.Community;
import use_case.save_community.WatchlistDataAccessInterface;
import use_case.shared.CommunityProvider;

/**
 * Adapter that lets stories 2-4 read the real watchlist through {@link CommunityProvider}.
 * Owner: Shreyas. Swap it in for DemoCommunityProvider in AppBuilder once the watchlist works.
 */
public class WatchlistCommunityProvider implements CommunityProvider {

    private final WatchlistDataAccessInterface store;
    private final String owner;

    /**
     * Creates the adapter.
     *
     * @param store where the watchlist is kept
     * @param owner the signed-in user
     */
    public WatchlistCommunityProvider(WatchlistDataAccessInterface store, String owner) {
        this.store = store;
        this.owner = owner;
    }

    @Override
    public List<Community> getCommunities() {
        return store.load(owner).getCommunities();
    }

    @Override
    public Optional<Community> findById(String id) {
        return store.load(owner).find(id);
    }
}
