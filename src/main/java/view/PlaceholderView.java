package view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A temporary tab shown until a member's real view is ready.
 */
public class PlaceholderView extends JPanel {

    /**
     * Creates the placeholder.
     *
     * @param message the text to show, e.g. who owns the tab
     */
    public PlaceholderView(String message) {
        super(new BorderLayout());
        add(new JLabel(message, SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
