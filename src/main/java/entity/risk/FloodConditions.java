package entity.risk;

import java.util.List;

import entity.FlowForecast;
import entity.FlowHistory;
import entity.GaugeReading;
import entity.WeatherOutlook;

/**
 * Everything the flood rules look at. Owner: Lucas.
 *
 * @param forecast       forecast river flow
 * @param history        past flows for "usual for this time of year"
 * @param weather        rain, snow and temperature
 * @param gaugeReadings  recent readings from the nearest gauge; empty if no gauge within range
 */
public record FloodConditions(FlowForecast forecast, FlowHistory history,
                              WeatherOutlook weather, List<GaugeReading> gaugeReadings) {
}
