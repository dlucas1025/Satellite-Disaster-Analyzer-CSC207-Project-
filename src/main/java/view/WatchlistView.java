package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import entity.Community;
import interface_adapter.ViewModel;
import interface_adapter.save_community.SaveCommunityController;
import interface_adapter.save_community.SaveCommunityState;

/**
 * Search box, search results and the saved watchlist. Owner: Shreyas.
 *
 * <p>Every controller call runs in a {@link SwingWorker}, so searching or saving never
 * freezes the window; buttons are disabled until the call finishes. The view only shows
 * the labels the presenter wrote.</p>
 */
public class WatchlistView extends JPanel implements PropertyChangeListener {

    private static final int SEARCH_COLUMNS = 24;
    private static final int DOUBLE_CLICK = 2;

    private final ViewModel<SaveCommunityState> viewModel;
    private final JTextField searchField = new JTextField(SEARCH_COLUMNS);
    private final JButton searchButton = new JButton("Search");
    private final DefaultListModel<String> resultsModel = new DefaultListModel<>();
    private final JList<String> resultsList = new JList<>(resultsModel);
    private final JButton saveButton = new JButton("Save to watchlist");
    private final DefaultListModel<String> watchlistModel = new DefaultListModel<>();
    private final JList<String> watchlistList = new JList<>(watchlistModel);
    private final JButton renameButton = new JButton("Rename");
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
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        final JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchRow.add(new JLabel("Community name:"));
        searchRow.add(searchField);
        searchRow.add(searchButton);

        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        watchlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final JPanel results = new JPanel(new BorderLayout());
        results.setBorder(BorderFactory.createTitledBorder("Search results (pick the right one)"));
        results.add(new JScrollPane(resultsList), BorderLayout.CENTER);
        results.add(saveButton, BorderLayout.SOUTH);

        final JPanel savedButtons = new JPanel(new GridLayout(1, 2));
        savedButtons.add(renameButton);
        savedButtons.add(removeButton);
        final JPanel saved = new JPanel(new BorderLayout());
        saved.setBorder(BorderFactory.createTitledBorder("My watchlist"));
        saved.add(new JScrollPane(watchlistList), BorderLayout.CENTER);
        saved.add(savedButtons, BorderLayout.SOUTH);

        final JPanel lists = new JPanel(new GridLayout(1, 2, 8, 0));
        lists.add(results);
        lists.add(saved);

        add(searchRow, BorderLayout.NORTH);
        add(lists, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        searchButton.addActionListener(event -> search());
        searchField.addActionListener(event -> search());
        saveButton.addActionListener(event -> saveSelected());
        resultsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == DOUBLE_CLICK) {
                    saveSelected();
                }
            }
        });
        renameButton.addActionListener(event -> renameSelected());
        removeButton.addActionListener(event -> removeSelected());

        viewModel.addPropertyChangeListener(this);
    }

    public void setController(SaveCommunityController controller) {
        this.controller = controller;
    }

    private void search() {
        final String query = searchField.getText();
        runInBackground(() -> controller.search(query));
    }

    private void saveSelected() {
        final Community chosen = selected(resultsList, viewModel.getState().getSearchResults());
        if (chosen == null) {
            showMessage("Pick a place from the search results first.");
            return;
        }
        runInBackground(() -> controller.save(chosen));
    }

    private void renameSelected() {
        final Community chosen = selected(watchlistList, viewModel.getState().getWatchlist());
        if (chosen == null) {
            showMessage("Pick a community on your watchlist first.");
            return;
        }
        final String newName = (String) JOptionPane.showInputDialog(this, "New name for " + chosen.name() + ":",
                "Rename", JOptionPane.PLAIN_MESSAGE, null, null, chosen.name());
        if (newName != null) {
            runInBackground(() -> controller.rename(chosen.id(), newName));
        }
    }

    private void removeSelected() {
        final Community chosen = selected(watchlistList, viewModel.getState().getWatchlist());
        if (chosen == null) {
            showMessage("Pick a community on your watchlist first.");
            return;
        }
        final int answer = JOptionPane.showConfirmDialog(this, "Remove " + chosen.name() + " from your watchlist?",
                "Remove", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            runInBackground(() -> controller.remove(chosen.id()));
        }
    }

    private static Community selected(JList<String> list, List<Community> communities) {
        final int index = list.getSelectedIndex();
        if (index < 0 || index >= communities.size()) {
            return null;
        }
        return communities.get(index);
    }

    /**
     * Runs a controller call off the Swing event thread and disables the buttons meanwhile.
     *
     * @param action the call to make
     */
    private void runInBackground(Runnable action) {
        if (controller == null) {
            return;
        }
        setBusy(true);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                action.run();
                return null;
            }

            @Override
            protected void done() {
                setBusy(false);
            }
        }.execute();
    }

    private void setBusy(boolean busy) {
        searchButton.setEnabled(!busy);
        saveButton.setEnabled(!busy);
        renameButton.setEnabled(!busy);
        removeButton.setEnabled(!busy);
        if (busy) {
            messageLabel.setText("Working...");
        }
    }

    private void showMessage(String text) {
        messageLabel.setText(text);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            final SaveCommunityState state = viewModel.getState();
            resultsModel.clear();
            state.getSearchLabels().forEach(resultsModel::addElement);
            watchlistModel.clear();
            state.getWatchlistLabels().forEach(watchlistModel::addElement);
            final String text = state.getError().isEmpty() ? state.getMessage() : state.getError();
            messageLabel.setText(text.isEmpty() ? " " : text);
        });
    }
}
