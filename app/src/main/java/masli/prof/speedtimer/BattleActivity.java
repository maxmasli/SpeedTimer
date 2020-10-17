package masli.prof.speedtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class BattleActivity extends AppCompatActivity {

    public static final String NICK_KEY = "nick";
    private String opponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Intent intent = getIntent();
        opponent = intent.getStringExtra(NICK_KEY);
    }

    public void sendToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

}


