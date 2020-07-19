package tapsi.geodoor.logic;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AutoGateLogic {

    private static final String TAG = "tapsi.autoGateLogic";

    private Context mContext;

    private LocalDateTime lastOpenGateEvent = LocalDateTime.MIN;
    private long currentUpdateInterval;
    private float lastCalculatedDistance;
    private TravelState currentState;

    private Location homeLocation;
    private int homeRadius = 30;
    private final float neededAccuracy = 20;

    public long getCountDown() {
        if (lastOpenGateEvent.equals(LocalDateTime.MIN))
            return 0L;

        long countDown = LocalDateTime.now().until(lastOpenGateEvent, ChronoUnit.SECONDS);
        if (countDown <= 0) {
            lastOpenGateEvent = LocalDateTime.MIN;
            return 0L;
        }

        return countDown;
    }

    public long getCurrentUpdateInterval() {
        return currentUpdateInterval;
    }

    public float getLastCalculatedDistance() {
        return lastCalculatedDistance;
    }

    public TravelState getCurrentState() {
        return currentState;
    }

    public enum TravelState {
        HOME,
        OUTSIDE
    }

    public AutoGateLogic(Context context) {
        mContext = context;
        currentState = TravelState.HOME;
        homeLocation = new Location("");
        homeLocation.setLatitude(47.047671);
        homeLocation.setLongitude(15.074499);
    }

    public void updateLocation(Location location) {
        if (location != null && location.getAccuracy() <= neededAccuracy) {
            lastCalculatedDistance = location.distanceTo(homeLocation);
            long countDown = getCountDown();
            if (lastCalculatedDistance < homeRadius
                    && currentState.equals(TravelState.OUTSIDE)
                    && countDown <= 0L) {
                Log.i(TAG, "Open Gate!");
                lastOpenGateEvent = LocalDateTime.now().plusSeconds(30);
                currentState = TravelState.HOME;
            } else if (lastCalculatedDistance > homeRadius && countDown <= 0L) {
                Log.i(TAG, "Moving Outside!");
                currentState = TravelState.OUTSIDE;
            }
        }
    }

    /**
     * Gets the progressive timeout based on the current location.
     * If the {{@link TravelState}} is HOME a standard interval is chosen.
     * @param location
     * @return
     */
    public long getProgressiveTimeOutInMillis(Location location)
    {
        if (currentState.equals(TravelState.HOME)){
            currentUpdateInterval = 10000;
            return currentUpdateInterval;
        }

        float distance = location.distanceTo(homeLocation);
        if (distance < 50)
            currentUpdateInterval =  3000;
        else if (distance < 100)
            currentUpdateInterval =  5000;
        else if (distance < 200)
            currentUpdateInterval =  10000;
        else if (distance < 500)
            currentUpdateInterval =  20000;
        else if (distance < 1000)
            currentUpdateInterval =  25000;
        else
            currentUpdateInterval = 30000;

        return currentUpdateInterval;
    }
}
