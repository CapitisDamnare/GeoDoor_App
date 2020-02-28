package tapsi.geodoor.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import tapsi.geodoor.geodoor_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private OnSettingsFragmentListener mListener;

    private TextInputEditText editText;
    private String TAG = "tapsi.geodoor.views.SettingsFragment";

    public SettingsFragment() {
        // Required empty public constructor
    }

    public void setEditText(String name) {
        this.editText.setText(name);
    }

    public interface OnSettingsFragmentListener {
        void onNameChanged(String name);
        void onSettingsCreated();
    }

    public void setListener(OnSettingsFragmentListener listener) {
        mListener = listener;
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

        if (mListener != null) {
            mListener.onSettingsCreated();
        }
        editText = getView().findViewById(R.id.textEditName);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && mListener != null) {
                    mListener.onNameChanged(editText.getText().toString());
                }
            }
        });
    }
}
