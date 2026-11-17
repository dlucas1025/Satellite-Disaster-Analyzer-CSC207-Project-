package entity;

/**
 * A latitude/longitude rectangle, used for map pictures and area searches.
 *
 * <p>Shared contract. In the plain latitude/longitude projection (EPSG:4326) that
 * satellite services return, coordinates map linearly to pixels, so
 * {@link #toPixel} is all the maths a map needs.</p>
 *
 * @param minLat southern edge in degrees
 * @param minLon western edge in degrees
 * @param maxLat northern edge in degrees
 * @param maxLon eastern edge in degrees
 */
public record BoundingBox(double minLat, double minLon, double maxLat, double maxLon) {

    private static final double KM_PER_DEGREE_LATITUDE = 111.32;

    /**
     * Validates that the box is not empty or inverted.
     *
     * @throws IllegalArgumentException if a min edge is not below its max edge
     */
    public BoundingBox {
        if (minLat >= maxLat || minLon >= maxLon) {
            throw new IllegalArgumentException("min edges must be below max edges");
        }
    }

    /**
     * A square box centred on a point.
     *
     * @param lat      centre latitude in degrees
     * @param lon      centre longitude in degrees
     * @param radiusKm half the width of the square in kilometres
     * @return the bounding box
     */
    public static BoundingBox around(double lat, double lon, double radiusKm) {
        final double dLat = radiusKm / KM_PER_DEGREE_LATITUDE;
        final double dLon = radiusKm / (KM_PER_DEGREE_LATITUDE * Math.cos(Math.toRadians(lat)));
        return new BoundingBox(lat - dLat, lon - dLon, lat + dLat, lon + dLon);
    }

    /**
     * Whether a point lies inside the box (edges included).
     *
     * @param lat latitude in degrees
     * @param lon longitude in degrees
     * @return true if inside
     */
    public boolean contains(double lat, double lon) {
        return lat >= minLat && lat <= maxLat && lon >= minLon && lon <= maxLon;
    }

    /**
     * Converts a point to pixel coordinates on a picture of this box.
     *
     * @param lat    latitude in degrees
     * @param lon    longitude in degrees
     * @param width  picture width in pixels
     * @param height picture height in pixels
     * @return {x, y}, with (0, 0) at the top-left corner
     */
    public int[] toPixel(double lat, double lon, int width, int height) {
        final int x = (int) Math.round((lon - minLon) / (maxLon - minLon) * width);
        final int y = (int) Math.round((maxLat - lat) / (maxLat - minLat) * height);
        return new int[] {x, y};
    }
}
