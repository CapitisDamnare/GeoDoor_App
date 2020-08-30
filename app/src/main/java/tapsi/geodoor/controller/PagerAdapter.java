package tapsi.geodoor.controller;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import tapsi.geodoor.R;
import tapsi.geodoor.views.ControlFragment;
import tapsi.geodoor.views.MainFragment;
import tapsi.geodoor.views.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private String TAG = "tapsi.geodoor.controller.PagerAdapter";
    private static int NUM_ITEMS = 3;
    private Context context;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
                return context.getResources().getString(R.string.status);
            case 1:
                return context.getResources().getString(R.string.controls);
            case 2:
                return context.getResources().getString(R.string.settings);
        }
        return null;
    }
}
