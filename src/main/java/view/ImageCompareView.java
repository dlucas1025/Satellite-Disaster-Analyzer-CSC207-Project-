package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import interface_adapter.ViewModel;
import interface_adapter.compare_imagery.CompareImageryController;
import interface_adapter.compare_imagery.CompareImageryState;

/**
 * Two satellite pictures side by side with the affected share of land. Owner: Member D.
 */
public class ImageCompareView extends JPanel implements PropertyChangeListener {

    private static final int DATE_COLUMNS = 10;

    private final ViewModel<CompareImageryState> viewModel;
    private final JComboBox<String> communityBox = new JComboBox<>();
    private final JComboBox<String> hazardBox = new JComboBox<>(new String[] {"FLOOD", "WILDFIRE"});
    private final JTextField beforeField = new JTextField("2022-04-20", DATE_COLUMNS);
    private final JTextField afterField = new JTextField("2022-05-10", DATE_COLUMNS);
    private final JButton compareButton = new JButton("Compare");
    private final JLabel beforeLabel = new JLabel("Before", SwingConstants.CENTER);
    private final JLabel afterLabel = new JLabel("After", SwingConstants.CENTER);
    private final JLabel summaryLabel = new JLabel(" ");
    private CompareImageryController controller;

    /**
     * Creates the view.
     *
     * @param viewModel the comparison view model
     */
    public ImageCompareView(ViewModel<CompareImageryState> viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;

        final JPanel top = new JPanel();
        top.add(new JLabel("Community:"));
        top.add(communityBox);
        top.add(hazardBox);
        top.add(new JLabel("Before:"));
        top.add(beforeField);
        top.add(new JLabel("After:"));
        top.add(afterField);
        top.add(compareButton);

        final JPanel pictures = new JPanel(new GridLayout(1, 2));
        pictures.add(beforeLabel);
        pictures.add(afterLabel);

        add(top, BorderLayout.NORTH);
        add(pictures, BorderLayout.CENTER);
        add(summaryLabel, BorderLayout.SOUTH);

        // TODO Member D: validate the dates and show an error instead of throwing; run in a SwingWorker
        compareButton.addActionListener(event -> {
            if (controller != null && communityBox.getSelectedItem() != null) {
                controller.execute((String) communityBox.getSelectedItem(), (String) hazardBox.getSelectedItem(),
                        LocalDate.parse(beforeField.getText().trim()), LocalDate.parse(afterField.getText().trim()));
            }
        });
        viewModel.addPropertyChangeListener(this);
    }

    public void setController(CompareImageryController controller) {
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
            final CompareImageryState state = viewModel.getState();
            // TODO Member D: turn the pixel arrays into BufferedImages and show them as ImageIcons,
            //  with the community marked; captions under each picture
            beforeLabel.setText(state.getBeforeCaption().isEmpty() ? "Before" : state.getBeforeCaption());
            afterLabel.setText(state.getAfterCaption().isEmpty() ? "After" : state.getAfterCaption());
            summaryLabel.setText(state.getError().isEmpty() ? state.getChangeSummary() : state.getError());
        });
    }
}
