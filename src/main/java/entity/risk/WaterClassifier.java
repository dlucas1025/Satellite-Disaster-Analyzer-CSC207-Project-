package entity.risk;

import entity.ImageType;

/**
 * Finds flood water. Owner: Youssef.
 */
public class WaterClassifier implements LandClassifier {

    @Override
    public ImageType imageType() {
        // TODO Youssef: FLOOD_MAP (fixed legend colours) or FALSE_COLOUR (dark blue water)
        return ImageType.FLOOD_MAP;
    }

    @Override
    public boolean isAffected(int argb) {
        // TODO Youssef: compare against the flood legend colours (count pixels in PNGs only)
        throw new UnsupportedOperationException("TODO Youssef: WaterClassifier.isAffected");
    }

    @Override
    public boolean isUnusable(int argb) {
        // TODO Youssef: the legend's "insufficient data" colour, or transparent pixels
        throw new UnsupportedOperationException("TODO Youssef: WaterClassifier.isUnusable");
    }
}
