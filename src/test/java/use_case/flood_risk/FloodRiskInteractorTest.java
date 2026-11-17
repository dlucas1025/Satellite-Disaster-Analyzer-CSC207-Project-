package use_case.flood_risk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for story 2. Owner: Member B.
 *
 * <p>Use fakes for RiverDataAccessInterface and FloodWeatherDataAccessInterface that return
 * hand-made forecasts, plus DemoCommunityProvider and a recording presenter. Test
 * FloodRiskModel's rules separately in entity/risk/FloodRiskModelTest.</p>
 */
class FloodRiskInteractorTest {

    @Test
    @Disabled("TODO Member B")
    void highFlowAndRainGiveHighRating() {
    }

    @Test
    @Disabled("TODO Member B")
    void noGaugeStillRatesAndSaysSo() {
    }

    @Test
    @Disabled("TODO Member B")
    void missingDataGivesCannotAssessNotLow() {
    }
}
