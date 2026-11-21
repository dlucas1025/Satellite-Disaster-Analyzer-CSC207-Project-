package use_case.nearby_fires;

import java.time.LocalDate;
import java.util.List;

import entity.BoundingBox;
import entity.FireDetection;
import use_case.shared.DataUnavailableException;

/**
 * Satellite fire detections. Owner: Hamza.
 */
public interface FireDataAccessInterface {

    /**
     * Fires detected in an area over a period.
     *
     * @param area    the box to search
     * @param lastDay the last day of the period
     * @param days    1-5 days ending on lastDay
     * @return detections, possibly empty
     * @throws DataUnavailableException if they cannot be fetched
     */
    List<FireDetection> findFires(BoundingBox area, LocalDate lastDay, int days);
}
