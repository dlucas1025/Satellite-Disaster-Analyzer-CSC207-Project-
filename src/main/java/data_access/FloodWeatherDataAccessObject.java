package data_access;

import java.time.LocalDate;

import entity.Community;
import entity.WeatherOutlook;
import use_case.flood_risk.FloodWeatherDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Rain, snow and temperature from an online weather service. Owner: Lucas.
 */
public class FloodWeatherDataAccessObject implements FloodWeatherDataAccessInterface {

    @Override
    public WeatherOutlook getWeather(Community community, LocalDate from, int days) {
        // TODO Lucas: forecast API for today, historical weather API for past dates
        throw new DataUnavailableException("Weather is not implemented yet");
    }
}
