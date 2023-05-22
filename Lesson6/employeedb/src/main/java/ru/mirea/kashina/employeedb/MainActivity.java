package ru.mirea.kashina.employeedb;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = App.getInstance().getDatabase();
        SuperHeroDao superHeroDao = db.superHeroDao();

        SuperHero superHero = new SuperHero();
        superHero.id = 1;
        superHero.name = "Spider-Man";
        superHero.superpower = 10;
        // запись супергероя в базу
        superHeroDao.insert(superHero);
        // Загрузка всех супергероев
        List<SuperHero> superHeroes = superHeroDao.getAll();
        // Получение определенного супергероя с id = 1
        superHero = superHeroDao.getById(1);
        // Обновление полей объекта
        superHero.superpower = 20;
        superHeroDao.update(superHero);
        Log.d(TAG, superHero.name + " " + superHero.superpower);
    }
}