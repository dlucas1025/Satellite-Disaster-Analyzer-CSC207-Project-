package use_case.nearby_fires;

import java.time.LocalDate;

import entity.Community;
import use_case.shared.DataUnavailableException;

/**
 * Wind direction for the wildfire rules. Owner: Hamza.
 */
public interface WindDataAccessInterface {

    /**
     * The dominant wind direction at a community on a day.
     *
     * @param community the community
     * @param day       the day; past days use historical weather
     * @return degrees the wind blows FROM, clockwise from north
     * @throws DataUnavailableException if it cannot be fetched
     */
    double getWindFromDegrees(Community community, LocalDate day);
}
