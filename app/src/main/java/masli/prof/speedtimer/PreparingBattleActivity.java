package masli.prof.speedtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PreparingBattleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn;
    private EditText editNickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparing_battle);

        editNickname = (EditText) findViewById(R.id.edit_nickname);
        editNickname.setText(MainActivity.name);

        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                MainActivity.name = editNickname.getText().toString();
                Intent intent = new Intent(this, WaitingListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
