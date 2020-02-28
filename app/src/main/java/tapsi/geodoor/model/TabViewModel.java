package tapsi.geodoor.model;

import android.app.Application;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import tapsi.geodoor.MainActivity;
import tapsi.geodoor.geodoor_app.R;
import tapsi.geodoor.logic.data.Config;
import tapsi.geodoor.logic.data.ConfigRepository;
import tapsi.geodoor.views.SettingsFragment;

public class TabViewModel extends AndroidViewModel {

    private String TAG = "tapsi.geodoor.controller.TabViewModel";

    private MainActivity mainActivity;
    private ViewPager viewPager;
    private ConfigRepository configRepository;

    private LiveData<Config> config;
    private Config currentConfig;

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
        configRepository = new ConfigRepository(application);
        //config = configRepository.getConfig();
        Log.d(TAG, "on Config Loaded: ");
    }

    public Config getCurrentConfig() {
        return configRepository.getThisConfig();
    }

    public void onNameChanged(String name) {
        Log.d(TAG, "onChanged: " + name);
        if (currentConfig.getName().equals(name))
            return;
        currentConfig.setName(name);
        configRepository.update(currentConfig);
    }

    public void onSettingsCreated() {
        config = configRepository.getConfig();

        this.config.observe(mainActivity, new Observer<Config>() {
            @Override
            public void onChanged(Config config) {
                if (config == null)
                    return;
                currentConfig = config;
                PagerAdapter pagerAdapter = (PagerAdapter) viewPager.getAdapter();
                pagerAdapter.getSettingsFragment().setEditText(config.getName());

                // TODO: set all settings Parameter
                Log.d(TAG, "set all settings Parameter!");
            }
        });
    }
}
