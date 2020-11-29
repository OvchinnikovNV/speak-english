package com.example.myvoiserecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TrainingActivity extends AppCompatActivity {

    private ImageView imageViewTrainMainWindow;
    private TextView textViewTrain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);



    }
}