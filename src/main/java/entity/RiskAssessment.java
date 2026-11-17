package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * The result of rating one hazard for one community on one date.
 *
 * <p>Shared contract. The score is the sum of the factors' points, capped at
 * {@link #MAX_SCORE}; the level follows from the score.</p>
 */
public final class RiskAssessment {

    /** The highest possible score. */
    public static final int MAX_SCORE = 100;

    private final Community community;
    private final Hazard hazard;
    private final LocalDate asOf;
    private final List<RiskFactor> factors;
    private final int score;
    private final RiskLevel level;

    /**
     * Creates an assessment from its reasons.
     *
     * @param community the community rated
     * @param hazard    the hazard rated
     * @param asOf      the date the rating applies to
     * @param factors   the reasons; an empty list means Low
     */
    public RiskAssessment(Community community, Hazard hazard, LocalDate asOf, List<RiskFactor> factors) {
        if (community == null || hazard == null || asOf == null || factors == null) {
            throw new IllegalArgumentException("arguments must not be null");
        }
        this.community = community;
        this.hazard = hazard;
        this.asOf = asOf;
        this.factors = List.copyOf(factors);
        int total = 0;
        for (RiskFactor factor : this.factors) {
            total += factor.points();
        }
        this.score = Math.min(total, MAX_SCORE);
        this.level = RiskLevel.fromScore(this.score);
    }

    public Community getCommunity() {
        return community;
    }

    public Hazard getHazard() {
        return hazard;
    }

    public LocalDate getAsOf() {
        return asOf;
    }

    public List<RiskFactor> getFactors() {
        return factors;
    }

    public int getScore() {
        return score;
    }

    public RiskLevel getLevel() {
        return level;
    }

    /**
     * Whether the rating is the same as or more serious than a level.
     *
     * @param threshold the level to compare with
     * @return true if this rating reaches the threshold
     */
    public boolean isAtLeast(RiskLevel threshold) {
        return level.atLeast(threshold);
    }
}
