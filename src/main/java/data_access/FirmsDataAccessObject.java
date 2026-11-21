package data_access;

import java.time.LocalDate;
import java.util.List;

import entity.BoundingBox;
import entity.FireDetection;
import use_case.nearby_fires.FireDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Satellite fire detections from NASA FIRMS. Owner: Hamza.
 */
public class FirmsDataAccessObject implements FireDataAccessInterface {

    private final String mapKey;
    private final FirmsCsvParser parser;

    /**
     * Creates the data access object.
     *
     * @param mapKey your FIRMS key (from ApiConfig, never hard-coded)
     * @param parser turns the CSV response into detections
     */
    public FirmsDataAccessObject(String mapKey, FirmsCsvParser parser) {
        this.mapKey = mapKey;
        this.parser = parser;
    }

    @Override
    public List<FireDetection> findFires(BoundingBox area, LocalDate lastDay, int days) {
        if (mapKey == null || mapKey.isBlank()) {
            throw new DataUnavailableException("No FIRMS key: add firms.map.key to config.properties");
        }
        // TODO Hamza: build the area URL (near-real-time source for recent days, standard source
        //  for past dates), fetch with OkHttp, return parser.parse(body)
        throw new DataUnavailableException("Fire detections are not implemented yet");
    }
}
