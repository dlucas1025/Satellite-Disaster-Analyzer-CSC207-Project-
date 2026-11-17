package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Holds one view's state and tells the view when it changes (Observer pattern,
 * as in the ca-login starter).
 *
 * <p>Each story makes its own subclass or instance, e.g.
 * {@code new ViewModel<FloodRiskState>("flood risk")}. Presenters call {@link #setState}
 * then {@link #firePropertyChange()}; views register a listener and redraw.
 * Views must update Swing components on the event thread
 * ({@code SwingUtilities.invokeLater}) because presenters may run on a background thread.</p>
 *
 * @param <T> the state class for one view
 */
public class ViewModel<T> {

    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private T state;

    /**
     * Creates a view model.
     *
     * @param viewName the name of the view this model belongs to
     */
    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /** Tells listeners that the state changed. */
    public void firePropertyChange() {
        support.firePropertyChange("state", null, state);
    }

    /**
     * Tells listeners that one named part of the state changed, e.g. "error".
     *
     * @param propertyName the part that changed
     */
    public void firePropertyChange(String propertyName) {
        support.firePropertyChange(propertyName, null, state);
    }

    /**
     * Registers a listener, usually the view.
     *
     * @param listener the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
