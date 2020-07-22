package tapsi.geodoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import tapsi.geodoor.services.LocationUpdateServiceInfo;
import tapsi.geodoor.services.LocationUpdatesService;
import tapsi.geodoor.services.Utils;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String TAG = "tapsi.MapActivity";

    private Location savedLocation;
    private Location newLocation;

    private GoogleMap googleMap;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MapActivity.MyReceiver myReceiver;

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

            Log.i(TAG, "MapActivity connected");
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
        setContentView(R.layout.activity_map);
        initButtons();

        Intent intent = getIntent();
        savedLocation = (Location) intent.getParcelableExtra("location");

        TextView txtViewSavedLocation = findViewById(R.id.txtViewSavedLocation);
        txtViewSavedLocation.setText(formatLocation(savedLocation));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        ImageButton btnBack = findViewById(R.id.map_btn_back);
        btnBack.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });

        Button btnGetPosition = findViewById(R.id.map_btn_get_position);
        Button btnSavePosition = findViewById(R.id.map_btn_save_position);

        btnGetPosition.setOnClickListener(v -> {
            mService.getCurrentLocation();
        });

        btnSavePosition.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", newLocation);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnGetPosition.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btnSavePosition.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker and move the map's camera to the same location.
        if (savedLocation != null)
        {
            LatLng currentSavedLocation =
                    new LatLng(savedLocation.getLatitude(), savedLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(currentSavedLocation)
                    .title("Saved Location"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentSavedLocation, 21.0f) );
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                googleMap.addMarker(new MarkerOptions().position(point)
                        .title("New Gate Position"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(point));

                newLocation = new Location("");
                newLocation.setLatitude(point.latitude);
                newLocation.setLongitude(point.longitude);

                TextView txtViewCurrentMarkerLocation = findViewById(R.id.txtViewCurrentMarkerLocation);
                txtViewCurrentMarkerLocation.setText(formatLocation(point));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        super.onStop();
    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(mService.EXTRA_CURRENT_LOCATION)) {
                Location location = (Location) intent.getParcelableExtra(LocationUpdatesService.EXTRA_CURRENT_LOCATION);

                if (location == null)
                    return;

                LatLng currentSavedLocation =
                        new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(currentSavedLocation)
                        .title("Saved Location"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentSavedLocation, 19.0f) );

                newLocation = location;

                TextView txtViewCurrentMarkerLocation = findViewById(R.id.txtViewCurrentMarkerLocation);
                txtViewCurrentMarkerLocation.setText(formatLocation(location));
            }
        }
    }

    private void startAnimation(View view, MotionEvent motionEvent) {
        final Animation animScale = AnimationUtils.loadAnimation(this,
                R.anim.anim_scale);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundResource(R.color.colorDarkBlue);
                AnimationSet sets = new AnimationSet(false);
                sets.addAnimation(animScale);
                view.startAnimation(sets);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                view.setBackgroundResource(R.color.colorBlue);
                break;
        }
    }

    private String formatLocation(LatLng point) {
        Location location = new Location("");
        location.setLatitude(point.latitude);
        location.setLongitude(point.longitude);
        return formatLocation(location);
    }

    private String formatLocation(Location location) {
        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(RoundingMode.CEILING);

        return df.format(location.getLatitude()) + ",\n" + df.format(location.getLongitude());
    }
}