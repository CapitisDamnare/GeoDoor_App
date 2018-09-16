package geodoor.tapsi.geodoor_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new geodoor.tapsi.geodoor_app.controller.PagerAdapter(getSupportFragmentManager()));

        setupNavigationMenu();


        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
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

    private void setUpToolbar() {

    }

    private void setupNavigationMenu() {
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        // Style Navigation Text
        Menu menu = navigationView.getMenu();

        MenuItem itemBack = menu.findItem(R.id.nav_back);
        SpannableString s_itemBack = new SpannableString(itemBack.getTitle());
        s_itemBack.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceItemWhite), 0, s_itemBack.length(), 0);
//        s_itemBack.setSpan(new CustomTypefaceSpan("", typeface),0,s_itemBack.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        itemBack.setTitle(s_itemBack);

        MenuItem itemSettings = menu.findItem(R.id.nav_settings);
        SpannableString s_itemSettings = new SpannableString(itemSettings.getTitle());
        s_itemSettings.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceItemBlue), 0, s_itemSettings.length(), 0);
        itemSettings.setTitle(s_itemSettings);

        MenuItem itemAbout = menu.findItem(R.id.nav_about);
        SpannableString s_itemAbout = new SpannableString(itemAbout.getTitle());
        s_itemAbout.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceItemWhite), 0, s_itemAbout.length(), 0);
        itemAbout.setTitle(s_itemAbout);

        MenuItem itemHide = menu.findItem(R.id.nav_hide);
        SpannableString s_itemHide = new SpannableString(itemHide.getTitle());
        s_itemHide.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceItemBlue), 0, s_itemHide.length(), 0);
        itemHide.setTitle(s_itemHide);

        MenuItem itemExit = menu.findItem(R.id.nav_exit);
        SpannableString s_itemExit = new SpannableString(itemExit.getTitle());
        s_itemExit.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceItemWhite), 0, s_itemExit.length(), 0);
        itemExit.setTitle(s_itemExit);
    }
}
