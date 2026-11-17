package use_case.flood_risk;

import java.time.LocalDate;

/**
 * A request for a flood rating. Owner: Member B.
 *
 * @param communityId the community to rate
 * @param asOf        the date to rate from; null means today
 */
public record FloodRiskInputData(String communityId, LocalDate asOf) {
}
