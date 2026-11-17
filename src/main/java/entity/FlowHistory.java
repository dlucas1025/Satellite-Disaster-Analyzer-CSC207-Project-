package entity;

import java.util.List;
import java.util.Map;

/**
 * Past daily river flows at one place, grouped by day of the year,
 * used to tell what is "usual" for this time of year. Owner: Member B.
 *
 * @param flowsByDayOfYear for each day of the year (1-366), the flows seen on that day in past years
 */
public record FlowHistory(Map<Integer, List<Double>> flowsByDayOfYear) {

    /**
     * Where a flow ranks among past flows for the same time of year.
     *
     * @param dayOfYear 1-366
     * @param flowM3s   the flow to rank
     * @return a percentile from 0 to 100
     */
    public double percentileOf(int dayOfYear, double flowM3s) {
        // TODO Member B: consider a window of a few days around dayOfYear for more samples
        throw new UnsupportedOperationException("TODO Member B: FlowHistory.percentileOf");
    }

    /**
     * The typical flow for a day of the year.
     *
     * @param dayOfYear 1-366
     * @return the median past flow in m3/s
     */
    public double median(int dayOfYear) {
        // TODO Member B
        throw new UnsupportedOperationException("TODO Member B: FlowHistory.median");
    }
}
