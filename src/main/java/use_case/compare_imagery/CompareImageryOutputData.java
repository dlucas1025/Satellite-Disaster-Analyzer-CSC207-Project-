package use_case.compare_imagery;

import entity.SatelliteImage;

/**
 * Two pictures and how much land was affected in each. Owner: Youssef.
 *
 * @param communityName  for the heading
 * @param before         the earlier picture
 * @param after          the later picture
 * @param beforeFraction affected share of land, 0 to 1
 * @param afterFraction  affected share of land, 0 to 1
 * @param beforeReliable false if the picture is mostly cloud
 * @param afterReliable  false if the picture is mostly cloud
 */
public record CompareImageryOutputData(String communityName, SatelliteImage before, SatelliteImage after,
                                       double beforeFraction, double afterFraction,
                                       boolean beforeReliable, boolean afterReliable) {
}
