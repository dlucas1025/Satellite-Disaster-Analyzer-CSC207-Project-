package entity.risk;

import java.util.List;

import entity.FireDetection;

/**
 * Everything the wildfire rules look at. Owner: Hamza.
 *
 * @param fires        detections inside the search radius
 * @param windFromDeg  direction the wind blows FROM, degrees clockwise from north
 * @param radiusKm     the search radius used
 */
public record FireConditions(List<FireDetection> fires, double windFromDeg, double radiusKm) {
}
