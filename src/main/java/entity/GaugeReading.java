package entity;

import java.time.LocalDateTime;

/**
 * One measurement from a river gauge. Owner: Lucas.
 *
 * @param stationId    the gauge
 * @param time         when it was measured
 * @param waterLevelM  water level in metres
 * @param dischargeM3s flow in m3/s (may be NaN if the gauge does not report it)
 */
public record GaugeReading(String stationId, LocalDateTime time, double waterLevelM, double dischargeM3s) {
}
