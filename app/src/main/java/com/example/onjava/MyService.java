package com.example.onjava;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    Boolean stop = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setTime ();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LOG", "destroy");
        stop = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setTime() {
        //MainActivity.text.setText("new text");
        Log.d("LOG", "new text - " + MainActivity.text.getText() );
        Log.d("LOG", "setTime" );
        new Thread(new Runnable() {
            public void run() {
                Log.d("LOG", "run" );
                for (int i = 0; i > -1 ; i++) {
                    if(stop)
                        break;
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        MainActivity.newText("newtext");
                        Log.d("LOG", "try " + i);
                        //MainActivity.text.setText("text");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("LOG", "while");
                }
                Log.d("LOG", "вышли с цикла");
                stopSelf();
            }
        }).start();
    }
}
