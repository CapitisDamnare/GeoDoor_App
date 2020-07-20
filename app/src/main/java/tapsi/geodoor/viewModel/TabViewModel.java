package tapsi.geodoor.viewModel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tapsi.geodoor.database.GeoDoorDbRepository;
import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.logic.AutoGateLogic;

public class TabViewModel extends AndroidViewModel {

    private String TAG = "tapsi.tabViewModel";
    private GeoDoorDbRepository repository;

    private LiveData<Config> config;

    private MutableLiveData<Boolean> isAutoMode = new MutableLiveData<>();

    private MutableLiveData<Float> distance = new MutableLiveData<>();
    private MutableLiveData<Location> lastLocation = new MutableLiveData<>();
    private MutableLiveData<Long> updateInterval = new MutableLiveData<>();
    private MutableLiveData<Long> countDown = new MutableLiveData<>();
    private MutableLiveData<AutoGateLogic.TravelState> currentState = new MutableLiveData<>();

    public TabViewModel(@NonNull Application application) {
        super(application);
        repository = new GeoDoorDbRepository(application);
        config = repository.getConfig();
    }

    public LiveData<Config> getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        repository.update(config);
    }

    public Config getSynConfig() {
        return repository.getSynConfig();
    }

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
