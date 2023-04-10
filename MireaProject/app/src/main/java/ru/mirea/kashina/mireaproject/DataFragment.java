package ru.mirea.kashina.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        TextView textView = view.findViewById(R.id.data_text);
        textView.setText("Уникальная информация об отрасли Машинное обучение " +
                " технология искусственного интеллекта," +
                        " которая предоставляет вычислительным системам возможность автоматически учиться и совершенствоваться на основе опыта\n" +
                "\n" +
                        "Зачем?"
                +
                "\n" +
                "Этот продвинутый метод обработки данных позволяет решать следующие задачи:\n" +
                "\n" +
                " > регрессия и прогнозирование\n" +
                " > классификация\n" +
                " > кластеризация\n" +
                " > снижение размерности\n" +
                " > выявление аномалий");
        int primaryColor = getResources().getColor(R.color.primary_color);
        int backgroundColor = getResources().getColor(R.color.purple_200);
        textView.setTextColor(primaryColor);
        view.setBackgroundColor(backgroundColor);

        int padding = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        textView.setPadding(padding, padding, padding, padding);
        return view;
    }
}