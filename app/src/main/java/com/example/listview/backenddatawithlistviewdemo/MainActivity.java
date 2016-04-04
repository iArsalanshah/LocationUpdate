package com.example.listview.backenddatawithlistviewdemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.database.Cursor;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static com.example.listview.backenddatawithlistviewdemo.DBHelper.KEY_LOCATION;

public class MainActivity extends AppCompatActivity {
    Button startServiceBtn;
    ListView lv;
    SQLController dbcon;
    private Button stopServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbcon = new SQLController(this);
        dbcon.open();
        startServiceBtn = (Button) findViewById(R.id.btnStartService);
        stopServiceBtn = (Button) findViewById(R.id.btnStopService);
        lv = (ListView) findViewById(R.id.memberList_id);

        startServiceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        });
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });

        // Attach The Data From DataBase Into ListView Using Crusor Adapter
        Cursor cursor = dbcon.readData();
        String[] from = new String[] { DBHelper.KEY_ID, KEY_LOCATION};
        int[] to = new int[] { R.id.member_id, R.id.member_latLngTime};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.view_member_entry, cursor, from, to);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }
}
