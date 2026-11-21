package entity.risk;

import entity.ImageType;

/**
 * Decides which pixels of a satellite picture show affected land (Strategy). Owner: Youssef.
 * A new hazard or picture type is a new class; the interactor does not change.
 */
public interface LandClassifier {

    /**
     * The picture type this classifier understands.
     *
     * @return the image type to request
     */
    ImageType imageType();

    /**
     * Whether a pixel shows affected land (water for floods, burn scar for fires).
     *
     * @param argb the pixel colour
     * @return true if affected
     */
    boolean isAffected(int argb);

    /**
     * Whether a pixel cannot be judged (cloud, no data).
     *
     * @param argb the pixel colour
     * @return true if unusable
     */
    boolean isUnusable(int argb);
}
