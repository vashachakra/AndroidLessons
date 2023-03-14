package ru.mirea.kashina.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

     public void onClickNewActivity(View view) {
       Intent intent = new Intent(this, SecondActivity.class);
       intent.putExtra("key", "MIREA - КАШИНА АНАСТАСИЯ СЕРГЕЕВНА");
       startActivity(intent);
   }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "OnDestroy");
    }
}