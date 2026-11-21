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
import javax.swing.SwingUtilities;

import interface_adapter.ViewModel;
import interface_adapter.flood_risk.FloodRiskController;
import interface_adapter.flood_risk.FloodRiskState;

/**
 * Flood rating, reasons and flow chart. Owner: Lucas.
 */
public class FloodRiskView extends JPanel implements PropertyChangeListener {

    private final ViewModel<FloodRiskState> viewModel;
    private final JComboBox<String> communityBox = new JComboBox<>();
    private final JButton assessButton = new JButton("Assess flood risk");
    private final JLabel levelLabel = new JLabel(" ");
    private final DefaultListModel<String> reasonsModel = new DefaultListModel<>();
    private final JLabel messageLabel = new JLabel(" ");
    private FloodRiskController controller;

    /**
     * Creates the view.
     *
     * @param viewModel the flood risk view model
     */
    public FloodRiskView(ViewModel<FloodRiskState> viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;

        final JPanel top = new JPanel();
        top.add(new JLabel("Community:"));
        top.add(communityBox);
        // TODO Lucas: add an "as of" date picker (e.g. a JSpinner with a date model)
        top.add(assessButton);

        final JPanel centre = new JPanel(new BorderLayout());
        centre.add(levelLabel, BorderLayout.NORTH);
        centre.add(new JScrollPane(new JList<>(reasonsModel)), BorderLayout.CENTER);
        // TODO Lucas: JFreeChart ChartPanel of forecast flow vs usual flow, BorderLayout.SOUTH

        add(top, BorderLayout.NORTH);
        add(centre, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        // TODO Lucas: run the controller in a SwingWorker so API calls never freeze the window
        assessButton.addActionListener(event -> {
            if (controller != null && communityBox.getSelectedItem() != null) {
                controller.execute((String) communityBox.getSelectedItem(), null);
            }
        });
        viewModel.addPropertyChangeListener(this);
    }

    public void setController(FloodRiskController controller) {
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
            final FloodRiskState state = viewModel.getState();
            levelLabel.setText(state.getHeading() + "  " + state.getOverallLevel());
            reasonsModel.clear();
            state.getReasons().forEach(reasonsModel::addElement);
            final String text = state.getError().isEmpty() ? state.getNote() : state.getError();
            messageLabel.setText(text.isEmpty() ? " " : text);
        });
    }
}
