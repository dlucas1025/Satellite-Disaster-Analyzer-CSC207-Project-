package interface_adapter.nearby_fires;

import java.time.LocalDate;

import use_case.nearby_fires.NearbyFiresInputBoundary;
import use_case.nearby_fires.NearbyFiresInputData;

/**
 * Turns the Check button into a use case call. Owner: Member C.
 */
public class NearbyFiresController {

    private final NearbyFiresInputBoundary interactor;

    /**
     * Creates the controller.
     *
     * @param interactor the use case
     */
    public NearbyFiresController(NearbyFiresInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Checks for fires.
     *
     * @param communityId the chosen community
     * @param radiusKm    search radius
     * @param days        days to look back
     * @param asOf        last day, or null for today
     */
    public void execute(String communityId, double radiusKm, int days, LocalDate asOf) {
        interactor.execute(new NearbyFiresInputData(communityId, radiusKm, days, asOf));
    }
}
