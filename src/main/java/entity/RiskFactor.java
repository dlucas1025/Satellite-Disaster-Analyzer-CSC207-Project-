package entity;

/**
 * One reason behind a risk rating, such as "50 mm of rain forecast over 3 days".
 * Shared contract.
 *
 * @param description a sentence a non-expert understands
 * @param points      how much this reason adds to the score (0 or more)
 */
public record RiskFactor(String description, int points) {

    /**
     * Validates the fields.
     *
     * @throws IllegalArgumentException if the description is blank or points are negative
     */
    public RiskFactor {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be blank");
        }
        if (points < 0) {
            throw new IllegalArgumentException("points must not be negative");
        }
    }
}
