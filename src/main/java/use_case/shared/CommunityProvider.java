package use_case.shared;

import java.util.List;
import java.util.Optional;

import entity.Community;

/**
 * Where the other stories get the user's communities from.
 *
 * <p>Shared contract that keeps stories 2-4 independent of story 1. Until Member A's
 * watchlist is merged, {@code AppBuilder} supplies {@code data_access.DemoCommunityProvider}
 * (Peguis First Nation and Jasper). When it is merged, A's watchlist data access object
 * also implements this interface and one line in {@code AppBuilder} switches over
 * (Dependency Inversion).</p>
 *
 * <p>Interactors should receive a community id in their input data and look the
 * community up here.</p>
 */
public interface CommunityProvider {

    /**
     * All communities the user can choose from, in display order.
     *
     * @return the communities; never null
     */
    List<Community> getCommunities();

    /**
     * Looks up one community.
     *
     * @param id the community's id
     * @return the community, or empty if there is none with that id
     */
    Optional<Community> findById(String id);
}
