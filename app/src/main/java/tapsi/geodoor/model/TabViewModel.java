package tapsi.geodoor.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import tapsi.geodoor.MainActivity;

public class TabViewModel extends AndroidViewModel {

    private String TAG = "tapsi.geodoor.controller.TabViewModel";

    private MainActivity mainActivity;
    private ViewPager viewPager;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public TabViewModel(@NonNull Application application) {
        super(application);
    }

    public void onNameChanged(String name) {
        Log.d(TAG, "onChanged: " + name);
    }

    public void onSettingsCreated() {
    }
}
