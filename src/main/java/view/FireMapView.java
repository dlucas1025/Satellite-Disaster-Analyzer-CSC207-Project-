package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import interface_adapter.ViewModel;
import interface_adapter.nearby_fires.NearbyFiresController;
import interface_adapter.nearby_fires.NearbyFiresState;

/**
 * Map of nearby fires with the wind direction and threat rating. Owner: Member C.
 */
public class FireMapView extends JPanel implements PropertyChangeListener {

    private static final int DEFAULT_RADIUS_KM = 100;
    private static final int MAX_RADIUS_KM = 500;
    private static final int RADIUS_STEP_KM = 25;
    private static final int DEFAULT_DAYS = 3;
    private static final int MAX_DAYS = 5;

    private final ViewModel<NearbyFiresState> viewModel;
    private final JComboBox<String> communityBox = new JComboBox<>();
    private final JSpinner radiusSpinner = new JSpinner(
            new SpinnerNumberModel(DEFAULT_RADIUS_KM, RADIUS_STEP_KM, MAX_RADIUS_KM, RADIUS_STEP_KM));
    private final JSpinner daysSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_DAYS, 1, MAX_DAYS, 1));
    private final JButton checkButton = new JButton("Check for fires");
    private final MapPanel mapPanel = new MapPanel();
    private final JLabel summaryLabel = new JLabel(" ");
    private final DefaultListModel<String> reasonsModel = new DefaultListModel<>();
    private NearbyFiresController controller;

    /**
     * Creates the view.
     *
     * @param viewModel the wildfire view model
     */
    public FireMapView(ViewModel<NearbyFiresState> viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;

        final JPanel top = new JPanel();
        top.add(new JLabel("Community:"));
        top.add(communityBox);
        top.add(new JLabel("Radius km:"));
        top.add(radiusSpinner);
        top.add(new JLabel("Days:"));
        top.add(daysSpinner);
        top.add(checkButton);

        final JPanel side = new JPanel(new BorderLayout());
        side.add(summaryLabel, BorderLayout.NORTH);
        side.add(new JScrollPane(new JList<>(reasonsModel)), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(mapPanel, BorderLayout.CENTER);
        add(side, BorderLayout.EAST);

        // TODO Member C: run the controller in a SwingWorker
        checkButton.addActionListener(event -> {
            if (controller != null && communityBox.getSelectedItem() != null) {
                controller.execute((String) communityBox.getSelectedItem(),
                        ((Number) radiusSpinner.getValue()).doubleValue(),
                        (Integer) daysSpinner.getValue(), null);
            }
        });
        viewModel.addPropertyChangeListener(this);
    }

    public void setController(NearbyFiresController controller) {
        this.controller = controller;
    }

    /**
     * Fills the community drop-down.
     *
     * @param communityIds ids from the community provider
     */
    public void setCommunityChoices(List<String> communityIds) {
        communityBox.removeAllItems();
        communityIds.forEach(communityBox::addItem);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            final NearbyFiresState state = viewModel.getState();
            summaryLabel.setText(state.getError().isEmpty()
                    ? state.getSummary() + "  " + state.getLevel() : state.getError());
            reasonsModel.clear();
            state.getReasons().forEach(reasonsModel::addElement);
            // TODO Member C: give mapPanel the background picture, area, fire points and wind
            mapPanel.repaint();
        });
    }
}
