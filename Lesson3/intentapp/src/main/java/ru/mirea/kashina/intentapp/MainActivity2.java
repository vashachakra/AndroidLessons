package ru.mirea.kashina.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private TextView Text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent_app);
        Text1= findViewById(R.id.textView);
        String text = (String) getIntent().getSerializableExtra("key");
        Text1.setText(text);
    }


}