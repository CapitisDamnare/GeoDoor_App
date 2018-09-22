package geodoor.tapsi.controller;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import geodoor.tapsi.MainActivity;
import geodoor.tapsi.geodoor_app.R;

public class TabController {

    private MainActivity mainActivity;
    private ViewPager viewPager;

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        setupTabLayout();
        setUpToolbar();
    }

    private void setupTabLayout() {
        viewPager = mainActivity.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(mainActivity.getSupportFragmentManager()));
        TabLayout tabLayout = mainActivity.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpToolbar() {
        Toolbar toolbar = mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);
        ActionBar actionbar = mainActivity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }
}
