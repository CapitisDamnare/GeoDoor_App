package geodoor.tapsi.geodoor_app.controller;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import geodoor.tapsi.geodoor_app.views.ControlFragment;
import geodoor.tapsi.geodoor_app.views.MainFragment;
import geodoor.tapsi.geodoor_app.views.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainFragment();
            case 1:
                return new ControlFragment();
            case 2:
                return new SettingsFragment();
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
