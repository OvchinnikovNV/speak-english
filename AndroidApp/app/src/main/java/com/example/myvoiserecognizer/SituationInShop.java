package com.example.myvoiserecognizer;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SituationInShop extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH = 100;
    private TextView textViewWhatSayUser;
    private TextView textViewWhatSayBot;
    private ImageButton buttonVoiceInput;
    private Button buttonStart;
    private  TextView textViewListen;

    private Button buttonNextStory;
    private ImageView imageViewDialog;


    TextToSpeech textToSpeech;
    SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;

    int countWordsBot = 0;
    ArrayList<String> wordsForBot = new ArrayList<String>();
    {
        wordsForBot.add("We only have rooms on the first floor.\n" +
                "Do you want to rent it?");
        wordsForBot.add("Oh, i am sorry. I didn't look carefully. We have the last room\nwith a sea view. Is it okay?");
    }

    ArrayList<String> matches;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().hide();
        checkPermission();

        textViewWhatSayUser = findViewById(R.id.textViewWhatSayUser);
        buttonVoiceInput = findViewById(R.id.buttonVoiceInput);
        buttonVoiceInput.setEnabled(false);
        textViewWhatSayBot = findViewById(R.id.textViewWhatSayBot);
        textViewListen = findViewById(R.id.textViewListen);
        buttonStart = findViewById(R.id.buttonStart);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        buttonNextStory = dialog.findViewById(R.id.buttonNextStoryDialog);
        imageViewDialog = dialog.findViewById(R.id.imageViewDialog);
        imageViewDialog.setImageResource(R.drawable.ic_baba);
        buttonNextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setTitle("Award");

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
                matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches != null){
                    textViewWhatSayUser.setText(matches.get(0));

                    if(matches.get(0).contains("room") || matches.get(0).contains("sea") || matches.get(0).contains("rent") || matches.get(0).contains("yes")){
                        textViewWhatSayUser.setTextColor(Color.GREEN);
                        if(countWordsBot < wordsForBot.size()){
                            textViewWhatSayBot.setText(wordsForBot.get(countWordsBot));
                            botSpeak();
                            countWordsBot++;
                        }
                        else{
                            dialog.show();
                        }
                    }
                    else {
                        textViewWhatSayBot.setText("I don't understand. Try again");
                        botSpeak();
                        textViewWhatSayUser.setTextColor(Color.RED);
                    }
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

                botSpeak();
                buttonVoiceInput.setEnabled(true);
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

    private void botSpeak() {
        String say = textViewWhatSayBot.getText().toString();
        int speech = textToSpeech.speak(say,TextToSpeech.QUEUE_FLUSH,null);
        //showToast(say);
    }

    private void showToast(String text){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.textViewToast);
        toastText.setText("some text");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
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