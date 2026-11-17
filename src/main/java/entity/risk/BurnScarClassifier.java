package entity.risk;

import entity.ImageType;

/**
 * Finds burned land. Owner: Member D.
 */
public class BurnScarClassifier implements LandClassifier {

    @Override
    public ImageType imageType() {
        return ImageType.FALSE_COLOUR;
    }

    @Override
    public boolean isAffected(int argb) {
        // TODO Member D: in false colour, burn scars are red-brown (red high, green and blue low)
        throw new UnsupportedOperationException("TODO Member D: BurnScarClassifier.isAffected");
    }

    @Override
    public boolean isUnusable(int argb) {
        // TODO Member D: clouds are bright cyan-white in false colour
        throw new UnsupportedOperationException("TODO Member D: BurnScarClassifier.isUnusable");
    }
}
