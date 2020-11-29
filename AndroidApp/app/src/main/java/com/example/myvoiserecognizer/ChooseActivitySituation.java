package com.example.myvoiserecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseActivitySituation extends AppCompatActivity {

    ImageView imageViewAssociation, imageViewTraining, imageViewMain, imageViewHistory, imageViewBread, imageViewRoomInHotel;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_situation);
        getSupportActionBar().hide();


        imageViewAssociation = findViewById(R.id.imageViewAssociation);
        imageViewTraining = findViewById(R.id.imageViewTraining);
        imageViewMain = findViewById(R.id.imageViewMain);
        imageViewHistory = findViewById(R.id.imageViewHistory);
        imageViewBread = findViewById(R.id.imageViewBread);
        imageViewRoomInHotel = findViewById(R.id.imageViewRoomInHotel);

        imageViewRoomInHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivitySituation.this, SituationInHotel.class);
                startActivity(intent);
            }
        });

        imageViewBread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivitySituation.this, SituationInShop.class);
                startActivity(intent);
            }
        });




        imageViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivitySituation.this, MainWindow.class);
                startActivity(intent);
            }
        });

        imageViewAssociation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivitySituation.this,AssociationsActivity.class);
                startActivity(intent);
            }
        });

        imageViewTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivitySituation.this, TrainingActivity.class);
                startActivity(intent);
            }
        });


    }
}