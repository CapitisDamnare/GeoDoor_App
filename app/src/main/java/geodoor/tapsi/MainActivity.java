package geodoor.tapsi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import geodoor.tapsi.controller.NavigationMenuController;
import geodoor.tapsi.controller.TabController;
import geodoor.tapsi.geodoor_app.R;
import geodoor.tapsi.geodoor_app.WarningFragmentDialog;

public class MainActivity extends AppCompatActivity implements WarningFragmentDialog.Communicator {

    private TabController tabController;
    private NavigationMenuController navigationMenuController;

    // Permission stuff
    public static final int MY_PERMISSIONS_REQUESTS = 99;

    private String TAG = "tapsi";

    public MainActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabController = new TabController(this);
        navigationMenuController = new NavigationMenuController(this, tabController);

        mHandlerTask.run();
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
                    //startForegroundService();
//                    permissionGranted = true;

                } else {
                    Toast.makeText(this, "Permission Denied\nClosing Application", Toast.LENGTH_LONG).show();
                    finishAndRemoveTask();
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
                finishAndRemoveTask();
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
                    Log.d(TAG, "Permission granted 2");
//                    startForegroundService();
//                    permissionGranted = true;
                }
            } else {
                Log.d(TAG, "Permission granted 3");
//                startForegroundService();
//                permissionGranted = true;
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
}
