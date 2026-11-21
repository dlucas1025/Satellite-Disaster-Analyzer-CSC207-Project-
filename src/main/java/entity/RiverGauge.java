package entity;

/**
 * A river measuring station. Owner: Lucas.
 *
 * @param stationId the official station id, e.g. "05SD005"
 * @param name      e.g. "Fisher River near Fisherton"
 * @param latitude  degrees north
 * @param longitude degrees east
 */
public record RiverGauge(String stationId, String name, double latitude, double longitude) {

    /**
     * Distance from a community to this gauge.
     *
     * @param community the community
     * @return kilometres
     */
    public double distanceKmTo(Community community) {
        return community.distanceKmTo(latitude, longitude);
    }
}
