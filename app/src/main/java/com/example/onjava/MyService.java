package com.example.onjava;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.concurrent.TimeUnit;

import static com.example.onjava.MainActivity.INTERVAL_TIME_SECONDS;

public class MyService extends Service {

    public static String TIME = "TIME";

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

        new Thread(new Runnable() {
            public void run() {
                int i = 0;

                Log.d("LOG", "run" );
                while (!stop) {
                    try {
                        TimeUnit.SECONDS.sleep(INTERVAL_TIME_SECONDS);
                        Log.d("LOG", "try " + i);
                        Intent intent = new Intent(MyReceiver.TIME);
                        intent.putExtra(TIME, i);
                        sendBroadcast(intent);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Log.d("LOG", "while");
                }
                Log.d("LOG", "вышли с цикла");
                stopSelf();
            }
        }).start();
    }
}
