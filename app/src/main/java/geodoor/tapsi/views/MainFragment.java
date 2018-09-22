package geodoor.tapsi.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import geodoor.tapsi.geodoor_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private Animation doorAnimation1;
    private Animation doorAnimation2;
    private Animation doorAnimation3;
    private Animation doorAnimation4;
    private Animation doorAnimation5;
    private Animation doorAnimation6;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public  void doorAnimationOpen() {
        doorAnimation1 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v1 = getView().findViewById(R.id.status_door_panel6);
        v1.startAnimation(doorAnimation1);

        doorAnimation2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v2 =  getView().findViewById(R.id.status_door_panel5);

        v2.postDelayed(new Runnable() {
            @Override
            public void run() {
                v2.startAnimation(doorAnimation2);
            }

        }, 200);

        doorAnimation3 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v3 = getView().findViewById(R.id.status_door_panel4);

        v3.postDelayed(new Runnable() {
            @Override
            public void run() {
                v3.startAnimation(doorAnimation3);
            }

        }, 500);

        doorAnimation4 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v4 = getView().findViewById(R.id.status_door_panel3);

        v4.postDelayed(new Runnable() {
            @Override
            public void run() {
                v4.startAnimation(doorAnimation4);
            }

        }, 800);

        doorAnimation5 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v5 = getView().findViewById(R.id.status_door_panel2);

        v5.postDelayed(new Runnable() {
            @Override
            public void run() {
                v5.startAnimation(doorAnimation5);
            }

        }, 1000);

        doorAnimation6 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_open);
        final ImageView v6 = getView().findViewById(R.id.status_door_panel1);

        v6.postDelayed(new Runnable() {
            @Override
            public void run() {
                v6.startAnimation(doorAnimation6);
            }

        }, 1200);
    }

    public void doorAnimationClose() {
        doorAnimation1 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v1 = getView().findViewById(R.id.status_door_panel1);
        v1.startAnimation(doorAnimation1);

        doorAnimation2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v2 = getView().findViewById(R.id.status_door_panel2);

        v2.postDelayed(new Runnable() {
            @Override
            public void run() {
                v2.startAnimation(doorAnimation2);
            }

        }, 200);

        doorAnimation3 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v3 = getView().findViewById(R.id.status_door_panel3);

        v3.postDelayed(new Runnable() {
            @Override
            public void run() {
                v3.startAnimation(doorAnimation3);
            }

        }, 500);

        doorAnimation4 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v4 = getView().findViewById(R.id.status_door_panel4);

        v4.postDelayed(new Runnable() {
            @Override
            public void run() {
                v4.startAnimation(doorAnimation4);
            }

        }, 800);

        doorAnimation5 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v5 = getView().findViewById(R.id.status_door_panel5);

        v5.postDelayed(new Runnable() {
            @Override
            public void run() {
                v5.startAnimation(doorAnimation5);
            }

        }, 1000);

        doorAnimation6 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.anim_translate_door_close);
        final ImageView v6 = getView().findViewById(R.id.status_door_panel6);

        v6.postDelayed(new Runnable() {
            @Override
            public void run() {
                v6.startAnimation(doorAnimation6);
            }

        }, 1200);
    }
}
