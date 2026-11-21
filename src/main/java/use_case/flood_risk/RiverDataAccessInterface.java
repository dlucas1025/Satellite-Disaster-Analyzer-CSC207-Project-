package use_case.flood_risk;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import entity.Community;
import entity.FlowForecast;
import entity.FlowHistory;
import entity.GaugeReading;
import entity.RiverGauge;
import use_case.shared.DataUnavailableException;

/**
 * River flow and gauge data. Owner: Lucas.
 */
public interface RiverDataAccessInterface {

    /**
     * Forecast daily flow starting on a date.
     *
     * @param latitude  where to forecast (use the nearest gauge's position for small rivers)
     * @param longitude where to forecast
     * @param from      first day
     * @param days      number of days, e.g. 7
     * @return the forecast
     * @throws DataUnavailableException if it cannot be fetched
     */
    FlowForecast getFlowForecast(double latitude, double longitude, LocalDate from, int days);

    /**
     * Past daily flows for "usual for this time of year". Safe to cache: history does not change.
     *
     * @param latitude  where
     * @param longitude where
     * @return the history
     * @throws DataUnavailableException if it cannot be fetched
     */
    FlowHistory getFlowHistory(double latitude, double longitude);

    /**
     * The closest active river gauge.
     *
     * @param community the community
     * @param maxKm     search radius, e.g. 50
     * @return the gauge, or empty if none within range
     */
    Optional<RiverGauge> findNearestGauge(Community community, double maxKm);

    /**
     * Gauge readings for the 2 days before a date.
     *
     * @param gauge the gauge
     * @param upTo  the last day
     * @return readings in time order
     * @throws DataUnavailableException if they cannot be fetched
     */
    List<GaugeReading> getRecentReadings(RiverGauge gauge, LocalDate upTo);
}
