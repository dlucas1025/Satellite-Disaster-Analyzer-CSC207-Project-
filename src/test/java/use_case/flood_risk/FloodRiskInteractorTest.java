package use_case.flood_risk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for story 2. Owner: Lucas.
 *
 * <p>Use fakes for RiverDataAccessInterface and FloodWeatherDataAccessInterface that return
 * hand-made forecasts, plus DemoCommunityProvider and a recording presenter. Test
 * FloodRiskModel's rules separately in entity/risk/FloodRiskModelTest.</p>
 */
class FloodRiskInteractorTest {

    @Test
    @Disabled("TODO Lucas")
    void highFlowAndRainGiveHighRating() {
    }

    @Test
    @Disabled("TODO Lucas")
    void noGaugeStillRatesAndSaysSo() {
    }

    @Test
    @Disabled("TODO Lucas")
    void missingDataGivesCannotAssessNotLow() {
    }
}
