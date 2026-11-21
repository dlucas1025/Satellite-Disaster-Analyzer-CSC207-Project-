package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The communities one user watches. Owner: Shreyas.
 *
 * <p>Communities are kept in the order they were added. Two communities are the
 * same if they have the same id, so the same place cannot be saved twice.</p>
 */
public class Watchlist {

    private final String owner;
    private final List<Community> communities = new ArrayList<>();

    /**
     * Creates an empty watchlist.
     *
     * @param owner the user's name
     * @throws IllegalArgumentException if the owner is blank
     */
    public Watchlist(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("owner must not be blank");
        }
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    /**
     * The saved communities, in the order they were added.
     *
     * @return a read-only view of the list
     */
    public List<Community> getCommunities() {
        return Collections.unmodifiableList(communities);
    }

    /**
     * Adds a community unless one with the same id is already saved.
     *
     * @param community the community to add
     * @return true if added, false if it was a duplicate
     * @throws IllegalArgumentException if the community is null
     */
    public boolean add(Community community) {
        if (community == null) {
            throw new IllegalArgumentException("community must not be null");
        }
        if (find(community.id()).isPresent()) {
            return false;
        }
        communities.add(community);
        return true;
    }

    /**
     * Removes a community.
     *
     * @param communityId the id to remove
     * @return true if something was removed
     */
    public boolean remove(String communityId) {
        return communities.removeIf(c -> c.id().equals(communityId));
    }

    /**
     * Renames a saved community, e.g. to "Peguis FN". Its id and position stay the same.
     *
     * @param communityId the id to rename
     * @param newName     the new display name
     * @return true if the community was found and renamed
     * @throws IllegalArgumentException if the new name is blank
     */
    public boolean rename(String communityId, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("new name must not be blank");
        }
        for (int i = 0; i < communities.size(); i++) {
            final Community old = communities.get(i);
            if (old.id().equals(communityId)) {
                // Community is an immutable record, so replace it with a renamed copy.
                communities.set(i, new Community(old.id(), newName.trim(), old.province(),
                        old.latitude(), old.longitude()));
                return true;
            }
        }
        return false;
    }

    /**
     * Looks up a saved community.
     *
     * @param communityId the id
     * @return the community, or empty if it is not saved
     */
    public Optional<Community> find(String communityId) {
        return communities.stream().filter(c -> c.id().equals(communityId)).findFirst();
    }
}
