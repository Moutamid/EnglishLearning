package app.com.englishlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import app.com.englishlearning.databinding.ActivitySpeakingLessonsBinding;
import app.com.englishlearning.model.WordsModel;

public class SpeakingLessonsActivity extends AppCompatActivity {
    ActivitySpeakingLessonsBinding binding;
    List<WordsModel> models;
    int status = 1;
    TextToSpeech textToSpeech;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpeakingLessonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        models = new ArrayList<>();
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if (i != TextToSpeech.ERROR) {
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        setData();
        reload();
        onClick();
    }

    public void setData() {
        InputStream input;

        AssetManager assetManager = getAssets();
        try {
            input = assetManager.open("phrases.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            // byte buffer into a string
            String text = new String(buffer);
            String[] lines = text.split("\r?\n|\r");
            for (String name : lines) {
                WordsModel data = new WordsModel("urls", name);
                models.add(data);

            }
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void reload() {
        int size = models.size();
        Random i = new Random();
        int number = i.nextInt(size);
        name = models.get(number).getTitle();
        binding.cardMicrophone.setVisibility(View.VISIBLE);
        binding.cardLoading.setVisibility(View.INVISIBLE);
        binding.include.showWon.setVisibility(View.INVISIBLE);
        binding.tvPhrase.setText(name);
        binding.imgWord2.setVisibility(View.INVISIBLE);

    }

    public void record() {
        binding.cardMicrophone.setVisibility(View.INVISIBLE);
        binding.imgWord2.setVisibility(View.VISIBLE);
    }

    public void onClick() {
        binding.cardMicrophone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record();
            }
        });
        binding.cardSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    textToSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        binding.include.okayBtnWorkDoneDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload();
            }
        });

        binding.imgWord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        result();
                    }
                }, 5000);


            }
        });
        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SpeakingLessonsActivity.this, Dashboard.class));

            }
        });
    }

    public void loading() {
        binding.cardLoading.setVisibility(View.VISIBLE);
        binding.cardMicrophone.setVisibility(View.INVISIBLE);
        binding.imgWord2.setVisibility(View.INVISIBLE);
    }

    public void result() {
        binding.include.descWorkDoneDialog.setText("Good Work You spoke Correctly");
        binding.include.showWon.setVisibility(View.VISIBLE);
        binding.cardMicrophone.setVisibility(View.INVISIBLE);
        binding.imgWord2.setVisibility(View.INVISIBLE);
        binding.cardLoading.setVisibility(View.INVISIBLE);
    }


}