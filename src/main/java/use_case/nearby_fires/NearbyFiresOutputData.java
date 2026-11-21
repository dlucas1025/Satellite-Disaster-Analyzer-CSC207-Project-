package use_case.nearby_fires;

import java.util.List;

import entity.BoundingBox;
import entity.FireDetection;
import entity.RiskAssessment;

/**
 * Nearby fires and the threat rating. Owner: Hamza.
 *
 * @param communityName     for the heading
 * @param area              the searched area, for drawing the map
 * @param fires             detections inside the radius
 * @param nearestKm         distance to the nearest fire, or NaN if none
 * @param nearestBearingDeg direction of the nearest fire from the community, or NaN
 * @param windFromDeg       direction the wind blows from
 * @param assessment        the wildfire rating and reasons
 */
public record NearbyFiresOutputData(String communityName, BoundingBox area, List<FireDetection> fires,
                                    double nearestKm, double nearestBearingDeg, double windFromDeg,
                                    RiskAssessment assessment) {
}
