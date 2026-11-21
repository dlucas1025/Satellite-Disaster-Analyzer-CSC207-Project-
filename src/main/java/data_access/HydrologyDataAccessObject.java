package data_access;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import entity.Community;
import entity.FlowForecast;
import entity.FlowHistory;
import entity.GaugeReading;
import entity.RiverGauge;
import use_case.flood_risk.RiverDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * River flow forecasts, flow history and river gauges from online services. Owner: Lucas.
 */
public class HydrologyDataAccessObject implements RiverDataAccessInterface {

    @Override
    public FlowForecast getFlowForecast(double latitude, double longitude, LocalDate from, int days) {
        // TODO Lucas: flood forecast API (daily river discharge)
        throw new DataUnavailableException("River flow forecast is not implemented yet");
    }

    @Override
    public FlowHistory getFlowHistory(double latitude, double longitude) {
        // TODO Lucas: flood API history back to 1984; cache to ~/.northwatch/cache/flow/
        throw new DataUnavailableException("River flow history is not implemented yet");
    }

    @Override
    public Optional<RiverGauge> findNearestGauge(Community community, double maxKm) {
        // TODO Lucas: Water Survey of Canada stations in community.areaAround(maxKm), pick the closest
        return Optional.empty();
    }

    @Override
    public List<GaugeReading> getRecentReadings(RiverGauge gauge, LocalDate upTo) {
        // TODO Lucas: real-time (last ~30 days) or historical daily means for older dates
        throw new DataUnavailableException("Gauge readings are not implemented yet");
    }
}
