package com.example.onjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, stop;
    TextView text;

    BroadcastReceiver customReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        text = findViewById(R.id.text);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        intentFilter = new IntentFilter(MyReceiver.TIME);

        customReceiver = new BroadcastReceiver() {

            int time;

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("LOG", "MyReceiver: сработал метод onReciever");
                Log.d("LOG", "MyReceiver: значение i из MyService - " + intent);

                time = intent.getIntExtra(MyService.TIME, 0);
                text.setText("time = " + time);
                Toast.makeText(context, "значение i = " + time, Toast.LENGTH_SHORT).show();
            }
        };






    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LOG", "Main: onResume");

        registerReceiver(customReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOG", "Main: onPause");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LOG", "Main: onDestroy");

        MyService ms = new MyService();
        ms.stopSelf();

    }

    @Override
    public void onClick(View v){

        switch (v.getId()) {

            case R.id.start:
                startService(new Intent(this, MyService.class));
                Toast.makeText(this, "нажата кнопка стартсервис", Toast.LENGTH_SHORT).show();
                break;

            case R.id.stop:
                unregisterReceiver(customReceiver);
                stopService(new Intent(this, MyService.class));
                break;
        }
    }
}
