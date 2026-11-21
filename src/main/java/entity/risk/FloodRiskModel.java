package entity.risk;

import java.time.LocalDate;

import entity.Community;
import entity.RiskAssessment;

/**
 * Flood rules (Strategy). Owner: Lucas.
 *
 * <p>Starting points, to tune and document in the README:</p>
 * <ul>
 *   <li>River flow: forecast above the 90th percentile for the time of year +25 (99th: +45)</li>
 *   <li>Rain: 25 mm or more over 3 days +15 (50 mm: +25)</li>
 *   <li>Snowmelt: recent snow and 3+ days above 5 degrees +15</li>
 *   <li>Gauge: level rose more than 0.3 m in 48 hours +15</li>
 * </ul>
 */
public class FloodRiskModel implements RiskModel<FloodConditions> {

    @Override
    public RiskAssessment assess(Community community, FloodConditions conditions, LocalDate day) {
        // TODO Lucas: build a List<RiskFactor> from the rules above, then
        //  return new RiskAssessment(community, Hazard.FLOOD, day, factors)
        throw new UnsupportedOperationException("TODO Lucas: FloodRiskModel.assess");
    }
}
