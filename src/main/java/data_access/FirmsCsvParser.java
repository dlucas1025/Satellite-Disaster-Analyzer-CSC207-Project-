package data_access;

import java.util.List;

import entity.FireDetection;

/**
 * Turns a FIRMS CSV response into fire detections (Single Responsibility:
 * fetching and parsing are separate classes). Owner: Member C.
 */
public class FirmsCsvParser {

    /**
     * Parses a CSV response.
     *
     * @param csv the response body, header row first
     * @return one detection per data row
     */
    public List<FireDetection> parse(String csv) {
        // TODO Member C: find columns by header name (latitude, longitude, acq_date, acq_time,
        //  confidence, frp) rather than by position
        throw new UnsupportedOperationException("TODO Member C: FirmsCsvParser.parse");
    }
}
