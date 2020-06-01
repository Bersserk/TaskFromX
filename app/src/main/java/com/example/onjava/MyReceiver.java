package com.example.onjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static final String TIME = "SERVICE_TIME";
    int time;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("LOG", "MyReceiver: сработал метод onReciever");
        Log.d("LOG", "MyReceiver: значение i из MyService - " + intent);

        time = intent.getIntExtra(MyService.TIME, 0);
        Toast.makeText(context, "значение i = " + time, Toast.LENGTH_SHORT).show();


    }
}
