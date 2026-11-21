package use_case.flood_risk;

import java.util.List;

import entity.RiskAssessment;

/**
 * The 7-day flood rating. Owner: Lucas.
 *
 * @param communityName  for the heading
 * @param dailyRatings   one assessment per day, in date order
 * @param forecastFlow   forecast flow per day, for the chart
 * @param usualFlow      typical flow per day, for the chart
 * @param usedGauge      false if no gauge was close enough and the rating uses forecasts only
 */
public record FloodRiskOutputData(String communityName, List<RiskAssessment> dailyRatings,
                                  List<Double> forecastFlow, List<Double> usualFlow, boolean usedGauge) {
}
