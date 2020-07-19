package tapsi.geodoor.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class TabViewModel extends ViewModel {

    private String TAG = "tapsi.tabViewModel";

    private MutableLiveData<Boolean> isAutoMode = new MutableLiveData<>();
    private LiveData<Boolean> liveIsAutoMode = Transformations.map(isAutoMode, input -> input);

    public void setAutoMode(boolean isEnabled) {
        isAutoMode.setValue(isEnabled);
    }

    public LiveData<Boolean> getAutoMode(){
        return isAutoMode;
    }
}
