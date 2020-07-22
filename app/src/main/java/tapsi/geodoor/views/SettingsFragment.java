package tapsi.geodoor.views;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import tapsi.geodoor.R;
import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.viewModel.TabViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private String TAG = "tapsi.geodoor.views.SettingsFragment";
    private Config currentConfig;

    private SettingsFragmentListener callback;

    private TabViewModel tabViewModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    public interface SettingsFragmentListener {
        void onBtnMapOpen(Location location);
    }

    public void setOnSettingsFragmentListener(SettingsFragment.SettingsFragmentListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabViewModel = new ViewModelProvider(getActivity()).get(TabViewModel.class);

        initLiveDataObservers();
        initButtons();
        initTextEdit();
    }

    private void initLiveDataObservers() {
        if (tabViewModel.getConfig() != null)
        {
            tabViewModel.getConfig().observe(getViewLifecycleOwner(), config -> {
                if (config!= null) {
                    currentConfig = config;
                    applyCurrentConfig();
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButtons() {
        Button controls_btn_save = getView().findViewById(R.id.controls_btn_save);
        Button controls_btn_cancel = getView().findViewById(R.id.controls_btn_cancel);
        Button controls_btn_map = getView().findViewById(R.id.controls_btn_map);

        controls_btn_save.setOnClickListener(v -> {
            saveConfig();
            tabViewModel.setConfig(currentConfig);
            setButtonBarVisibility(View.INVISIBLE);
        });
        controls_btn_cancel.setOnClickListener(v -> {
            applyCurrentConfig();
            setButtonBarVisibility(View.INVISIBLE);
        });
        controls_btn_map.setOnClickListener(v -> {
            Location location = new Location("");
            location.setLatitude(Double.parseDouble(currentConfig.getLatitude()));
            location.setLongitude(Double.parseDouble(currentConfig.getLongitude()));
            location.setAltitude(Double.parseDouble(currentConfig.getAltitude()));
            callback.onBtnMapOpen(location);
        });

        controls_btn_save.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        controls_btn_cancel.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        controls_btn_map.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTextEdit() {
        TextInputEditText txtEditName = getView().findViewById(R.id.txtEditName);
        TextInputEditText txtEditIpAddress = getView().findViewById(R.id.txtEditIpAddress);
        TextInputEditText txtEditRadius = getView().findViewById(R.id.txtEditRadius);
        TextInputEditText txtEditAccuracy = getView().findViewById(R.id.txtEditAccuracy);

        txtEditName.setOnTouchListener((view, motionEvent) -> {
            setButtonBarVisibility(View.VISIBLE);
            return false;
        });

        txtEditIpAddress.setOnTouchListener((view, motionEvent) -> {
            setButtonBarVisibility(View.VISIBLE);
            return false;
        });

        txtEditRadius.setOnTouchListener((view, motionEvent) -> {
            setButtonBarVisibility(View.VISIBLE);
            return false;
        });

        txtEditAccuracy.setOnTouchListener((view, motionEvent) -> {
            setButtonBarVisibility(View.VISIBLE);
            return false;
        });
    }

    private void startAnimation(View view, MotionEvent motionEvent) {
        final Animation animScale = AnimationUtils.loadAnimation(getView().getContext(),
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

    private void applyCurrentConfig() {
        TextInputEditText txtEditName = getView().findViewById(R.id.txtEditName);
        txtEditName.setText(currentConfig.getName());
        TextInputEditText txtEditIpAddress = getView().findViewById(R.id.txtEditIpAddress);
        txtEditIpAddress.setText(currentConfig.getIpAddress());
        TextInputEditText txtEditRadius = getView().findViewById(R.id.txtEditRadius);
        txtEditRadius.setText(String.valueOf(currentConfig.getRadius()));
        TextInputEditText txtEditAccuracy = getView().findViewById(R.id.txtEditAccuracy);
        txtEditAccuracy.setText(String.valueOf(currentConfig.getAccuracy()));

        TextView txtViewLatitude = getView().findViewById(R.id.txtViewLatitude);
        txtViewLatitude.setText(currentConfig.getLatitude());
        TextView txtViewLongitude = getView().findViewById(R.id.txtViewLongitude);
        txtViewLongitude.setText(currentConfig.getLongitude());
    }

    private void saveConfig() {
        TextInputEditText txtEditName = getView().findViewById(R.id.txtEditName);
        currentConfig.setName(String.valueOf(txtEditName.getText()));
        TextInputEditText txtEditIpAddress = getView().findViewById(R.id.txtEditIpAddress);
        currentConfig.setIpAddress(String.valueOf(txtEditIpAddress.getText()));
        TextInputEditText txtEditRadius = getView().findViewById(R.id.txtEditRadius);
        currentConfig.setRadius(Integer.parseInt(String.valueOf(txtEditRadius.getText())));
        TextInputEditText txtEditAccuracy = getView().findViewById(R.id.txtEditAccuracy);
        currentConfig.setAccuracy(Float.parseFloat(String.valueOf(txtEditAccuracy.getText())));
    }

    private void setButtonBarVisibility(int visibility) {
        LinearLayout layout = getView().findViewById(R.id.layout_buttons);
        layout.setVisibility(visibility);
    }
}
