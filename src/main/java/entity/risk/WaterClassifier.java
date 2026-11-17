package entity.risk;

import entity.ImageType;

/**
 * Finds flood water. Owner: Member D.
 */
public class WaterClassifier implements LandClassifier {

    @Override
    public ImageType imageType() {
        // TODO Member D: FLOOD_MAP (fixed legend colours) or FALSE_COLOUR (dark blue water)
        return ImageType.FLOOD_MAP;
    }

    @Override
    public boolean isAffected(int argb) {
        // TODO Member D: compare against the flood legend colours (count pixels in PNGs only)
        throw new UnsupportedOperationException("TODO Member D: WaterClassifier.isAffected");
    }

    @Override
    public boolean isUnusable(int argb) {
        // TODO Member D: the legend's "insufficient data" colour, or transparent pixels
        throw new UnsupportedOperationException("TODO Member D: WaterClassifier.isUnusable");
    }
}
