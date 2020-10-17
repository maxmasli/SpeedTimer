package masli.prof.speedtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaitingListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Client client;

    private ListView waitingList;
    SimpleAdapter adapter;
    ArrayList<Map<String, Object>> data;
    Map<String, Object> m;

    final String ATTRIBUTE_NAME_NICKNAME = "nickname";
    final String ATTRIBUTE_NAME_EVENT = "event";

    //здесь уже надо подключатьсяч к серверу, скопировать код из баииле активити

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);

        waitingList = findViewById(R.id.waiting_list);

        data = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 10; i++) {
//            m = new HashMap<String, Object>();
//            m.put(ATTRIBUTE_NAME_NICKNAME, "nickname " + i);
//            m.put(ATTRIBUTE_NAME_EVENT, getResources().getString(R.string.cube3x3x3));
//            data.add(m);
//        }

        String[] from = {ATTRIBUTE_NAME_NICKNAME, ATTRIBUTE_NAME_EVENT};
        int[] to = {R.id.nickname, R.id.event};

        adapter = new SimpleAdapter(this, data, R.layout.item_waiting_users, from, to);

        waitingList.setAdapter(adapter);
        waitingList.setOnItemClickListener(this);

        client = new Client(this); // подключаемся к серверу
        client.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String nick = view.findViewById(R.id.nickname).toString();
        toBattleActivity(nick);
    }

    public void addToList(String nickname){
        m = new HashMap<String, Object>();
        m.put(ATTRIBUTE_NAME_NICKNAME, nickname);
        m.put(ATTRIBUTE_NAME_EVENT, getResources().getString(R.string.cube3x3x3));
        data.add(m);
        adapter.notifyDataSetChanged();
    }

    public void removeToList(String nickname){
        for (Map m: data) {
            if (m.get(ATTRIBUTE_NAME_NICKNAME).equals(nickname)){
                data.remove(m);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void toBattleActivity(String nick) {
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra(BattleActivity.NICK_KEY, nick);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        client.interrupt();
        super.onDestroy();
    }
}
