package com.example.myvoiserecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AssociationsActivity extends AppCompatActivity {

    private TextView textViewAssociation;
    private ImageView buttonStartAssociation;
    private TextView textViewGuessAssociation, textViewHintForButton;

    private String url = "https://72c3e254902d.ngrok.io/";
    private String postBodyString;
    private MediaType mediaType;
    private RequestBody requestBody;

    String answer;


    ArrayList<String> matches;


    TextToSpeech textToSpeech;
    SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;

    ImageView imageViewAssociation, imageViewTraining, imageViewMain, imageViewHistory, imageViewGuess, imageView4;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associations);
        getSupportActionBar().hide();

        buttonStartAssociation = findViewById(R.id.buttonRefreshAssociation);
        imageViewGuess = findViewById(R.id.imageViewGuess);

        imageViewAssociation = findViewById(R.id.imageViewAssociation);
        imageViewTraining = findViewById(R.id.imageViewTraining);
        imageViewMain = findViewById(R.id.imageViewMain);
        imageViewHistory = findViewById(R.id.imageViewHistory);





        imageViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssociationsActivity.this, MainWindow.class);
                startActivity(intent);
            }
        });

        imageViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssociationsActivity.this,ChooseActivitySituation.class);
                startActivity(intent);
            }
        });

        imageViewTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssociationsActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        //postRequest("huiiil", url);
        textViewAssociation = findViewById(R.id.textViewAssociation);
        buttonStartAssociation = findViewById(R.id.buttonRefreshAssociation);
        textViewGuessAssociation = findViewById(R.id.textViewGuessAssociation);
        textViewHintForButton = findViewById(R.id.textViewHintForButton);
        imageView4 = findViewById(R.id.imageView4);


        buttonStartAssociation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String guess = editTextGuessAssociation.getText().toString();
                textViewGuessAssociation.setText("");
                postRequest("",url);
                textViewHintForButton.setVisibility(View.INVISIBLE);
                imageView4.setVisibility(View.INVISIBLE);

            }
        });

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

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
                    textViewGuessAssociation.setText(matches.get(0));
                    postRequest(matches.get(0),url);



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


        imageViewGuess.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:

                        speechRecognizer.stopListening();

                        break;
                    case MotionEvent.ACTION_DOWN:
                       // textViewListen.setText("Listening");
                        Toast.makeText(AssociationsActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                        speechRecognizer.startListening(speechRecognizerIntent);
                        break;
                }
                return false;
            }
        });

    }

    private RequestBody buildRequestBody(String msg) {
        postBodyString = msg;
        mediaType = MediaType.parse("text/plain");
        requestBody = RequestBody.create(postBodyString, mediaType);
        return requestBody;
    }

    private void postRequest(String message, String URL) {
        final RequestBody requestBody = buildRequestBody(message);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .post(requestBody)
                .url(URL)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AssociationsActivity.this, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        call.cancel();

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            answer = response.body().string();

                            //Toast.makeText(AssociationsActivity.this, answer, Toast.LENGTH_SHORT).show();


                            if(answer.equals("0") || answer.equals("1")){
                                //String[] stroka = matches.get(0).split(" ");
                                if(answer.equals("1")){
                                    textViewGuessAssociation.setTextColor(Color.GREEN);
                                }if(answer.equals("0")){
                                    textViewGuessAssociation.setTextColor(Color.RED);
                                }
                            }
                            else{
                                textViewAssociation.setText(answer);
                            }





                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}