package geodoor.tapsi.geodoor_app;

//TODO: Refactor package name... can't deal with it

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;

import geodoor.tapsi.geodoor_app.helper.StaticValues;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;

    private String TAG = "tapsi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        setupTabLayout();
        setupNavigationMenu();
        setUpToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTabLayout() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new geodoor.tapsi.geodoor_app.controller.PagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }



    private void setupNavigationMenu() {
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case StaticValues.MENU_BACK:
                                break;
                            case StaticValues.MENU_SETTINGS:
                                viewPager.setCurrentItem(2);
                                break;
                            case StaticValues.MENU_ABOUT:
                                break;
                            case StaticValues.MENU_CLOSE:
                                hideApplication();
                                break;
                            case StaticValues.MENU_CLOSE_ALL:
                                closeApplication();
                                break;
                        }
                        return true;
                    }
                });

        // Style Navigation Text
        Menu menu = navigationView.getMenu();

        MenuItem itemBack = menu.findItem(R.id.nav_back);
        SpannableString s_itemBack = new SpannableString(itemBack.getTitle());
        s_itemBack.setSpan(new

                TextAppearanceSpan(this, R.style.TextAppearanceItemWhite), 0, s_itemBack.length(), 0);
//        s_itemBack.setSpan(new CustomTypefaceSpan("", typeface),0,s_itemBack.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        itemBack.setTitle(s_itemBack);

        MenuItem itemSettings = menu.findItem(R.id.nav_settings);
        SpannableString s_itemSettings = new SpannableString(itemSettings.getTitle());
        s_itemSettings.setSpan(new

                TextAppearanceSpan(this, R.style.TextAppearanceItemBlue), 0, s_itemSettings.length(), 0);
        itemSettings.setTitle(s_itemSettings);

        MenuItem itemAbout = menu.findItem(R.id.nav_about);
        SpannableString s_itemAbout = new SpannableString(itemAbout.getTitle());
        s_itemAbout.setSpan(new

                TextAppearanceSpan(this, R.style.TextAppearanceItemWhite), 0, s_itemAbout.length(), 0);
        itemAbout.setTitle(s_itemAbout);

        MenuItem itemHide = menu.findItem(R.id.nav_hide);
        SpannableString s_itemHide = new SpannableString(itemHide.getTitle());
        s_itemHide.setSpan(new

                TextAppearanceSpan(this, R.style.TextAppearanceItemBlue), 0, s_itemHide.length(), 0);
        itemHide.setTitle(s_itemHide);

        MenuItem itemExit = menu.findItem(R.id.nav_exit);
        SpannableString s_itemExit = new SpannableString(itemExit.getTitle());
        s_itemExit.setSpan(new

                TextAppearanceSpan(this, R.style.TextAppearanceItemWhite), 0, s_itemExit.length(), 0);
        itemExit.setTitle(s_itemExit);
    }

    private void closeApplication() {
        this.finishAndRemoveTask();
    }

    private void hideApplication() {
        this.hideApplication();
    }
}
