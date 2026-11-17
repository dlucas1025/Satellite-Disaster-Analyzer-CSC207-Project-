package data_access;

import java.util.List;

import entity.Community;
import use_case.save_community.PlaceSearchDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Searches Canadian places with an online geocoding service. Owner: Member A.
 */
public class GeocodingDataAccessObject implements PlaceSearchDataAccessInterface {

    @Override
    public List<Community> searchPlaces(String query) {
        // TODO Member A: call the geocoding API (Canada only), parse JSON with org.json,
        //  build one Community per result; wrap IO errors in DataUnavailableException
        throw new DataUnavailableException("Place search is not implemented yet");
    }
}
