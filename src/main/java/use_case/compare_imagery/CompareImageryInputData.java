package use_case.compare_imagery;

import java.time.LocalDate;

import entity.Hazard;

/**
 * A request to compare two pictures. Owner: Member D.
 *
 * @param communityId the community
 * @param hazard      FLOOD (water) or WILDFIRE (burned land)
 * @param before      the earlier date
 * @param after       the later date
 */
public record CompareImageryInputData(String communityId, Hazard hazard, LocalDate before, LocalDate after) {
}
