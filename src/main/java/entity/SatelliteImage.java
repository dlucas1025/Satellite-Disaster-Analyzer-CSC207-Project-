package entity;

import java.time.LocalDate;

import entity.risk.LandClassifier;

/**
 * A satellite picture of an area on a date. Owner: Youssef.
 *
 * <p>Holds raw ARGB pixel values rather than a java.awt BufferedImage so the entity
 * layer has no GUI imports; the view converts the pixels for display.</p>
 */
public final class SatelliteImage {

    private final ImageType type;
    private final LocalDate date;
    private final BoundingBox area;
    private final int width;
    private final int height;
    private final int[] pixels;

    /**
     * Creates the image.
     *
     * @param type   picture type
     * @param date   the date it shows
     * @param area   the area it covers
     * @param width  pixels across
     * @param height pixels down
     * @param pixels ARGB values, row by row from the top-left; length width * height
     */
    public SatelliteImage(ImageType type, LocalDate date, BoundingBox area, int width, int height, int[] pixels) {
        if (pixels.length != width * height) {
            throw new IllegalArgumentException("pixels must have width * height values");
        }
        this.type = type;
        this.date = date;
        this.area = area;
        this.width = width;
        this.height = height;
        this.pixels = pixels.clone();
    }

    public ImageType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public BoundingBox getArea() {
        return area;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * A copy of the pixel values.
     *
     * @return ARGB values, row by row
     */
    public int[] getPixels() {
        return pixels.clone();
    }

    /**
     * Share of usable pixels that the classifier marks as affected (flooded or burned).
     *
     * @param classifier the rule for one hazard
     * @return a fraction from 0 to 1
     */
    public double affectedFraction(LandClassifier classifier) {
        // TODO Youssef: count classifier.isAffected over pixels that are not classifier.isUnusable
        throw new UnsupportedOperationException("TODO Youssef: SatelliteImage.affectedFraction");
    }

    /**
     * Share of pixels hidden by cloud or missing data.
     *
     * @param classifier the rule for one hazard
     * @return a fraction from 0 to 1
     */
    public double unusableFraction(LandClassifier classifier) {
        // TODO Youssef
        throw new UnsupportedOperationException("TODO Youssef: SatelliteImage.unusableFraction");
    }
}
