package geodoor.tapsi.geodoor_app.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
                return new SettingsFragment();
            case 2:
                return new MainFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
