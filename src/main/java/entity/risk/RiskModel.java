package entity.risk;

import java.time.LocalDate;

import entity.Community;
import entity.RiskAssessment;

/**
 * A set of rules that rates one hazard (Strategy pattern).
 *
 * <p>Shared contract. Member B writes {@code FloodRiskModel implements RiskModel<FloodConditions>}
 * and Member C writes {@code WildfireRiskModel implements RiskModel<FireConditions>}, each with
 * their own conditions class in this package. A new hazard or a better rule is a new class;
 * no interactor changes (Open/Closed).</p>
 *
 * @param <T> the observations and forecasts the rules need
 */
public interface RiskModel<T> {

    /**
     * Rates the hazard.
     *
     * @param community  the community being rated
     * @param conditions the data the rules look at
     * @param asOf       the date the rating applies to
     * @return the rating with its reasons
     */
    RiskAssessment assess(Community community, T conditions, LocalDate asOf);
}
