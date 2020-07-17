package tapsi.geodoor.model;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import tapsi.geodoor.views.ControlFragment;
import tapsi.geodoor.views.MainFragment;
import tapsi.geodoor.views.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private String TAG = "tapsi.geodoor.controller.PagerAdapter";
    private final Context mContext;

    MainFragment mainFragment;
    ControlFragment controlFragment;
    SettingsFragment settingsFragment;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mainFragment = new MainFragment();
        controlFragment = new ControlFragment();
        settingsFragment = new SettingsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mainFragment;
            case 1:
                return controlFragment;
            case 2:
                return settingsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "STATUS";
            case 1:
                return "STEUERUNG";
            case 2:
                return "EINSTELLUNGEN";
        }
        return super.getPageTitle(position);
    }
}
