package ru.mirea.kashina.favoritebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView BookView = findViewById(R.id.textView);
            String university = extras.getString(MainActivity.KEY);
            BookView.setText(String.format("Мой любимая книга: %s", university));
        }
    }

    // Отправка введенных пользователем данных по нажатию на кнопку
    public void SendButton(View view) {
            Intent data = new Intent();
            EditText MyBook = findViewById(R.id.EditBook);
            String text = MyBook.getText().toString();
                    data.putExtra(MainActivity.USER_MESSAGE, text);
            setResult(MainActivity.RESULT_OK, data);
            finish();
        }

}