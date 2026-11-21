package app;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import data_access.ApiConfig;
import data_access.CachingImageryDataAccessObject;
import data_access.CloudWatchlistDataAccessObject;
import data_access.DemoCommunityProvider;
import data_access.FallbackWatchlistDataAccessObject;
import data_access.FileWatchlistDataAccessObject;
import data_access.FirmsCsvParser;
import data_access.FirmsDataAccessObject;
import data_access.FloodWeatherDataAccessObject;
import data_access.GeocodingDataAccessObject;
import data_access.GibsImageryDataAccessObject;
import data_access.HydrologyDataAccessObject;
import data_access.WindDataAccessObject;
import entity.Community;
import entity.Hazard;
import entity.risk.BurnScarClassifier;
import entity.risk.FloodRiskModel;
import entity.risk.WaterClassifier;
import entity.risk.WildfireRiskModel;
import interface_adapter.ViewModel;
import interface_adapter.compare_imagery.CompareImageryController;
import interface_adapter.compare_imagery.CompareImageryPresenter;
import interface_adapter.compare_imagery.CompareImageryState;
import interface_adapter.flood_risk.FloodRiskController;
import interface_adapter.flood_risk.FloodRiskPresenter;
import interface_adapter.flood_risk.FloodRiskState;
import interface_adapter.nearby_fires.NearbyFiresController;
import interface_adapter.nearby_fires.NearbyFiresPresenter;
import interface_adapter.nearby_fires.NearbyFiresState;
import interface_adapter.save_community.SaveCommunityController;
import interface_adapter.save_community.SaveCommunityPresenter;
import interface_adapter.save_community.SaveCommunityState;
import use_case.compare_imagery.CompareImageryInteractor;
import use_case.flood_risk.FloodRiskInteractor;
import use_case.nearby_fires.NearbyFiresInteractor;
import use_case.save_community.SaveCommunityInteractor;
import use_case.save_community.WatchlistDataAccessInterface;
import use_case.shared.CommunityProvider;
import view.FireMapView;
import view.FloodRiskView;
import view.ImageCompareView;
import view.WatchlistView;

/**
 * Wires every layer together (Builder pattern; the composition root).
 *
 * <p>This is the only class allowed to name concrete classes from every layer.
 * Each story has one {@code addXxxView()} and one {@code addXxxUseCase()} method.
 * <b>Only edit your own two methods</b>, which keeps merge conflicts small.
 * Call each story's view method before its use case method.</p>
 */
public class AppBuilder {

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 750;
    private static final Path DATA_FOLDER = Path.of(System.getProperty("user.home"), ".northwatch");
    /** The signed-in user. TODO Shreyas: replace with real sign-in. */
    private static final String USER = "demo-user";

    private final JTabbedPane tabs = new JTabbedPane();

    /**
     * Where stories 2-4 get communities from. Once Shreyas's watchlist works, switch this to
     * {@code new WatchlistCommunityProvider(watchlistStore, USER)} (Dependency Inversion: one line).
     */
    private final CommunityProvider communityProvider = new DemoCommunityProvider();

    // ------------------------------------------------------------------
    // Story 1: Watchlist (Shreyas)
    // ------------------------------------------------------------------

    private final ViewModel<SaveCommunityState> watchlistViewModel = new ViewModel<>("watchlist");
    private WatchlistView watchlistView;

    /**
     * Adds the watchlist tab.
     *
     * @return this builder
     */
    public AppBuilder addWatchlistView() {
        watchlistViewModel.setState(new SaveCommunityState());
        watchlistView = new WatchlistView(watchlistViewModel);
        tabs.addTab("Watchlist", watchlistView);
        return this;
    }

    /**
     * Wires the save-community use case.
     *
     * @return this builder
     */
    public AppBuilder addWatchlistUseCase() {
        final WatchlistDataAccessInterface watchlistStore = new FallbackWatchlistDataAccessObject(
                new CloudWatchlistDataAccessObject(ApiConfig.get("userdata.base.url").orElse("")),
                new FileWatchlistDataAccessObject(DATA_FOLDER.resolve("watchlist.json")));
        final SaveCommunityInteractor interactor = new SaveCommunityInteractor(
                new GeocodingDataAccessObject(), watchlistStore, new SaveCommunityPresenter(watchlistViewModel));
        watchlistView.setController(new SaveCommunityController(interactor, USER));
        return this;
    }

