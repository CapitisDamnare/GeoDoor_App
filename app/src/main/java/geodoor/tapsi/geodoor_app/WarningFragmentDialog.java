package geodoor.tapsi.geodoor_app;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class WarningFragmentDialog extends DialogFragment implements View.OnClickListener {
    private Button ok, close;
    Communicator communicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_warning, null);

        ok = view.findViewById(R.id.warning_dialog_ok);
        close = view.findViewById(R.id.warning_dialog_close);

        ok.setOnClickListener(this);
        close.setOnClickListener(this);
        setCancelable(false);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.warning_dialog_ok) {
            communicator.onDialogMessage("ok");
            dismiss();
        } else {
            communicator.onDialogMessage("close");
            dismiss();
        }
    }

    public interface Communicator {
        void onDialogMessage(String message);
    }
}
