package tapsi.geodoor.views;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import tapsi.geodoor.R;
import tapsi.geodoor.logic.AutoGateLogic;
import tapsi.geodoor.viewModel.TabViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private String TAG = "tapsi.mainFragment";
    TabViewModel tabViewModel;

    private Animation doorAnimation1;
    private Animation doorAnimation2;
    private Animation doorAnimation3;
    private Animation doorAnimation4;
    private Animation doorAnimation5;
    private Animation doorAnimation6;

    private boolean isTimerRunning = false;
    private Timer timer;
    private long countDown;

    private boolean showStandardValues = false;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabViewModel = new ViewModelProvider(getActivity()).get(TabViewModel.class);
        initLiveDataObservers();
    }

    private void initLiveDataObservers() {
        tabViewModel.getAutoMode().observe(getViewLifecycleOwner(), isAutoMode -> {
            TextView statusMode = getView().findViewById(R.id.status_mode_textView);
            if (isAutoMode) {
                statusMode.setText(R.string.status_mode_textView_auto);
                showStandardValues = false;
            } else {
                statusMode.setText(R.string.status_mode_textView_man);
                showStandardValues = true;
                setStandardValues();
            }
        });

        tabViewModel.getDistance().observe(getViewLifecycleOwner(), distance -> {
            if (showStandardValues)
                return;

            TextView statusDistance = getView().findViewById(R.id.status_Distance);
            statusDistance.setText(formatDistance(distance));
        });

        tabViewModel.getLastLocation().observe(getViewLifecycleOwner(), lastLocation -> {
            if (showStandardValues)
                return;

            TextView statusSpeed = getView().findViewById(R.id.status_Speed);
            statusSpeed.setText(formatSpeed(lastLocation.getSpeed()));

            String text = lastLocation.getAccuracy() + " m";
            TextView statusAccuracy = getView().findViewById(R.id.status_Accuracy);
            statusAccuracy.setText(text);
        });

        tabViewModel.getUpdateInterval().observe(getViewLifecycleOwner(), updateInterval -> {
            if (showStandardValues)
                return;

            TextView statusGpsTimeout = getView().findViewById(R.id.status_gpsTimeout);
            String text = updateInterval / 1000 + " s";
            statusGpsTimeout.setText(text);
        });

        tabViewModel.getLastGateOpenEvent().observe(getViewLifecycleOwner(), countDown -> {
            if (showStandardValues)
                return;

            if (!isTimerRunning) {
                timer = new Timer();
                startCountDown(countDown);
            }
        });

        tabViewModel.getCurrentState().observe(getViewLifecycleOwner(), currentState -> {
            if (showStandardValues)
                return;

            TextView textView = getView().findViewById(R.id.status_Lock);
            if (currentState.equals(AutoGateLogic.TravelState.HOME))
                textView.setText(R.string.status_Lock_On);
            else if (currentState.equals(AutoGateLogic.TravelState.OUTSIDE))
                textView.setText(R.string.status_Lock_Off);
        });

        tabViewModel.getIsConnected().observe(getViewLifecycleOwner(), isConnected -> {
            TextView textView = getView().findViewById(R.id.status_connection);
            if (isConnected) {
                textView.setText(R.string.status_connection_connected);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
            } else {
                textView.setText(R.string.status_connection_connected);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            }
        });
    }

    public void doorAnimationOpen() {
        if (null == getView())
            return;

        doorAnimation1 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v1 = getView().findViewById(R.id.status_door_panel6);
        v1.startAnimation(doorAnimation1);

        doorAnimation2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v2 = getView().findViewById(R.id.status_door_panel5);

        v2.postDelayed(() -> v2.startAnimation(doorAnimation2), 200);

        doorAnimation3 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v3 = getView().findViewById(R.id.status_door_panel4);

        v3.postDelayed(() -> v3.startAnimation(doorAnimation3), 500);

        doorAnimation4 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v4 = getView().findViewById(R.id.status_door_panel3);

        v4.postDelayed(() -> v4.startAnimation(doorAnimation4), 800);

        doorAnimation5 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v5 = getView().findViewById(R.id.status_door_panel2);

        v5.postDelayed(() -> v5.startAnimation(doorAnimation5), 1000);

        doorAnimation6 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v6 = getView().findViewById(R.id.status_door_panel1);

        v6.postDelayed(() -> v6.startAnimation(doorAnimation6), 1200);
    }

    public void doorAnimationClose() {
        if (null == getView())
            return;

        doorAnimation1 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v1 = getView().findViewById(R.id.status_door_panel1);
        v1.startAnimation(doorAnimation1);

        doorAnimation2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v2 = getView().findViewById(R.id.status_door_panel2);

        v2.postDelayed(() -> v2.startAnimation(doorAnimation2), 200);

        doorAnimation3 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v3 = getView().findViewById(R.id.status_door_panel3);

        v3.postDelayed(() -> v3.startAnimation(doorAnimation3), 500);

        doorAnimation4 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v4 = getView().findViewById(R.id.status_door_panel4);

        v4.postDelayed(() -> v4.startAnimation(doorAnimation4), 800);

        doorAnimation5 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v5 = getView().findViewById(R.id.status_door_panel5);

        v5.postDelayed(() -> v5.startAnimation(doorAnimation5), 1000);

        doorAnimation6 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v6 = getView().findViewById(R.id.status_door_panel6);

        v6.postDelayed(() -> v6.startAnimation(doorAnimation6), 1200);
    }

    private String formatSpeed(float speed) {
        speed *= 3.6;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        String str_num = String.valueOf(df.format(speed));
        str_num += " km/h";
        return str_num;
    }

    private String formatDistance(float meter) {

        boolean km = false;
        if (meter > 999.99) {
            meter = meter / 1000;
            km = true;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        String str_num = String.valueOf(df.format(meter));
        if (km)
            str_num += " km";
        else
            str_num += " m";

        return str_num;
    }

    private void startCountDown(long countDown) {
        if (countDown <= 0)
            return;

        this.countDown = countDown;
        isTimerRunning = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                MainFragment.this.countDown -= 1; //increase every sec
                mHandler.obtainMessage(1).sendToTarget();

                if (MainFragment.this.countDown <= 0) {
                    timer.cancel();
                    isTimerRunning = false;
                }
            }
        }, 0, 1000);
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (getView() == null)
                return;
            TextView textView = getView().findViewById(R.id.status_LockTime);
            String text = countDown + " s";
            textView.setText(text);
        }
    };

    private void setStandardValues() {
        TextView statusDistance = getView().findViewById(R.id.status_Distance);
        TextView statusSpeed = getView().findViewById(R.id.status_Speed);
        TextView statusAccuracy = getView().findViewById(R.id.status_Accuracy);
        TextView statusGpsTimeout = getView().findViewById(R.id.status_gpsTimeout);

        statusDistance.setText(R.string.status_Distance);
        statusSpeed.setText(R.string.status_Speed);
        statusAccuracy.setText(R.string.status_Accuracy);
        statusGpsTimeout.setText(R.string.status_gpsTimeOut);
    }
}
