package interface_adapter.nearby_fires;

import java.util.ArrayList;
import java.util.List;

/**
 * Everything the wildfire screen shows, already formatted. Owner: Member C.
 */
public class NearbyFiresState {

    private String summary = "";
    private String level = "";
    private List<String> reasons = new ArrayList<>();
    private List<double[]> firePoints = new ArrayList<>();
    private double windFromDeg = Double.NaN;
    private String error = "";

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    /**
     * Fire positions to draw.
     *
     * @return {latitude, longitude} pairs
     */
    public List<double[]> getFirePoints() {
        return firePoints;
    }

    public void setFirePoints(List<double[]> firePoints) {
        this.firePoints = firePoints;
    }

    public double getWindFromDeg() {
        return windFromDeg;
    }

    public void setWindFromDeg(double windFromDeg) {
        this.windFromDeg = windFromDeg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
