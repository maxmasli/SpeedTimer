package masli.prof.speedtimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import masli.prof.cubinghelper.CubingHelper;

public class TimerFragment extends Fragment {

    private TextView timer;
    private TextView scrambleText;
    private FrameLayout frameLayout;

    private TextView text_avg_5;
    private TextView text_avg_12;
    private TextView text_avg_50;
    private TextView text_avg_100;

    private View.OnTouchListener listenerIfRunning;
    private View.OnTouchListener listenerIfNotRunning;

    public boolean isRunning = false;
    private CubingHelper cubingHelper;

    private long startTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        setHasOptionsMenu(true);

        listenerIfNotRunning = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { // листенер если таймер не идет
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!isRunning) { // при начале сборки
                        startTime = System.currentTimeMillis();
                        isRunning = true;
                        TimerViewPager.enableSwipe = false;
                        timer.setText("...");
                        frameLayout.setOnTouchListener(listenerIfRunning);
                    }
                }
                return true;
            }
        };

        listenerIfRunning = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { // листенер если таймер идет
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    long ct = System.currentTimeMillis();
                    double res = (double) (ct - startTime) / 1000;
                    isRunning = false;
                    cubingHelper.addResult(res, cubingHelper.getScramble(), CubingHelper.currentEvent); // добавляется результат

                    updateTimer(CubingHelper.currentEvent); // обновление
                    timer.setText(res + "");

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    frameLayout.setOnTouchListener(listenerIfNotRunning);
                    TimerViewPager.enableSwipe = true;
                }
                return true;
            }
        };


        timer = (TextView) v.findViewById(R.id.text_timer);
        frameLayout = (FrameLayout) v.findViewById(R.id.frame_layout);
        frameLayout.setOnTouchListener(listenerIfNotRunning);
        scrambleText = (TextView) v.findViewById(R.id.scramble);

        text_avg_5 = (TextView) v.findViewById(R.id.result_of_avg_5);
        text_avg_12 = (TextView) v.findViewById(R.id.result_of_avg_12);
        text_avg_50 = (TextView) v.findViewById(R.id.result_of_avg_50);
        text_avg_100 = (TextView) v.findViewById(R.id.result_of_avg_100);

        cubingHelper = CubingHelper.get();
        switch (CubingHelper.currentEvent) {
            case CubingHelper.event_333:
                scrambleText.setText(CubingHelper.get().get333RandomScramble());
                break;
            case CubingHelper.event_222:
                scrambleText.setText(CubingHelper.get().get222RandomScramble());
                break;
        }


        recountingAvgs(CubingHelper.currentEvent);
        return v;
    }

    public void cancelSolve() {
        isRunning = false;
        updateTimer(CubingHelper.currentEvent); // обновление
        timer.setText("0.00");
        frameLayout.setOnTouchListener(listenerIfNotRunning);
        TimerViewPager.enableSwipe = true;
    }

    public void updateTimer(String currentEvent) {
        Activity activityMain = getActivity(); // вызывает метод из MainActivity

        switch (currentEvent) {
            case CubingHelper.event_333:
                scrambleText.setText(cubingHelper.get333RandomScramble());
                break;

            case CubingHelper.event_222:
                scrambleText.setText(cubingHelper.get222RandomScramble());
                break;
        }

        recountingAvgs(currentEvent); // пересчет авг

        ((MainActivity) activityMain).update();
        ((MainActivity) activityMain).invalidateOptionsMenu();

    }

    private void recountingAvgs(String currentEvent) {
        Object avg5;
        Object avg12;
        Object avg50;
        Object avg100;
        switch (currentEvent) {
            case CubingHelper.event_333:
                avg5 = cubingHelper.countAvg5(cubingHelper.getResults333());
                avg12 = cubingHelper.countAvg12(cubingHelper.getResults333());
                avg50 = cubingHelper.countAvg50(cubingHelper.getResults333());
                avg100 = cubingHelper.countAvg100(cubingHelper.getResults333());

                if (avg5 == null) {
                    text_avg_5.setText("---");
                } else {
                    text_avg_5.setText(String.valueOf(avg5));
                }

                if (avg12 == null) {
                    text_avg_12.setText("---");
                } else {
                    text_avg_12.setText(String.valueOf(avg12));
                }

                if (avg50 == null) {
                    text_avg_50.setText("---");
                } else {
                    text_avg_50.setText(String.valueOf(avg50));
                }

                if (avg100 == null) {
                    text_avg_100.setText("---");
                } else {
                    text_avg_100.setText(String.valueOf(avg100));
                }

                break;
            case CubingHelper.event_222:
                avg5 = cubingHelper.countAvg5(cubingHelper.getResults222());
                avg12 = cubingHelper.countAvg12(cubingHelper.getResults222());
                avg50 = cubingHelper.countAvg50(cubingHelper.getResults222());
                avg100 = cubingHelper.countAvg100(cubingHelper.getResults222());

                if (avg5 == null) {
                    text_avg_5.setText("---");
                } else {
                    text_avg_5.setText(String.valueOf(avg5));
                }

                if (avg12 == null) {
                    text_avg_12.setText("---");
                } else {
                    text_avg_12.setText(String.valueOf(avg12));
                }

                if (avg50 == null) {
                    text_avg_50.setText("---");
                } else {
                    text_avg_50.setText(String.valueOf(avg50));
                }

                if (avg100 == null) {
                    text_avg_100.setText("---");
                } else {
                    text_avg_100.setText(String.valueOf(avg100));
                }
                break;
        }
    }

}
