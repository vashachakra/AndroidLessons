package ru.mirea.kashina.thread;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Arrays;
import ru.mirea.kashina.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО - 17-20, НОМЕР ПО СПИСКУ: 9, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Побег из Шоушенка");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(),	"Stack:	"	+	Arrays.toString(mainThread.getStackTrace()));
    }
    public void onClick(View view) {
        Runnable runnable = new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.i("ThreadProject", "Запущен поток № " + numberThread);
                long endTime = System.currentTimeMillis()
                        + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime -
                                    System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public void onClickCountPairs (View view) {
        Runnable runnable = () -> {
            EditText editText = findViewById(R.id.editTextPairs);
            float pairs = Float.parseFloat(editText.getText().toString());
            EditText editText2 = findViewById(R.id.editTextDays);
            float days = Float.parseFloat(editText2.getText().toString());
            TextView textView = findViewById(R.id.pairEncounter);
            textView.setText("Среднее количество пар в месяц : " + pairs / days);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}