package com.example.onjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, stop;
    public static TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        text = findViewById(R.id.text);


        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    public static void newText(String s){
        text.setText(s);
    }



    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.start:
                //this.text.setText("сервис запущен");
                //Log.d("LOG", "text из MainActivity " + text.getText());
                startService(new Intent(this, MyService.class));

                break;
            case R.id.stop:
                //text.setText("Нажата кнопка Button2");
                stopService(new Intent(this, MyService.class));
                break;
        }
    }

}
