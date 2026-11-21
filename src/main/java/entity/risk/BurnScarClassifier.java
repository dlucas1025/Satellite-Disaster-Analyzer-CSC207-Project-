package entity.risk;

import entity.ImageType;

/**
 * Finds burned land. Owner: Youssef.
 */
public class BurnScarClassifier implements LandClassifier {

    @Override
    public ImageType imageType() {
        return ImageType.FALSE_COLOUR;
    }

    @Override
    public boolean isAffected(int argb) {
        // TODO Youssef: in false colour, burn scars are red-brown (red high, green and blue low)
        throw new UnsupportedOperationException("TODO Youssef: BurnScarClassifier.isAffected");
    }

    @Override
    public boolean isUnusable(int argb) {
        // TODO Youssef: clouds are bright cyan-white in false colour
        throw new UnsupportedOperationException("TODO Youssef: BurnScarClassifier.isUnusable");
    }
}
