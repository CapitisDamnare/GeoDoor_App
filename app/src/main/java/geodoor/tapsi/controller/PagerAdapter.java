package geodoor.tapsi.controller;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import geodoor.tapsi.views.ControlFragment;
import geodoor.tapsi.views.MainFragment;
import geodoor.tapsi.views.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    MainFragment mainFragment = new MainFragment();
    ControlFragment controlFragment = new ControlFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public ControlFragment getControlFragment() {
        return controlFragment;
    }

    public SettingsFragment getSettingsFragment() {
        return settingsFragment;
    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
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
        switch (position){
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
