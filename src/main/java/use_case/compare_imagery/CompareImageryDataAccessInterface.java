package use_case.compare_imagery;

import java.time.LocalDate;

import entity.BoundingBox;
import entity.ImageType;
import entity.SatelliteImage;
import use_case.shared.DataUnavailableException;

/**
 * Satellite pictures. Owner: Member D.
 *
 * <p>Implemented by the online source and by the caching decorator that wraps it;
 * both must behave the same (Liskov), which a shared contract test checks.</p>
 */
public interface CompareImageryDataAccessInterface {

    /**
     * One picture of an area on a date.
     *
     * @param area   the area
     * @param type   picture type
     * @param date   the date
     * @param width  pixels across
     * @param height pixels down
     * @return the picture
     * @throws DataUnavailableException if there is no picture for that date or it cannot be fetched
     */
    SatelliteImage getImage(BoundingBox area, ImageType type, LocalDate date, int width, int height);
}
