package tapsi.geodoor.services;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";
    public static final String PREFERENCE_FILE = "tapsi_geodoor";

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    public static boolean requestingLocationUpdates(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, 0);
        return preferences.getBoolean(KEY_REQUESTING_LOCATION_UPDATES, true);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     * @param requestingLocationUpdates The location updates state.
     */
    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, 0);
        preferences.edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }
}

