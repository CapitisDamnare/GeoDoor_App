package tapsi.geodoor.model;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;

import tapsi.geodoor.logic.AutoGateLogic;

public class TabViewModel extends ViewModel {

    private String TAG = "tapsi.tabViewModel";

    private MutableLiveData<Boolean> isAutoMode = new MutableLiveData<>();

    private MutableLiveData<Float> distance = new MutableLiveData<>();
    private MutableLiveData<Location> lastLocation = new MutableLiveData<>();
    private MutableLiveData<Long> updateInterval = new MutableLiveData<>();
    private MutableLiveData<Long> countDown = new MutableLiveData<>();
    private MutableLiveData<AutoGateLogic.TravelState> currentState = new MutableLiveData<>();

    public LiveData<Boolean> getAutoMode(){
        return isAutoMode;
    }

    public void setAutoMode(boolean isEnabled) {
        isAutoMode.setValue(isEnabled);
    }

    public LiveData<Location> getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location location) {
        lastLocation.setValue(location);
    }

    public MutableLiveData<Float> getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance.setValue(distance);
    }

    public MutableLiveData<Long> getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(Long updateInterval) {
        this.updateInterval.setValue(updateInterval);
    }

    public MutableLiveData<Long> getLastGateOpenEvent() {
        return countDown;
    }

    public void setLastGateOpenEvent(Long lastGateOpenEvent) {
        this.countDown.setValue(lastGateOpenEvent);
    }

    public MutableLiveData<AutoGateLogic.TravelState> getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AutoGateLogic.TravelState currentState) {
        this.currentState.setValue(currentState);
    }
}
