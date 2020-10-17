package masli.prof.speedtimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;

import masli.prof.cubinghelper.CubingHelper;

public class MainActivity extends AppCompatActivity {

    public static String name = "user";

    private static final int CHOOSE_EVENT_ID = 1;

    private TimerViewPager mainPager;
    private UpdateListener listener;

    private TimerFragment timerFragment;
    private ResultsFragment resultsFragment;

    // интерфасе для обновления результатов в ResultsFragment
    public interface UpdateListener {
        void updateAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //это чтобы экран не выключался

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager()); //создается адаптер
        mainPager = (TimerViewPager) findViewById(R.id.main_pager);

        BottomAppBar bar = findViewById(R.id.bottom_app_bar); //создается бар который внизу
        setSupportActionBar(bar); //тут так надо

        timerFragment = new TimerFragment();
        resultsFragment = new ResultsFragment();
        listener = (UpdateListener) resultsFragment;

        mainPager.setAdapter(adapter);
        mainPager.setCurrentItem(0);


    }

    //адаптер
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return timerFragment;
                case 1:
                    return resultsFragment;
                default:
                    return timerFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    //меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar_menu, menu);

        MenuItem item = menu.findItem(R.id.app_bar_current_event);

        switch (CubingHelper.currentEvent) { //здесь ставится иконка в зависимости от выбранного евента
            case CubingHelper.event_333:
                item.setIcon(R.drawable.cube333);
                break;
            case CubingHelper.event_222:
                item.setIcon(R.drawable.cube222);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_battle:
                Intent intent = new Intent(this, PreparingBattleActivity.class);
                startActivity(intent);
                break;
            case R.id.app_bar_current_event:
                FragmentManager manager = getSupportFragmentManager();
                DialogChooseEvent dialog = new DialogChooseEvent(this);
                dialog.show(manager, "");
                break;
        }
        return true;
    }

    //метод для обновления
    public void update() {
        listener.updateAdapter();
    }

    public void updateTimer() {
        timerFragment.updateTimer(CubingHelper.currentEvent);
    }


    @Override
    public void onBackPressed() {
        if (timerFragment.isRunning) {
            timerFragment.cancelSolve();
        } else {
            super.onBackPressed();
        }
    }
}
