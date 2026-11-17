package use_case.nearby_fires;

import java.util.Optional;

import entity.Community;
import entity.risk.FireConditions;
import entity.risk.RiskModel;
import use_case.shared.CommunityProvider;

/**
 * Finds nearby fires, the wind direction and the threat rating. Owner: Member C.
 */
public class NearbyFiresInteractor implements NearbyFiresInputBoundary {

    private final CommunityProvider communities;
    private final FireDataAccessInterface fireData;
    private final WindDataAccessInterface windData;
    private final RiskModel<FireConditions> model;
    private final NearbyFiresOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param communities where communities come from
     * @param fireData    satellite fire detections
     * @param windData    wind direction
     * @param model       the wildfire rules
     * @param presenter   receives the result
     */
    public NearbyFiresInteractor(CommunityProvider communities, FireDataAccessInterface fireData,
                                 WindDataAccessInterface windData, RiskModel<FireConditions> model,
                                 NearbyFiresOutputBoundary presenter) {
        this.communities = communities;
        this.fireData = fireData;
        this.windData = windData;
        this.model = model;
        this.presenter = presenter;
    }

    @Override
    public void execute(NearbyFiresInputData input) {
        final Optional<Community> community = communities.findById(input.communityId());
        if (community.isEmpty()) {
            presenter.prepareFailView("Unknown community: " + input.communityId());
            return;
        }
        // TODO Member C:
        //  1. area = community.areaAround(radius); fires = fireData.findFires(...)
        //  2. keep only fires within radiusKm (the box corners are further than the radius)
        //  3. nearest fire distance and bearing; wind = windData.getWindFromDegrees(...)
        //  4. assessment = model.assess(community, new FireConditions(...), day)
        //  5. presenter.prepareSuccessView(...); catch DataUnavailableException -> fail view
        presenter.prepareFailView("Wildfire check is not implemented yet");
    }
}
