package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The communities one user watches. Owner: Member A.
 */
public class Watchlist {

    private final String owner;
    private final List<Community> communities = new ArrayList<>();

    /**
     * Creates an empty watchlist.
     *
     * @param owner the user's name
     */
    public Watchlist(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public List<Community> getCommunities() {
        return Collections.unmodifiableList(communities);
    }

    /**
     * Adds a community unless one with the same id is already saved.
     *
     * @param community the community to add
     * @return true if added, false if it was a duplicate
     */
    public boolean add(Community community) {
        // TODO Member A
        throw new UnsupportedOperationException("TODO Member A: Watchlist.add");
    }

    /**
     * Removes a community.
     *
     * @param communityId the id to remove
     * @return true if something was removed
     */
    public boolean remove(String communityId) {
        // TODO Member A
        throw new UnsupportedOperationException("TODO Member A: Watchlist.remove");
    }

    /**
     * Renames a saved community, e.g. to "Peguis FN".
     *
     * @param communityId the id to rename
     * @param newName     the new display name
     * @return true if the community was found and renamed
     */
    public boolean rename(String communityId, String newName) {
        // TODO Member A: Community is a record, so replace it with a copy that has the new name
        throw new UnsupportedOperationException("TODO Member A: Watchlist.rename");
    }

    /**
     * Looks up a saved community.
     *
     * @param communityId the id
     * @return the community, or empty
     */
    public Optional<Community> find(String communityId) {
        // TODO Member A
        throw new UnsupportedOperationException("TODO Member A: Watchlist.find");
    }
}
