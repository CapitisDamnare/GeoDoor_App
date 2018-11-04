package tapsi.geodoor.model;

import android.app.Application;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.annotation.NonNull;
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

public class TabViewModel extends AndroidViewModel implements SettingsFragment.OnSettingsFragmentListener {

    private String TAG = "tapsi.geodoor.controller.TabViewModel";

    private MainActivity mainActivity;
    private ViewPager viewPager;
    private ConfigRepository configRepository;

    private LiveData<Config> config;
    private Config currentConfig;

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabViewModel(@NonNull Application application) {
        super(application);
        configRepository = new ConfigRepository(application);
        //configRepository.deleteAll();

        mainActivity = MainActivity.getStaticActivity();
        setupTabLayout();
    }

    private void setupTabLayout() {
        viewPager = mainActivity.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(mainActivity.getSupportFragmentManager(), this));
        TabLayout tabLayout = mainActivity.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onNameChanged(String name) {
        if (currentConfig.getName().equals(name))
            return;
        currentConfig.setName(name);
        configRepository.update(currentConfig);
    }

    @Override
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
            }
        });
    }
}
