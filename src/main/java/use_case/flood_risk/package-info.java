/**
 * Story 2: Flood risk for the coming week. Owner: Member B.
 *
 * <p>Two narrow data access interfaces: {@link RiverDataAccessInterface} (flow and gauges) and
 * {@link FloodWeatherDataAccessInterface}. The rules live in {@code entity.risk.FloodRiskModel}
 * and are injected, so they can be tested and replaced on their own. Search for
 * "TODO Member B"; ARCHITECTURE.md has the full checklist.</p>
 */
package use_case.flood_risk;
