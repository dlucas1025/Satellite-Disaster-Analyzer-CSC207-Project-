package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CommunityTest {

    private final Community winnipeg = new Community("wpg", "Winnipeg", "MB", 49.8951, -97.1384);

    @Test
    void distanceToSelfIsZero() {
        assertEquals(0.0, winnipeg.distanceKmTo(49.8951, -97.1384), 1e-9);
    }

    @Test
    void distanceWinnipegToBrandonIsAbout200Km() {
        final double km = winnipeg.distanceKmTo(49.8485, -99.9501);
        assertEquals(202.0, km, 3.0);
    }

    @Test
    void bearingDueNorthIsZero() {
        assertEquals(0.0, winnipeg.bearingTo(51.0, -97.1384), 0.01);
    }

    @Test
    void bearingDueWestIsAbout270() {
        assertEquals(270.0, winnipeg.bearingTo(49.8951, -98.0), 1.0);
    }

    @Test
    void areaAroundContainsCommunity() {
        final BoundingBox box = winnipeg.areaAround(50);
        assertTrue(box.contains(winnipeg.latitude(), winnipeg.longitude()));
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new Community("x", " ", "MB", 50, -97));
    }

    @Test
    void rejectsLatitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> new Community("x", "X", "MB", 91, -97));
    }
}
