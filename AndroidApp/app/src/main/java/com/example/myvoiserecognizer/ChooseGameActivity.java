package com.example.myvoiserecognizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ChooseGameActivity extends AppCompatActivity {

    Button buttonGame, buttonAssociation, buttonFilms;
    ImageView imageViewAssociation, imageViewTraining, imageViewMain, imageViewHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
        getSupportActionBar().hide();
        imageViewAssociation = findViewById(R.id.imageViewAssociation);
        imageViewTraining = findViewById(R.id.imageViewTraining);
        imageViewMain = findViewById(R.id.imageViewMain);
        imageViewHistory = findViewById(R.id.imageViewHistory);

        imageViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseGameActivity.this, VoiceRecognizer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this, "My score", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}