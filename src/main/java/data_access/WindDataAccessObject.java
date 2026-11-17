package data_access;

import java.time.LocalDate;

import entity.Community;
import use_case.nearby_fires.WindDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Wind direction from an online weather service. Owner: Member C.
 * Deliberately separate from Member B's weather class so the stories stay independent.
 */
public class WindDataAccessObject implements WindDataAccessInterface {

    @Override
    public double getWindFromDegrees(Community community, LocalDate day) {
        // TODO Member C: dominant wind direction for the day (forecast or historical API)
        throw new DataUnavailableException("Wind is not implemented yet");
    }
}
