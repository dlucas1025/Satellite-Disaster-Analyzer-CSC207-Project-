package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class RiskAssessmentTest {

    private final Community peguis = new Community("peguis-fn-mb", "Peguis First Nation", "MB", 51.33, -97.57);
    private final LocalDate day = LocalDate.of(2022, 5, 3);

    @Test
    void levelBoundaries() {
        assertEquals(RiskLevel.LOW, RiskLevel.fromScore(24));
        assertEquals(RiskLevel.MODERATE, RiskLevel.fromScore(25));
        assertEquals(RiskLevel.HIGH, RiskLevel.fromScore(50));
        assertEquals(RiskLevel.SEVERE, RiskLevel.fromScore(75));
        assertEquals(RiskLevel.SEVERE, RiskLevel.fromScore(100));
    }

    @Test
    void noFactorsMeansLow() {
        final RiskAssessment a = new RiskAssessment(peguis, Hazard.FLOOD, day, List.of());
        assertEquals(0, a.getScore());
        assertEquals(RiskLevel.LOW, a.getLevel());
    }

    @Test
    void scoreIsSumOfPoints() {
        final RiskAssessment a = new RiskAssessment(peguis, Hazard.FLOOD, day, List.of(
                new RiskFactor("River flow above 90th percentile", 25),
                new RiskFactor("30 mm of rain over 3 days", 15)));
        assertEquals(40, a.getScore());
        assertEquals(RiskLevel.MODERATE, a.getLevel());
    }

    @Test
    void scoreIsCappedAt100() {
        final RiskAssessment a = new RiskAssessment(peguis, Hazard.WILDFIRE, day, List.of(
                new RiskFactor("Fire within 10 km", 60),
                new RiskFactor("Wind toward community", 20),
                new RiskFactor("Many detections", 10),
                new RiskFactor("Intense fire", 10),
                new RiskFactor("Extra", 10)));
        assertEquals(100, a.getScore());
        assertTrue(a.isAtLeast(RiskLevel.SEVERE));
    }

    @Test
    void atLeastComparesLevels() {
        assertTrue(RiskLevel.HIGH.atLeast(RiskLevel.MODERATE));
        assertTrue(RiskLevel.HIGH.atLeast(RiskLevel.HIGH));
        assertFalse(RiskLevel.MODERATE.atLeast(RiskLevel.HIGH));
    }
}
