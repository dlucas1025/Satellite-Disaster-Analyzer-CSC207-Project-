package entity;

import java.time.LocalDateTime;

/**
 * One fire hot spot seen from a satellite. Owner: Member C.
 *
 * @param latitude          degrees north
 * @param longitude         degrees east
 * @param detectedAt        when the satellite saw it (UTC)
 * @param confidence        the service's confidence label, e.g. "high"
 * @param radiativePowerMw  fire intensity in megawatts
 */
public record FireDetection(double latitude, double longitude, LocalDateTime detectedAt,
                            String confidence, double radiativePowerMw) {

    /**
     * Distance from a community to this fire.
     *
     * @param community the community
     * @return kilometres
     */
    public double distanceKmTo(Community community) {
        return community.distanceKmTo(latitude, longitude);
    }
}
