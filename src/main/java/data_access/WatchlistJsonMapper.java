package data_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Community;
import entity.Watchlist;

/**
 * Converts a watchlist to and from JSON. Owner: Shreyas.
 *
 * <p>Kept separate from the stores (Single Responsibility) so the file store and the
 * cloud store write exactly the same format. One community looks like:</p>
 * <pre>
 * {"id": "peguis-fn-mb", "name": "Peguis First Nation", "province": "MB",
 *  "latitude": 51.33, "longitude": -97.57}
 * </pre>
 */
public final class WatchlistJsonMapper {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PROVINCE = "province";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private WatchlistJsonMapper() {
    }

    /**
     * Converts a watchlist's communities to a JSON array, in order.
     *
     * @param watchlist the watchlist
     * @return one JSON object per community
     */
    public static JSONArray toJson(Watchlist watchlist) {
        final JSONArray array = new JSONArray();
        for (Community community : watchlist.getCommunities()) {
            array.put(new JSONObject()
                    .put(ID, community.id())
                    .put(NAME, community.name())
                    .put(PROVINCE, community.province())
                    .put(LATITUDE, community.latitude())
                    .put(LONGITUDE, community.longitude()));
        }
        return array;
    }

    /**
     * Builds a watchlist from a JSON array written by {@link #toJson}.
     *
     * @param owner the user the watchlist belongs to
     * @param array the saved communities
     * @return the watchlist
     * @throws JSONException            if a field is missing or has the wrong type
     * @throws IllegalArgumentException if a saved community is invalid
     */
    public static Watchlist fromJson(String owner, JSONArray array) {
        final Watchlist watchlist = new Watchlist(owner);
        for (int i = 0; i < array.length(); i++) {
            final JSONObject item = array.getJSONObject(i);
            watchlist.add(new Community(
                    item.getString(ID),
                    item.getString(NAME),
                    item.optString(PROVINCE, ""),
                    item.getDouble(LATITUDE),
                    item.getDouble(LONGITUDE)));
        }
        return watchlist;
    }
}
