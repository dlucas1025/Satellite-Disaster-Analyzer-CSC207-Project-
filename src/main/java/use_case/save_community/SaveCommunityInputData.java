package use_case.save_community;

import entity.Community;

/**
 * What the user asked the watchlist to do. Owner: Member A.
 * Use the static factory that matches the action; unused fields are null.
 *
 * @param owner       the user's name
 * @param query       search text (search only)
 * @param place       the place to save (save only)
 * @param communityId the saved community to change (remove, rename)
 * @param newName     the new name (rename only)
 */
public record SaveCommunityInputData(String owner, String query, Community place,
                                     String communityId, String newName) {

    /**
     * Input for a search.
     *
     * @param owner the user
     * @param query the name typed
     * @return the input data
     */
    public static SaveCommunityInputData forSearch(String owner, String query) {
        return new SaveCommunityInputData(owner, query, null, null, null);
    }

    /**
     * Input for saving a chosen place.
     *
     * @param owner the user
     * @param place the place picked from the search results
     * @return the input data
     */
    public static SaveCommunityInputData forSave(String owner, Community place) {
        return new SaveCommunityInputData(owner, null, place, null, null);
    }

    /**
     * Input for removing a saved community.
     *
     * @param owner       the user
     * @param communityId the community to remove
     * @return the input data
     */
    public static SaveCommunityInputData forRemove(String owner, String communityId) {
        return new SaveCommunityInputData(owner, null, null, communityId, null);
    }

    /**
     * Input for renaming a saved community.
     *
     * @param owner       the user
     * @param communityId the community to rename
     * @param newName     the new name
     * @return the input data
     */
    public static SaveCommunityInputData forRename(String owner, String communityId, String newName) {
        return new SaveCommunityInputData(owner, null, null, communityId, newName);
    }
}
