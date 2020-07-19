package tapsi.geodoor.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import tapsi.geodoor.R;
import tapsi.geodoor.model.TabViewModel;

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
        tabViewModel.getAutoMode().observe(getViewLifecycleOwner(), isAutoMode -> {
            Log.i(TAG, "getAutoMode().observe: " + isAutoMode);
            TextView statusMode = getView().findViewById(R.id.status_mode_textView);
            if(isAutoMode)
                statusMode.setText(R.string.status_mode_textView_auto);
            else
                statusMode.setText(R.string.status_mode_textView_man);
        });
    }

    public void doorAnimationOpen() {
        if(null == getView())
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
        if(null == getView())
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
}
