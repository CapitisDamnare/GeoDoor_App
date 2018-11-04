package tapsi.geodoor.model;

import android.content.Intent;
import com.google.android.material.navigation.NavigationView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;

import tapsi.geodoor.MainActivity;
import tapsi.geodoor.geodoor_app.R;
import tapsi.geodoor.logic.Constants;
import tapsi.geodoor.logic.service.MyService;

public class NavigationMenuController {

    private MainActivity mainActivity;
    private TabViewModel tabViewModel;
    private DrawerLayout mDrawerLayout;

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public NavigationMenuController(MainActivity mainActivity, TabViewModel tabViewModel) {
        this.mainActivity = mainActivity;
        this.tabViewModel = tabViewModel;
        mDrawerLayout = mainActivity.findViewById(R.id.drawer_layout);
        setupNavigationMenu();
    }

    private void setupNavigationMenu() {
        NavigationView navigationView = mainActivity.findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        final PagerAdapter pagerAdapter = (PagerAdapter) tabViewModel.getViewPager().getAdapter();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        String title = menuItem.getTitle().toString();

                        if (title.equals(mainActivity.getResources().getString(R.string.back))) {
                            pagerAdapter.getMainFragment().doorAnimationOpen();
                            return true;
                        }

                        if (title.equals(mainActivity.getResources().getString(R.string.settings))) {
                            tabViewModel.getViewPager().setCurrentItem(2);
                            return true;
                        }

                        if (title.equals(mainActivity.getResources().getString(R.string.about))) {
                            pagerAdapter.getMainFragment().doorAnimationClose();
                            //sendOutBroadcast(Constants.BROADCAST.EVENT_TOSOCKET, Constants.BROADCAST.NAME_OPENGATE, "true");
                            return true;
                        }

                        if (title.equals(mainActivity.getResources().getString(R.string.hide))) {
                            hideApplication();
                            return true;
                        }

                        if (title.equals(mainActivity.getResources().getString(R.string.exit))) {
                            Intent stopIntent = new Intent(mainActivity, MyService.class);
                            stopIntent.setAction(Constants.ACTION.SOCKET_STOP);
                            mainActivity.startService(stopIntent);
                            mainActivity.unbindService(mainActivity.serviceConnection);
                            closeApplication();
                            return true;
                        }
                        return true;
                    }
                });

        // Style Navigation Text
        Menu menu = navigationView.getMenu();

        MenuItem itemBack = menu.findItem(R.id.nav_back);
        SpannableString s_itemBack = new SpannableString(itemBack.getTitle());
        s_itemBack.setSpan(new

                TextAppearanceSpan(mainActivity, R.style.TextAppearanceItemWhite), 0, s_itemBack.length(), 0);
//        s_itemBack.setSpan(new CustomTypefaceSpan("", typeface),0,s_itemBack.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        itemBack.setTitle(s_itemBack);

        MenuItem itemSettings = menu.findItem(R.id.nav_settings);
        SpannableString s_itemSettings = new SpannableString(itemSettings.getTitle());
        s_itemSettings.setSpan(new

                TextAppearanceSpan(mainActivity, R.style.TextAppearanceItemBlue), 0, s_itemSettings.length(), 0);
        itemSettings.setTitle(s_itemSettings);

        MenuItem itemAbout = menu.findItem(R.id.nav_about);
        SpannableString s_itemAbout = new SpannableString(itemAbout.getTitle());
        s_itemAbout.setSpan(new

                TextAppearanceSpan(mainActivity, R.style.TextAppearanceItemWhite), 0, s_itemAbout.length(), 0);
        itemAbout.setTitle(s_itemAbout);

        MenuItem itemHide = menu.findItem(R.id.nav_hide);
        SpannableString s_itemHide = new SpannableString(itemHide.getTitle());
        s_itemHide.setSpan(new

                TextAppearanceSpan(mainActivity, R.style.TextAppearanceItemBlue), 0, s_itemHide.length(), 0);
        itemHide.setTitle(s_itemHide);

        MenuItem itemExit = menu.findItem(R.id.nav_exit);
        SpannableString s_itemExit = new SpannableString(itemExit.getTitle());
        s_itemExit.setSpan(new

                TextAppearanceSpan(mainActivity, R.style.TextAppearanceItemWhite), 0, s_itemExit.length(), 0);
        itemExit.setTitle(s_itemExit);
    }

    private void closeApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainActivity.finishAndRemoveTask();
        } else
        {
            mainActivity.finishAffinity();
        }
    }

    private void hideApplication() {
        mainActivity.moveTaskToBack(false);
    }

    public void sendOutBroadcast(String event, String name, String value) {
        Intent intent = new Intent(event);
        intent.putExtra(name, value);
        LocalBroadcastManager.getInstance(mainActivity).sendBroadcast(intent);
    }
}
