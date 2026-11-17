package entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BoundingBoxTest {

    private final BoundingBox box = new BoundingBox(50.0, -98.0, 52.0, -96.0);

    @Test
    void containsCentreButNotOutside() {
        assertTrue(box.contains(51.0, -97.0));
        assertFalse(box.contains(53.0, -97.0));
    }

    @Test
    void cornersMapToPictureCorners() {
        assertArrayEquals(new int[] {0, 0}, box.toPixel(52.0, -98.0, 200, 100));
        assertArrayEquals(new int[] {200, 100}, box.toPixel(50.0, -96.0, 200, 100));
    }

    @Test
    void centreMapsToPictureCentre() {
        assertArrayEquals(new int[] {100, 50}, box.toPixel(51.0, -97.0, 200, 100));
    }

    @Test
    void rejectsInvertedBox() {
        assertThrows(IllegalArgumentException.class, () -> new BoundingBox(52.0, -98.0, 50.0, -96.0));
    }
}
