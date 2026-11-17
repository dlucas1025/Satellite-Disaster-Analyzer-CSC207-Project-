package use_case.compare_imagery;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for story 4. Owner: Member D.
 *
 * <p>Use a fake CompareImageryDataAccessInterface that returns tiny hand-made pixel arrays
 * (e.g. 4x4 with 2 water pixels), DemoCommunityProvider and a recording presenter. Test the
 * classifiers separately, and write one contract test that the GIBS source, the caching
 * decorator and the fake all pass (Liskov).</p>
 */
class CompareImageryInteractorTest {

    @Test
    @Disabled("TODO Member D")
    void reportsAffectedShareForBothDates() {
    }

    @Test
    @Disabled("TODO Member D")
    void cloudyPictureIsFlaggedUnreliable() {
    }

    @Test
    @Disabled("TODO Member D")
    void missingPictureShowsError() {
    }
}
