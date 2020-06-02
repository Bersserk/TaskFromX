package com.example.onjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, stop;
    TextView text;
    ListView listView;

    BroadcastReceiver customReceiver;
    IntentFilter intentFilter;
    ArrayAdapter<String> adapter;
    ArrayList <String> num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        text = findViewById(R.id.text);
        listView = findViewById(R.id.lv);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        num = new ArrayList<String>(10);


        intentFilter = new IntentFilter(MyReceiver.TIME);

        customReceiver = new BroadcastReceiver() {

            int time;

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("LOG", "MyReceiver: сработал метод onReciever");
                Log.d("LOG", "MyReceiver: значение i из MyService - " + intent);

                time = intent.getIntExtra(MyService.TIME, 0);
                String s = String.valueOf(time);




                num.add(String.valueOf(time));
                if(num.size() == 10)
                    num.remove(0);


                Log.d("LOG", "Arraylist = " + num.size());

                adapter = new ArrayAdapter<String>(getApplication(),
                        android.R.layout.simple_list_item_1, num);

                listView.setAdapter(adapter);

                /*
                if (time == 11 ) {
                    text.setText("");
                }
                    text.append(time+"\n");
                */



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
