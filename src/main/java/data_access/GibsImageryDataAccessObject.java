package data_access;

import java.time.LocalDate;

import entity.BoundingBox;
import entity.ImageType;
import entity.SatelliteImage;
import use_case.compare_imagery.CompareImageryDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Satellite pictures from NASA GIBS. Owner: Member D.
 */
public class GibsImageryDataAccessObject implements CompareImageryDataAccessInterface {

    @Override
    public SatelliteImage getImage(BoundingBox area, ImageType type, LocalDate date, int width, int height) {
        // TODO Member D: map ImageType to a GIBS layer name; request a PNG (never JPEG) for the box;
        //  decode with javax.imageio.ImageIO here, then copy the pixels into a SatelliteImage
        throw new DataUnavailableException("Satellite pictures are not implemented yet");
    }
}
