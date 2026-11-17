package app;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * Starts NorthWatch.
 */
public final class Main {

    private Main() {
    }

    /**
     * Builds and shows the application window.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new AppBuilder()
                .addWatchlistView()
                .addWatchlistUseCase()
                .addFloodRiskView()
                .addFloodRiskUseCase()
                .addNearbyFiresView()
                .addNearbyFiresUseCase()
                .addCompareImageryView()
                .addCompareImageryUseCase()
                .build()
                .setVisible(true));
    }
}
