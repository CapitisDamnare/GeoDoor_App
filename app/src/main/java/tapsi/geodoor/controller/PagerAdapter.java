package tapsi.geodoor.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import tapsi.geodoor.views.ControlFragment;
import tapsi.geodoor.views.MainFragment;
import tapsi.geodoor.views.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private String TAG = "tapsi.geodoor.controller.PagerAdapter";
    private static int NUM_ITEMS = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance();
            case 1:
                return ControlFragment.newInstance();
            case 2:
                return SettingsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

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
        return null;
    }
}
