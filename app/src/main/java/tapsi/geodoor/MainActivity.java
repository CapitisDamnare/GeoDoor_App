package tapsi.geodoor;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.andexert.library.BuildConfig;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import tapsi.geodoor.controller.NavigationMenuController;
import tapsi.geodoor.controller.PagerAdapter;
import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.services.LocationUpdateServiceInfo;
import tapsi.geodoor.services.LocationUpdatesService;
import tapsi.geodoor.services.Utils;
import tapsi.geodoor.viewModel.TabViewModel;
import tapsi.geodoor.views.ControlFragment;
import tapsi.geodoor.views.SettingsFragment;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        ControlFragment.ControlFragmentListener,
        SettingsFragment.SettingsFragmentListener {

    private NavigationMenuController navigationMenuController;
    private PagerAdapter pagerAdapter;
    private TabViewModel tabViewModel;
    private MainActivity mainActivity;

    private String TAG = "tapsi.MainActivity";

    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 39;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;

    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            if (Utils.requestingLocationUpdates(getApplicationContext())) {
                mService.requestLocationUpdates();
            }

            tabViewModel.getConfig().observe(mainActivity, config -> {
                if (config != null) {
                    mService.updateConfig(config);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        mainActivity = this;

        setContentView(R.layout.activity_main);
        setupTabLayout();
        navigationMenuController = new NavigationMenuController(this);
        setUpToolbar();
        tabViewModel = new ViewModelProvider(this).get(TabViewModel.class);

        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        // Restore the state of the buttons when the activity (re)launches.
        setButtonsState(Utils.requestingLocationUpdates(this));

        startService(new Intent(this, LocationUpdatesService.class));
        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //stopService(new Intent(this, LocationUpdatesService.class));
    }

    /**
     * Initializes the {@see TabLayout} with the {@see PagerAdapter}.
     */
    private void setupTabLayout() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }

    /**
     * Initializes the {@see Toolbar}.
     */
    private void setUpToolbar() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.drawer_layout),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.drawer_layout),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigationMenuController.getmDrawerLayout().openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(mService.EXTRA_LOCATION)) {
                LocationUpdateServiceInfo info = (LocationUpdateServiceInfo) intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
                Log.i(TAG, "MainActivity got LocationUpdate");
                if (info != null) {
                    tabViewModel.setDistance(info.distance());
                    tabViewModel.setLastLocation(info.currentLocation());
                    tabViewModel.setUpdateInterval(info.currentUpdateInterval());
                    tabViewModel.setLastGateOpenEvent(info.countDown());
                    tabViewModel.setCurrentState(info.currentState());
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            Log.i(TAG, "onSharedPreferenceChanged");
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
    }

    private void setButtonsState(boolean requestingLocationUpdates) {
        tabViewModel.setAutoMode(requestingLocationUpdates);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ControlFragment) {
            ControlFragment controlFragment = (ControlFragment) fragment;
            controlFragment.setOnControlFragmentListener(this);
        }

        if (fragment instanceof SettingsFragment) {
            SettingsFragment settingsFragment = (SettingsFragment) fragment;
            settingsFragment.setOnSettingsFragmentListener(this);
        }
    }

    @Override
    public void onBtnModeClicked(boolean isAutoMode) {
        if (isAutoMode) {
            mService.requestLocationUpdates();
            setButtonsState(true);
        } else {
            mService.removeLocationUpdates(true);
            setButtonsState(false);
        }
    }

    @Override
    public void onBtnMapOpen(Location location) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("location", location);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "GotResult!");

                Location location = data.getParcelableExtra("result");
                if (location == null)
                    return;

                Config config = tabViewModel.getConfig().getValue();
                config.setLatitude(String.valueOf(location.getLatitude()));
                config.setLongitude(String.valueOf(location.getLongitude()));

                tabViewModel.setConfig(config);
            }
            if (resultCode == RESULT_CANCELED) {
                // Do nothing
            }
        }
    }

    public void stopApplication() {
        mService.removeLocationUpdates(false);
        this.finishAndRemoveTask();
    }
}
