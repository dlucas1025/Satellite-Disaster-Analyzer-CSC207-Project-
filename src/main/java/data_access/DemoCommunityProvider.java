package data_access;

import java.util.List;
import java.util.Optional;

import entity.Community;
import use_case.shared.CommunityProvider;

/**
 * A fixed list of demo communities, used until the real watchlist is merged
 * and in tests. The two places match the demo events: the May 2022 Fisher River
 * flood and the July 2024 Jasper wildfire.
 */
public class DemoCommunityProvider implements CommunityProvider {

    private static final List<Community> DEMO = List.of(
            new Community("peguis-fn-mb", "Peguis First Nation", "MB", 51.330, -97.570),
            new Community("jasper-ab", "Jasper", "AB", 52.874, -118.081));

    @Override
    public List<Community> getCommunities() {
        return DEMO;
    }

    @Override
    public Optional<Community> findById(String id) {
        return DEMO.stream().filter(c -> c.id().equals(id)).findFirst();
    }
}
