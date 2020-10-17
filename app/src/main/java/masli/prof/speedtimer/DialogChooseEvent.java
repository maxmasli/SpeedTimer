package masli.prof.speedtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import masli.prof.cubinghelper.CubingHelper;

public class DialogChooseEvent extends AppCompatDialogFragment {

    private String[] events = {"2x2x2", "3x3x3"};
    private MainActivity main;

    public DialogChooseEvent(MainActivity main) {
        this.main = main;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_event);
        builder.setItems(events, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        CubingHelper.currentEvent = CubingHelper.event_222;
                        break;
                    case 1:
                        CubingHelper.currentEvent = CubingHelper.event_333;
                        break;
                }
                main.updateTimer();
            }
        });

        return builder.create();
    }

}
