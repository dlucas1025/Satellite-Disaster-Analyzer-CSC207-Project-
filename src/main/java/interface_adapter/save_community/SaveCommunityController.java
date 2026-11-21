package interface_adapter.save_community;

import entity.Community;
import use_case.save_community.SaveCommunityInputBoundary;
import use_case.save_community.SaveCommunityInputData;

/**
 * Turns watchlist screen actions into use case calls. Owner: Shreyas.
 */
public class SaveCommunityController {

    private final SaveCommunityInputBoundary interactor;
    private final String owner;

    /**
     * Creates the controller.
     *
     * @param interactor the use case
     * @param owner      the signed-in user (TODO Shreyas: real sign-in)
     */
    public SaveCommunityController(SaveCommunityInputBoundary interactor, String owner) {
        this.interactor = interactor;
        this.owner = owner;
    }

    /** Loads the saved watchlist. */
    public void load() {
        interactor.load(owner);
    }

    /**
     * Searches for places.
     *
     * @param query the text typed
     */
    public void search(String query) {
        interactor.search(SaveCommunityInputData.forSearch(owner, query));
    }

    /**
     * Saves a search result.
     *
     * @param place the chosen place
     */
    public void save(Community place) {
        interactor.save(SaveCommunityInputData.forSave(owner, place));
    }

    /**
     * Removes a saved community.
     *
     * @param communityId its id
     */
    public void remove(String communityId) {
        interactor.remove(SaveCommunityInputData.forRemove(owner, communityId));
    }

    /**
     * Renames a saved community.
     *
     * @param communityId its id
     * @param newName     the new name
     */
    public void rename(String communityId, String newName) {
        interactor.rename(SaveCommunityInputData.forRename(owner, communityId, newName));
    }
}
