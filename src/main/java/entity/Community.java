package entity;

/**
 * A place the user watches, such as a First Nation, town or farm.
 *
 * <p>Shared contract: every user story takes a Community as its input.
 * Changes to this class need a team-reviewed pull request.</p>
 *
 * @param id        a stable unique id, e.g. "peguis-fn-mb"
 * @param name      the display name, e.g. "Peguis First Nation"
 * @param province  the province or territory, e.g. "MB"
 * @param latitude  degrees north, -90 to 90
 * @param longitude degrees east, -180 to 180 (negative in Canada)
 */
public record Community(String id, String name, String province, double latitude, double longitude) {

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double MAX_LATITUDE = 90.0;
    private static final double MAX_LONGITUDE = 180.0;
    private static final double FULL_CIRCLE = 360.0;

    /**
     * Validates the fields.
     *
     * @throws IllegalArgumentException if a field is missing or a coordinate is out of range
     */
    public Community {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (Math.abs(latitude) > MAX_LATITUDE) {
            throw new IllegalArgumentException("latitude out of range: " + latitude);
        }
        if (Math.abs(longitude) > MAX_LONGITUDE) {
            throw new IllegalArgumentException("longitude out of range: " + longitude);
        }
    }

    /**
     * Great-circle distance from this community to a point.
     *
     * @param lat latitude of the point in degrees
     * @param lon longitude of the point in degrees
     * @return the distance in kilometres
     */
    public double distanceKmTo(double lat, double lon) {
        final double dLat = Math.toRadians(lat - latitude);
        final double dLon = Math.toRadians(lon - longitude);
        final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * EARTH_RADIUS_KM * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    /**
     * Initial compass bearing from this community to a point.
     *
     * @param lat latitude of the point in degrees
     * @param lon longitude of the point in degrees
     * @return degrees clockwise from north, from 0 (inclusive) to 360 (exclusive)
     */
    public double bearingTo(double lat, double lon) {
        final double phi1 = Math.toRadians(latitude);
        final double phi2 = Math.toRadians(lat);
        final double dLon = Math.toRadians(lon - longitude);
        final double y = Math.sin(dLon) * Math.cos(phi2);
        final double x = Math.cos(phi1) * Math.sin(phi2) - Math.sin(phi1) * Math.cos(phi2) * Math.cos(dLon);
        final double degrees = Math.toDegrees(Math.atan2(y, x));
        return (degrees + FULL_CIRCLE) % FULL_CIRCLE;
    }

    /**
     * The square area around this community, for map and satellite requests.
     *
     * @param radiusKm half the width of the square in kilometres
     * @return the bounding box
     */
    public BoundingBox areaAround(double radiusKm) {
        return BoundingBox.around(latitude, longitude, radiusKm);
    }
}
