package use_case.nearby_fires;

import java.time.LocalDate;

/**
 * A request to check for nearby fires. Owner: Hamza.
 *
 * @param communityId the community
 * @param radiusKm    search radius, default 100
 * @param days        how many days back to look (1-5), default 3
 * @param asOf        last day to look at; null means today
 */
public record NearbyFiresInputData(String communityId, double radiusKm, int days, LocalDate asOf) {
}
