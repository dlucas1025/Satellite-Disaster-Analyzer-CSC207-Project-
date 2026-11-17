package use_case.flood_risk;

import java.util.Optional;

import entity.Community;
import entity.risk.FloodConditions;
import entity.risk.RiskModel;
import use_case.shared.CommunityProvider;

/**
 * Rates flood risk for the next 7 days. Owner: Member B.
 */
public class FloodRiskInteractor implements FloodRiskInputBoundary {

    /** Number of days rated. */
    public static final int DAYS = 7;
    /** How far to look for a river gauge. */
    public static final double GAUGE_RADIUS_KM = 50;

    private final CommunityProvider communities;
    private final RiverDataAccessInterface riverData;
    private final FloodWeatherDataAccessInterface weatherData;
    private final RiskModel<FloodConditions> model;
    private final FloodRiskOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param communities where communities come from
     * @param riverData   river flow and gauges
     * @param weatherData rain, snow and temperature
     * @param model       the flood rules
     * @param presenter   receives the result
     */
    public FloodRiskInteractor(CommunityProvider communities, RiverDataAccessInterface riverData,
                               FloodWeatherDataAccessInterface weatherData, RiskModel<FloodConditions> model,
                               FloodRiskOutputBoundary presenter) {
        this.communities = communities;
        this.riverData = riverData;
        this.weatherData = weatherData;
        this.model = model;
        this.presenter = presenter;
    }

    @Override
    public void execute(FloodRiskInputData input) {
        final Optional<Community> community = communities.findById(input.communityId());
        if (community.isEmpty()) {
            presenter.prepareFailView("Unknown community: " + input.communityId());
            return;
        }
        // TODO Member B:
        //  1. date = input.asOf() or today
        //  2. find nearest gauge; query flow at the gauge's position if there is one
        //  3. get forecast, history, weather, recent readings (catch DataUnavailableException -> fail view)
        //  4. for each of the DAYS days: model.assess(community, conditions, day)
        //  5. presenter.prepareSuccessView(new FloodRiskOutputData(...))
        presenter.prepareFailView("Cannot assess: flood risk is not implemented yet");
    }
}
