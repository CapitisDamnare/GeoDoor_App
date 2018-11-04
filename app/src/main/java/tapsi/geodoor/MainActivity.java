package tapsi.geodoor;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import tapsi.geodoor.model.NavigationMenuController;
import tapsi.geodoor.model.TabViewModel;
import tapsi.geodoor.geodoor_app.R;
import tapsi.geodoor.global.WarningFragmentDialog;
import tapsi.geodoor.logic.Constants;
import tapsi.geodoor.logic.service.MyService;

public class MainActivity extends AppCompatActivity implements WarningFragmentDialog.Communicator {

    private TabViewModel tabViewModel;
    private NavigationMenuController navigationMenuController;
    private static MainActivity mainActivity;

    MyService myService;

    // Permission stuff
    public static final int MY_PERMISSIONS_REQUESTS = 99;

    private String TAG = "tapsi.main";

    public MainActivity getActivity() {
        return this;
    }

    public static MainActivity getStaticActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);

        tabViewModel = ViewModelProviders.of(this).get(TabViewModel.class);
        navigationMenuController = new NavigationMenuController(this, tabViewModel);

        setUpToolbar();
        mHandlerTask.run();
    }

    private void setUpToolbar() {
        Toolbar toolbar = mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);
        ActionBar actionbar = mainActivity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }

    private void startForegroundService() {
//        Intent startIntent = new Intent(this, MyService.class);
//        startIntent.setAction(Constants.ACTION.SOCKET_START);
//        startService(startIntent);
//        bindService(startIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder sBinder = (MyService.MyBinder) service;
            myService = sBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigationMenuController.getmDrawerLayout().openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUESTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                    Log.d(TAG, "Permission granted 1");
                    startForegroundService();
//                    permissionGranted = true;

                } else {
                    Toast.makeText(this, "Permission Denied\nClosing Application", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mainActivity.finishAndRemoveTask();
                    } else
                    {
                        mainActivity.finishAffinity();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onDialogMessage(String message) {

        switch (message) {
            case "ok":
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUESTS);
                break;
            case "close":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mainActivity.finishAndRemoveTask();
                } else
                {
                    mainActivity.finishAffinity();
                }
                break;
        }
    }

    // Wait for successful binding of the service
    Runnable mHandlerTask = new Runnable() {

        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) +
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_PHONE_STATE)
                        == PackageManager.PERMISSION_GRANTED) {
                    startForegroundService();
                }
            } else {
                startForegroundService();
            }
        }
    };

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_PHONE_STATE)) {

                WarningFragmentDialog warningFragmentDialog = new WarningFragmentDialog();
                warningFragmentDialog.show(getFragmentManager(), "Dialog");


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUESTS);
            }
            return false;
        }
        return true;
    }

    public void sendOutBroadcast(String event, String name, String value) {
        Intent intent = new Intent(event);
        intent.putExtra(name, value);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
