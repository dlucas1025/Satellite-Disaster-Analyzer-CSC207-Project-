package use_case.flood_risk;

import java.time.LocalDate;

import entity.Community;
import entity.WeatherOutlook;
import use_case.shared.DataUnavailableException;

/**
 * Rain, snow and temperature for the flood rules. Owner: Member B.
 */
public interface FloodWeatherDataAccessInterface {

    /**
     * Daily weather around a community, covering the 30 days before {@code from}
     * (for recent snow) and the following days (for the forecast).
     *
     * @param community the community
     * @param from      the as-of date; past dates use historical weather
     * @param days      forecast days, e.g. 7
     * @return the weather
     * @throws DataUnavailableException if it cannot be fetched
     */
    WeatherOutlook getWeather(Community community, LocalDate from, int days);
}
