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
    public int onStartCommand(Intent intent, int flags, int startId) {
        // run the method to start the thread
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

    // This method implements a thread and sends the intent with the data to the Broadcast
    private void setTime() {
        new Thread(new Runnable() {
            public void run() {
                int i = 0;

                while (!stop) {
                    try {
                        TimeUnit.SECONDS.sleep(INTERVAL_TIME_SECONDS);
                        Log.d("LOG", "try " + i);
                        Intent intent = new Intent(MainActivity.TIME);
                        intent.putExtra(TIME, i);
                        sendBroadcast(intent);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
    }
}
