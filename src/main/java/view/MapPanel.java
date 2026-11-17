package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * Draws a map picture with markers on top. Owner: Member C.
 *
 * <p>In the plain latitude/longitude projection, entity.BoundingBox.toPixel converts
 * a position to a pixel, so no map library is needed.</p>
 */
public class MapPanel extends JPanel {

    private static final int PREFERRED_WIDTH = 640;
    private static final int PREFERRED_HEIGHT = 480;

    /** Creates an empty map. */
    public MapPanel() {
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // TODO Member C: draw the background picture, the community marker,
        //  a red dot per fire and an arrow for the wind
        g.drawString("Map goes here", 20, 20);
    }
}
