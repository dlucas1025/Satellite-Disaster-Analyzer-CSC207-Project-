package interface_adapter.compare_imagery;

import java.time.LocalDate;

import entity.Hazard;
import use_case.compare_imagery.CompareImageryInputBoundary;
import use_case.compare_imagery.CompareImageryInputData;

/**
 * Turns the Compare button into a use case call. Owner: Member D.
 */
public class CompareImageryController {

    private final CompareImageryInputBoundary interactor;

    /**
     * Creates the controller.
     *
     * @param interactor the use case
     */
    public CompareImageryController(CompareImageryInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Compares two pictures.
     *
     * @param communityId the chosen community
     * @param hazardName  "FLOOD" or "WILDFIRE" from the drop-down
     * @param before      earlier date
     * @param after       later date
     */
    public void execute(String communityId, String hazardName, LocalDate before, LocalDate after) {
        interactor.execute(new CompareImageryInputData(communityId, Hazard.valueOf(hazardName), before, after));
    }
}
