package entity.risk;

import java.time.LocalDate;

import entity.Community;
import entity.RiskAssessment;

/**
 * Wildfire rules (Strategy). Owner: Member C.
 *
 * <p>Starting points, to tune and document in the README:</p>
 * <ul>
 *   <li>Distance to nearest fire: under 10 km +60, 10-25 km +40, 25-50 km +25, 50-100 km +10</li>
 *   <li>Wind blowing from the fire toward the community (within 45 degrees) +20</li>
 *   <li>10 or more detections in the radius +10</li>
 *   <li>Any detection of 100 MW or more +10</li>
 * </ul>
 */
public class WildfireRiskModel implements RiskModel<FireConditions> {

    @Override
    public RiskAssessment assess(Community community, FireConditions conditions, LocalDate day) {
        // TODO Member C: use community.bearingTo(fire) and conditions.windFromDeg() for the wind rule;
        //  return new RiskAssessment(community, Hazard.WILDFIRE, day, factors)
        throw new UnsupportedOperationException("TODO Member C: WildfireRiskModel.assess");
    }
}
