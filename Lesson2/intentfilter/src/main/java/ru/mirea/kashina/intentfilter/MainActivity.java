package ru.mirea.kashina.intentfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBrowserClick(android.view.View view){
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://priem.mirea.ru/first-degree/list"));
        startActivity(openLinkIntent);
    }

    public void onDataTransmission(android.view.View view){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Кашина Анастасия Сергеевна");
        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"));
    }
}