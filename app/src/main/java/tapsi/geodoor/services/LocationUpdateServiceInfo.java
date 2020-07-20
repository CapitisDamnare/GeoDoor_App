package tapsi.geodoor.services;

import android.location.Location;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import tapsi.geodoor.logic.AutoGateLogic;

@AutoValue public abstract class LocationUpdateServiceInfo implements Parcelable {

    public abstract float distance();
    public abstract Location currentLocation();
    public abstract long currentUpdateInterval();
    public abstract Long countDown();
    public abstract AutoGateLogic.TravelState currentState();

    public static Builder builder() {
        return new $AutoValue_LocationUpdateServiceInfo.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder setDistance(float distance);
        abstract Builder setCurrentLocation(Location currentLocation);
        abstract Builder setCurrentUpdateInterval(long currentUpdateInterval);
        abstract Builder setCountDown(Long countDown);
        abstract Builder setCurrentState(AutoGateLogic.TravelState currentState);
        abstract LocationUpdateServiceInfo build();
    }
}