    // ------------------------------------------------------------------
    // Story 2: Flood risk (Lucas)
    // ------------------------------------------------------------------

    private final ViewModel<FloodRiskState> floodRiskViewModel = new ViewModel<>("flood risk");
    private FloodRiskView floodRiskView;

    /**
     * Adds the flood risk tab.
     *
     * @return this builder
     */
    public AppBuilder addFloodRiskView() {
        floodRiskViewModel.setState(new FloodRiskState());
        floodRiskView = new FloodRiskView(floodRiskViewModel);
        floodRiskView.setCommunityChoices(communityIds());
        tabs.addTab("Flood risk", floodRiskView);
        return this;
    }

    /**
     * Wires the flood risk use case.
     *
     * @return this builder
     */
    public AppBuilder addFloodRiskUseCase() {
        final FloodRiskInteractor interactor = new FloodRiskInteractor(communityProvider,
                new HydrologyDataAccessObject(), new FloodWeatherDataAccessObject(),
                new FloodRiskModel(), new FloodRiskPresenter(floodRiskViewModel));
        floodRiskView.setController(new FloodRiskController(interactor));
        return this;
    }

    // ------------------------------------------------------------------
    // Story 3: Nearby wildfires (Hamza)
    // ------------------------------------------------------------------

    private final ViewModel<NearbyFiresState> nearbyFiresViewModel = new ViewModel<>("nearby fires");
    private FireMapView fireMapView;

    /**
     * Adds the wildfire tab.
     *
     * @return this builder
     */
    public AppBuilder addNearbyFiresView() {
        nearbyFiresViewModel.setState(new NearbyFiresState());
        fireMapView = new FireMapView(nearbyFiresViewModel);
        fireMapView.setCommunityChoices(communityIds());
        tabs.addTab("Wildfires", fireMapView);
        return this;
    }

    /**
     * Wires the nearby fires use case.
     *
     * @return this builder
     */
    public AppBuilder addNearbyFiresUseCase() {
        final NearbyFiresInteractor interactor = new NearbyFiresInteractor(communityProvider,
                new FirmsDataAccessObject(ApiConfig.get("firms.map.key").orElse(""), new FirmsCsvParser()),
                new WindDataAccessObject(), new WildfireRiskModel(), new NearbyFiresPresenter(nearbyFiresViewModel));
        fireMapView.setController(new NearbyFiresController(interactor));
        return this;
    }

    // ------------------------------------------------------------------
    // Story 4: Compare satellite pictures (Youssef)
    // ------------------------------------------------------------------

    private final ViewModel<CompareImageryState> compareImageryViewModel = new ViewModel<>("compare imagery");
    private ImageCompareView imageCompareView;

    /**
     * Adds the satellite comparison tab.
     *
     * @return this builder
     */
    public AppBuilder addCompareImageryView() {
        compareImageryViewModel.setState(new CompareImageryState());
        imageCompareView = new ImageCompareView(compareImageryViewModel);
        imageCompareView.setCommunityChoices(communityIds());
        tabs.addTab("Satellite", imageCompareView);
        return this;
    }

    /**
     * Wires the compare imagery use case.
     *
     * @return this builder
     */
    public AppBuilder addCompareImageryUseCase() {
        final CachingImageryDataAccessObject imagery = new CachingImageryDataAccessObject(
                new GibsImageryDataAccessObject(), DATA_FOLDER.resolve("cache").resolve("images"));
        final CompareImageryInteractor interactor = new CompareImageryInteractor(communityProvider, imagery,
                Map.of(Hazard.FLOOD, new WaterClassifier(), Hazard.WILDFIRE, new BurnScarClassifier()),
                new CompareImageryPresenter(compareImageryViewModel));
        imageCompareView.setController(new CompareImageryController(interactor));
        return this;
    }

    // ------------------------------------------------------------------

    /**
     * Creates the main window.
     *
     * @return the window, not yet visible
     */
    public JFrame build() {
        final JFrame frame = new JFrame("NorthWatch");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(tabs);
        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }

    /**
     * Community ids for the drop-downs in stories 2-4.
     * TODO integration: refresh the drop-downs when the watchlist changes.
     *
     * @return the ids, in display order
     */
    private List<String> communityIds() {
        return communityProvider.getCommunities().stream().map(Community::id).toList();
    }
}
