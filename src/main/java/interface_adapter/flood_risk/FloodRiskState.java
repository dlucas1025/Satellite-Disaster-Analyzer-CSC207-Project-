package interface_adapter.flood_risk;

import java.util.ArrayList;
import java.util.List;

/**
 * Everything the flood risk screen shows, already formatted. Owner: Lucas.
 */
public class FloodRiskState {

    private String heading = "";
    private String overallLevel = "";
    private List<String> dailyLevels = new ArrayList<>();
    private List<String> reasons = new ArrayList<>();
    private List<Double> forecastFlow = new ArrayList<>();
    private List<Double> usualFlow = new ArrayList<>();
    private String note = "";
    private String error = "";

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getOverallLevel() {
        return overallLevel;
    }

    public void setOverallLevel(String overallLevel) {
        this.overallLevel = overallLevel;
    }

    public List<String> getDailyLevels() {
        return dailyLevels;
    }

    public void setDailyLevels(List<String> dailyLevels) {
        this.dailyLevels = dailyLevels;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public List<Double> getForecastFlow() {
        return forecastFlow;
    }

    public void setForecastFlow(List<Double> forecastFlow) {
        this.forecastFlow = forecastFlow;
    }

    public List<Double> getUsualFlow() {
        return usualFlow;
    }

    public void setUsualFlow(List<Double> usualFlow) {
        this.usualFlow = usualFlow;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
