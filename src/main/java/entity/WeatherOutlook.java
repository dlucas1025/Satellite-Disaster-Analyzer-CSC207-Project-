package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * Daily weather around a community, forecast or historical. Owner: Lucas.
 *
 * @param firstDay        date of the first value
 * @param precipitationMm rain and melted snow per day
 * @param snowfallCm      snowfall per day
 * @param maxTempC        daily high temperature
 */
public record WeatherOutlook(LocalDate firstDay, List<Double> precipitationMm,
                             List<Double> snowfallCm, List<Double> maxTempC) {

    /**
     * The largest rain total over any run of consecutive days.
     *
     * @param days the run length, e.g. 3
     * @return millimetres
     */
    public double maxRainOver(int days) {
        // TODO Lucas
        throw new UnsupportedOperationException("TODO Lucas: WeatherOutlook.maxRainOver");
    }

    /**
     * How many days are warm enough to melt snow.
     *
     * @param thresholdC e.g. 5 degrees
     * @return the number of days above the threshold
     */
    public int warmDays(double thresholdC) {
        // TODO Lucas
        throw new UnsupportedOperationException("TODO Lucas: WeatherOutlook.warmDays");
    }
}
