package use_case.save_community;

import java.util.List;

import entity.Community;
import use_case.shared.DataUnavailableException;

/**
 * Finds places by name. Owner: Member A.
 */
public interface PlaceSearchDataAccessInterface {

    /**
     * Searches Canadian places.
     *
     * @param query the name typed by the user
     * @return matching places, best first; empty if none
     * @throws DataUnavailableException if the search service cannot be reached
     */
    List<Community> searchPlaces(String query);
}
