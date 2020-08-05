package tapsi.geodoor.views;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import tapsi.geodoor.R;
import tapsi.geodoor.viewModel.TabViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    private String TAG = "tapsi.controlFragment";
    private boolean isAutoMode;
    private ControlFragmentListener callback;
    private TabViewModel tabViewModel;

    public ControlFragment() {
        // Required empty public constructor
    }

    public static ControlFragment newInstance() {
        ControlFragment controlFragment = new ControlFragment();
        return controlFragment;
    }

    public interface ControlFragmentListener {
        void onBtnModeClicked(boolean isAutoMode);
        void onBtnOpenGate();
        void onBtnCloseGate();
        void onBtnOpenGateAuto();
        void onBtnOpenDoor();
    }

    public void setOnControlFragmentListener(ControlFragmentListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabViewModel = new ViewModelProvider(getActivity()).get(TabViewModel.class);
        tabViewModel.getAutoMode().observe(getViewLifecycleOwner(), isAutoMode -> {
            TextView statusMode = getView().findViewById(R.id.controls_btn_mode);
            this.isAutoMode = isAutoMode;
            if(isAutoMode)
                statusMode.setText(R.string.controls_btn_mode_activate_man);
            else
                statusMode.setText(R.string.controls_btn_mode_activate_auto);
        });
        buttonAnimation();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void buttonAnimation() {

        Button btn_gate_auto = getView().findViewById(R.id.controls_btn_gate_auto);
        Button btn_gate_man_open = getView().findViewById(R.id.controls_btn_gate_man_open);
        Button btn_gate_man_close = getView().findViewById(R.id.controls_btn_gate_man_close);
        Button btn_mode = getView().findViewById(R.id.controls_btn_mode);
        Button btn_door = getView().findViewById(R.id.controls_btn_door);
        Button btn_open_visu = getView().findViewById(R.id.controls_btn_open_visu);
        Button btn_homepage = getView().findViewById(R.id.controls_btn_homepage);
        Button btn_log = getView().findViewById(R.id.controls_btn_log);
        Button btn_error_report = getView().findViewById(R.id.controls_btn_error_report);


        btn_gate_auto.setOnClickListener(v -> {
            callback.onBtnOpenGateAuto();
        });

        btn_gate_man_open.setOnClickListener(v -> {
            callback.onBtnOpenGate();
        });

        btn_gate_man_close.setOnClickListener(v -> {
            callback.onBtnCloseGate();
        });

        btn_mode.setOnClickListener(v -> {
            isAutoMode = !isAutoMode;
            callback.onBtnModeClicked(isAutoMode);
        });

        btn_door.setOnClickListener(v -> {
            callback.onBtnOpenDoor();
        });

        btn_open_visu.setOnClickListener(v -> {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tabViewModel.getConfig().getValue().getIpAddress()));
                startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getView().getContext(), "Error",  Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        btn_homepage.setOnClickListener(v -> {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.die-technik-und-ich.at/?page_id=313"));
                startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getView().getContext(), "Error",  Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        btn_log.setOnClickListener(v -> Toast.makeText(getView().getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show());

        btn_error_report.setOnClickListener(v -> Toast.makeText(getView().getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show());

        btn_gate_auto.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_gate_man_open.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_gate_man_close.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_mode.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_door.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_open_visu.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_homepage.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_log.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
            return false;
        });

        btn_error_report.setOnTouchListener((view, motionEvent) -> {
            startAnimation(view, motionEvent);
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
}
