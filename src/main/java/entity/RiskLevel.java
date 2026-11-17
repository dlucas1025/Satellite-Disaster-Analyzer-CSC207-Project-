package entity;

/**
 * A risk rating from Low to Severe, derived from a 0-100 score.
 *
 * <p>Shared contract used by the flood and wildfire stories:
 * 0-24 Low, 25-49 Moderate, 50-74 High, 75-100 Severe.</p>
 */
public enum RiskLevel {
    LOW(0),
    MODERATE(25),
    HIGH(50),
    SEVERE(75);

    private final int minScore;

    RiskLevel(int minScore) {
        this.minScore = minScore;
    }

    /**
     * The lowest score that earns this level.
     *
     * @return the minimum score
     */
    public int getMinScore() {
        return minScore;
    }

    /**
     * Converts a score to a level.
     *
     * @param score a score from 0 to 100
     * @return the matching level
     * @throws IllegalArgumentException if the score is outside 0 to 100
     */
    public static RiskLevel fromScore(int score) {
        if (score < 0 || score > RiskAssessment.MAX_SCORE) {
            throw new IllegalArgumentException("score must be 0-100: " + score);
        }
        RiskLevel result = LOW;
        for (RiskLevel level : values()) {
            if (score >= level.minScore) {
                result = level;
            }
        }
        return result;
    }

    /**
     * Whether this level is the same as or more serious than another.
     *
     * @param other the level to compare with
     * @return true if this level is at least {@code other}
     */
    public boolean atLeast(RiskLevel other) {
        return this.compareTo(other) >= 0;
    }
}
