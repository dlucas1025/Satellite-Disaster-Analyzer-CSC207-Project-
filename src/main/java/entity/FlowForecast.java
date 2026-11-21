package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * Forecast daily river flow at one place. Owner: Lucas.
 *
 * @param firstDay       the date of the first value
 * @param dailyFlowM3s   expected flow for each day, in cubic metres per second
 */
public record FlowForecast(LocalDate firstDay, List<Double> dailyFlowM3s) {

    /**
     * The highest forecast flow.
     *
     * @return the peak flow in m3/s
     */
    public double peak() {
        // TODO Lucas
        throw new UnsupportedOperationException("TODO Lucas: FlowForecast.peak");
    }

    /**
     * The date of the highest forecast flow.
     *
     * @return the peak date
     */
    public LocalDate peakDay() {
        // TODO Lucas
        throw new UnsupportedOperationException("TODO Lucas: FlowForecast.peakDay");
    }
}
