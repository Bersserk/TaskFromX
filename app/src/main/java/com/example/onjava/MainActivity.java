package com.example.onjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, stop;
    TextView text;
    LinearLayout linLayout;

    BroadcastReceiver customReceiver;
    IntentFilter intentFilter;
    ArrayList<String> timeList;

    public static final int INTERVAL_TIME_SECONDS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        linLayout = findViewById(R.id.linLayout);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        intentFilter = new IntentFilter(MyReceiver.TIME);

        text = new TextView(this);
        text.setTextSize(32);
        text.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        text.setGravity(Gravity.TOP);
        linLayout.addView(text);

        customReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                long date = System.currentTimeMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("hh : mm : ss  a");
                final String dateString = sdf.format(date);

                timeList.add(dateString);
                StringBuffer bufferText;


                if (timeList.size() > (text.getHeight()) / 77) {
                    timeList.remove(0);

                    bufferText = new StringBuffer();

                    for (String s : timeList) {
                        bufferText.append(s + "\n");
                    }

                    text.setText(bufferText);
                } else {
                    text.append(dateString + "\n");
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopAllTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LOG", "Main: onDestroy");
        stopAllTask();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.start:
                timeList = new ArrayList<>();
                text.setText("");
                registerReceiver(customReceiver, intentFilter);
                startService(new Intent(this, MyService.class));
                Toast.makeText(this, "Service and Timer started", Toast.LENGTH_SHORT).show();
                break;

            case R.id.stop:
                stopAllTask();
                break;
        }
    }

    private void stopAllTask() {
        unregisterReceiver(customReceiver);
        stopService(new Intent(this, MyService.class));
        Toast.makeText(this, "Service and Timer stoped", Toast.LENGTH_SHORT).show();
    }

}
