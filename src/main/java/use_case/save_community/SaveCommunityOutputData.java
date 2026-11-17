package use_case.save_community;

import java.util.List;

import entity.Community;

/**
 * What the watchlist interactor hands to the presenter. Owner: Member A.
 *
 * @param searchResults places matching the last search (may be empty)
 * @param watchlist     the saved communities after the action
 * @param message       a short note for the user, e.g. "Already saved", or null
 */
public record SaveCommunityOutputData(List<Community> searchResults, List<Community> watchlist, String message) {
}
