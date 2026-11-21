package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entity.Community;
import interface_adapter.ViewModel;
import interface_adapter.save_community.SaveCommunityController;
import interface_adapter.save_community.SaveCommunityState;

/**
 * Search box, search results and the saved watchlist. Owner: Shreyas.
 */
public class WatchlistView extends JPanel implements PropertyChangeListener {

    private final ViewModel<SaveCommunityState> viewModel;
    private final JTextField searchField = new JTextField(20);
    private final JButton searchButton = new JButton("Search");
    private final DefaultListModel<Community> resultsModel = new DefaultListModel<>();
    private final JList<Community> resultsList = new JList<>(resultsModel);
    private final JButton saveButton = new JButton("Save to watchlist");
    private final DefaultListModel<Community> watchlistModel = new DefaultListModel<>();
    private final JList<Community> watchlistList = new JList<>(watchlistModel);
    private final JButton removeButton = new JButton("Remove");
    private final JLabel messageLabel = new JLabel(" ");
    private SaveCommunityController controller;

    /**
     * Creates the view.
     *
     * @param viewModel the watchlist view model
     */
    public WatchlistView(ViewModel<SaveCommunityState> viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;

        final JPanel searchRow = new JPanel();
        searchRow.add(new JLabel("Community:"));
        searchRow.add(searchField);
        searchRow.add(searchButton);

        final JPanel results = new JPanel(new BorderLayout());
        results.setBorder(BorderFactory.createTitledBorder("Search results"));
        results.add(new JScrollPane(resultsList), BorderLayout.CENTER);
        results.add(saveButton, BorderLayout.SOUTH);

        final JPanel saved = new JPanel(new BorderLayout());
        saved.setBorder(BorderFactory.createTitledBorder("My watchlist"));
        saved.add(new JScrollPane(watchlistList), BorderLayout.CENTER);
        saved.add(removeButton, BorderLayout.SOUTH);

        final JPanel lists = new JPanel(new GridLayout(1, 2));
        lists.add(results);
        lists.add(saved);

        add(searchRow, BorderLayout.NORTH);
        add(lists, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        // TODO Shreyas: run controller calls in a SwingWorker so the window never freezes;
        //  add rename and a custom renderer showing "name, province"
        searchButton.addActionListener(event -> {
            if (controller != null) {
                controller.search(searchField.getText());
            }
        });
        saveButton.addActionListener(event -> {
            if (controller != null && resultsList.getSelectedValue() != null) {
                controller.save(resultsList.getSelectedValue());
            }
        });
        removeButton.addActionListener(event -> {
            if (controller != null && watchlistList.getSelectedValue() != null) {
                controller.remove(watchlistList.getSelectedValue().id());
            }
        });

        viewModel.addPropertyChangeListener(this);
    }

    public void setController(SaveCommunityController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            final SaveCommunityState state = viewModel.getState();
            resultsModel.clear();
            state.getSearchResults().forEach(resultsModel::addElement);
            watchlistModel.clear();
            state.getWatchlist().forEach(watchlistModel::addElement);
            final String text = state.getError().isEmpty() ? state.getMessage() : state.getError();
            messageLabel.setText(text.isEmpty() ? " " : text);
        });
    }
}
