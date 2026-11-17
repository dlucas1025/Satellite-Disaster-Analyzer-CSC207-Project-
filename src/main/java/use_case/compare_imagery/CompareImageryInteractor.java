package use_case.compare_imagery;

import java.util.Map;
import java.util.Optional;

import entity.Community;
import entity.Hazard;
import entity.risk.LandClassifier;
import use_case.shared.CommunityProvider;

/**
 * Compares satellite pictures from two dates. Owner: Member D.
 */
public class CompareImageryInteractor implements CompareImageryInputBoundary {

    /** Size of the area shown around the community. */
    public static final double AREA_RADIUS_KM = 25;
    /** Above this share of unusable pixels a figure is flagged as unreliable. */
    public static final double MAX_UNUSABLE = 0.5;

    private final CommunityProvider communities;
    private final CompareImageryDataAccessInterface imagery;
    private final Map<Hazard, LandClassifier> classifiers;
    private final CompareImageryOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param communities where communities come from
     * @param imagery     satellite pictures
     * @param classifiers one classifier per hazard (water for FLOOD, burn scar for WILDFIRE)
     * @param presenter   receives the result
     */
    public CompareImageryInteractor(CommunityProvider communities, CompareImageryDataAccessInterface imagery,
                                    Map<Hazard, LandClassifier> classifiers,
                                    CompareImageryOutputBoundary presenter) {
        this.communities = communities;
        this.imagery = imagery;
        this.classifiers = Map.copyOf(classifiers);
        this.presenter = presenter;
    }

    @Override
    public void execute(CompareImageryInputData input) {
        final Optional<Community> community = communities.findById(input.communityId());
        if (community.isEmpty()) {
            presenter.prepareFailView("Unknown community: " + input.communityId());
            return;
        }
        final LandClassifier classifier = classifiers.get(input.hazard());
        if (classifier == null) {
            presenter.prepareFailView("No classifier for " + input.hazard());
            return;
        }
        // TODO Member D:
        //  1. check before < after
        //  2. area = community.areaAround(AREA_RADIUS_KM); fetch both pictures of classifier.imageType()
        //  3. fractions with image.affectedFraction(classifier); reliable if unusableFraction <= MAX_UNUSABLE
        //  4. presenter.prepareSuccessView(...); catch DataUnavailableException -> fail view
        presenter.prepareFailView("Satellite comparison is not implemented yet");
    }
}
