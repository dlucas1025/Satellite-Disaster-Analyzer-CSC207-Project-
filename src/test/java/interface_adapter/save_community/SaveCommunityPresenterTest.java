package interface_adapter.save_community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Community;
import interface_adapter.ViewModel;
import use_case.save_community.SaveCommunityOutputData;

/**
 * Tests that the presenter writes the right text into the view model. Owner: Shreyas.
 */
class SaveCommunityPresenterTest {

    private final Community peguis = new Community("geo-1", "Peguis", "MB", 50.08337, -96.95028);
    private final ViewModel<SaveCommunityState> viewModel = new ViewModel<>("watchlist");
    private final SaveCommunityPresenter presenter = new SaveCommunityPresenter(viewModel);
    private int notifications;

    SaveCommunityPresenterTest() {
        viewModel.setState(new SaveCommunityState());
        viewModel.addPropertyChangeListener(event -> notifications++);
    }

    @Test
    void labelShowsNameProvinceAndLocation() {
        assertEquals("Peguis, MB  (50.08°N, 96.95°W)", SaveCommunityPresenter.label(peguis));
    }

    @Test
    void labelSkipsMissingProvince() {
        final Community farm = new Community("farm", "My farm", "", 50.0, -97.0);
        assertEquals("My farm  (50.00°N, 97.00°W)", SaveCommunityPresenter.label(farm));
    }

    @Test
    void searchResultsBecomeLabelsAndNotifyTheView() {
        presenter.prepareSearchResults(new SaveCommunityOutputData(List.of(peguis), List.of(), null));
        assertEquals(List.of("Peguis, MB  (50.08°N, 96.95°W)"), viewModel.getState().getSearchLabels());
        assertEquals(List.of(peguis), viewModel.getState().getSearchResults());
        assertEquals("", viewModel.getState().getMessage());
        assertEquals(1, notifications);
    }

    @Test
    void watchlistViewShowsMessageAndClearsOldError() {
        viewModel.getState().setError("old error");
        presenter.prepareWatchlistView(new SaveCommunityOutputData(List.of(), List.of(peguis), "Saved Peguis."));
        assertEquals(1, viewModel.getState().getWatchlistLabels().size());
        assertEquals("Saved Peguis.", viewModel.getState().getMessage());
        assertEquals("", viewModel.getState().getError());
    }

    @Test
    void failViewSetsError() {
        presenter.prepareFailView("Search is unavailable right now");
        assertTrue(viewModel.getState().getError().startsWith("Search is unavailable"));
        assertEquals(1, notifications);
    }
}
