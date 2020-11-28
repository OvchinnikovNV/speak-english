package com.example.myvoiserecognizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class VoiceRecognizer extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH = 100;
    private TextView textViewWhatSayUser;
    private TextView textViewWhatSayBot;
    private ImageButton buttonVoiceInput;
    private Button buttonStart;
    private  TextView textViewListen;

    private Button buttonOk;
    private ImageView imageViewDialog;


    TextToSpeech textToSpeech;
    SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        checkPermission();

        textViewWhatSayUser = findViewById(R.id.textViewWhatSayUser);
        buttonVoiceInput = findViewById(R.id.buttonVoiceInput);
        textViewWhatSayBot = findViewById(R.id.textViewWhatSayBot);
        textViewListen = findViewById(R.id.textViewListen);
        buttonStart = findViewById(R.id.buttonStart);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        buttonOk = dialog.findViewById(R.id.buttonOk);
        imageViewDialog = dialog.findViewById(R.id.imageViewDialog);
        imageViewDialog.setImageResource(R.drawable.ic_baba);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.setTitle("Award");


//        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//
//
//        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
//                Locale.getDefault());

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches.get(0).contains("bread") && matches.get(0).contains("buy")){
                    textViewWhatSayUser.setTextColor(Color.GREEN);
                    dialog.show();
                }
                else {
                    textViewWhatSayUser.setTextColor(Color.RED);

                }

                if(matches != null){
                    textViewWhatSayUser.setText(matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i == TextToSpeech.SUCCESS){
                            int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                        }
                    }
                });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String say = textViewWhatSayBot.getText().toString();
                int speech = textToSpeech.speak(say,TextToSpeech.QUEUE_FLUSH,null);
            }
        });


        buttonVoiceInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        textViewListen.setText("Press to record");
                        speechRecognizer.stopListening();

                        break;
                    case MotionEvent.ACTION_DOWN:
                        textViewListen.setText("Listening");
                        speechRecognizer.startListening(speechRecognizerIntent);
                        break;
                }

                return false;
            }
        });

    }



    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak something =)");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getApplicationContext().getPackageName());
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //String whatSayUser = textViewWhatSayUser.getText().toString();
                    textViewWhatSayUser.setText(result.get(0));
                   String[] array =  result.get(0).split(" ");
                   ArrayList<String> words = new ArrayList<>(Arrays.asList(array));
                    if(words.contains("ok")){
                        Toast.makeText(this, "You say good", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(this, "LOLO", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}