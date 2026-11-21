package data_access;

import java.nio.file.Path;
import java.time.LocalDate;

import entity.BoundingBox;
import entity.ImageType;
import entity.SatelliteImage;
import use_case.compare_imagery.CompareImageryDataAccessInterface;

/**
 * Decorator: saves each picture to disk the first time and reuses it afterwards,
 * so repeated views are fast and work offline. Owner: Youssef.
 */
public class CachingImageryDataAccessObject implements CompareImageryDataAccessInterface {

    private final CompareImageryDataAccessInterface source;
    private final Path cacheFolder;

    /**
     * Creates the decorator.
     *
     * @param source      where to fetch pictures that are not cached yet
     * @param cacheFolder e.g. ~/.northwatch/cache/images
     */
    public CachingImageryDataAccessObject(CompareImageryDataAccessInterface source, Path cacheFolder) {
        this.source = source;
        this.cacheFolder = cacheFolder;
    }

    @Override
    public SatelliteImage getImage(BoundingBox area, ImageType type, LocalDate date, int width, int height) {
        // TODO Youssef: build a file name from type, date, area and size; load the PNG if it exists,
        //  otherwise call source.getImage(...) and save it
        return source.getImage(area, type, date, width, height);
    }
}
