package tapsi.geodoor.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import tapsi.geodoor.geodoor_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    public ControlFragment() {
        // Required empty public constructor
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
        buttonAnimation();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void buttonAnimation() {

        Button btn_gate_auto = getView().findViewById(R.id.controls_btn_gate_auto);
        Button btn_gate_man = getView().findViewById(R.id.controls_btn_gate_man);
        Button btn_mode = getView().findViewById(R.id.controls_btn_mode);
        Button btn_door = getView().findViewById(R.id.controls_btn_door);
        Button btn_gate_isOpen = getView().findViewById(R.id.controls_btn_gate_isOpen);
        Button btn_gate_isClosed = getView().findViewById(R.id.controls_btn_gate_isClosed);
        Button btn_open_visu = getView().findViewById(R.id.controls_btn_open_visu);
        Button btn_homepage = getView().findViewById(R.id.controls_btn_homepage);
        Button btn_log = getView().findViewById(R.id.controls_btn_log);
        Button btn_error_report = getView().findViewById(R.id.controls_btn_error_report);


        btn_gate_auto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gate_man.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_mode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_door.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gate_isOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gate_isClosed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_open_visu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_homepage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_error_report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getView().getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gate_auto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_gate_man.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_mode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_door.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_gate_isOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_gate_isClosed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_open_visu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_homepage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_log.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

        });

        btn_error_report.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startAnimation(view, motionEvent);
                return false;
            }

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
