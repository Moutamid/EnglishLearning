package app.com.englishlearning;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import app.com.englishlearning.adapter.QuizAdapter;
import app.com.englishlearning.adapter.WordsAdapter;
import app.com.englishlearning.databinding.ActivityQuizBinding;
import app.com.englishlearning.model.ImgUrlModel;
import app.com.englishlearning.model.WordsModel;
import app.com.englishlearning.network.APIClient;
import app.com.englishlearning.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    List<WordsModel> models;
    ActivityQuizBinding binding;
    QuizAdapter adapter;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        models = new ArrayList<>();
        setData();
        Reload();
        int size = models.size();
        Random i = new Random();
        int number = i.nextInt(size);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        binding.imgHomeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, Dashboard.class));
            }
        });
    }

    public void setData() {
        InputStream input;

        AssetManager assetManager = getAssets();
        try {
            input = assetManager.open("things.txt");
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

    public void setPic(String name) {
        final String[] url = {""};
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        apiService.Register("25707240-9c632bb3ee0bdb5ae65d78d7d", name, "photo", true).enqueue(new Callback<ImgUrlModel>() {
            @Override
            public void onResponse(Call<ImgUrlModel> call, Response<ImgUrlModel> response) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(response.body().getHits().get(0).getPreviewURL())
                        .apply(new RequestOptions()
                                .placeholder(R.color.lighterGrey)
                                .error(R.color.lighterGrey)
                        )
                        .diskCacheStrategy(AUTOMATIC)
                        .into(binding.imgWord);
            }

            @Override
            public void onFailure(Call<ImgUrlModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "bad", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void Reload() {
        binding.cardSpeaker.setVisibility(View.VISIBLE);
        binding.imgWord.setVisibility(View.VISIBLE);
        int size = models.size();
        Random i = new Random();
        int number = i.nextInt(size);
        String name = models.get(number).getTitle();
        setPic(name);
        List<WordsModel> options = new ArrayList<>();
        WordsModel option1 = new WordsModel("faf", models.get(i.nextInt(size)).getTitle());
        WordsModel option2 = new WordsModel("faf", models.get(i.nextInt(size)).getTitle());
        WordsModel option3 = new WordsModel("faf", models.get(i.nextInt(size)).getTitle());
        WordsModel option4 = new WordsModel("faf", name);
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
        binding.optionrec.setLayoutManager(new LinearLayoutManager(this));
        Collections.shuffle(options);
        adapter = new QuizAdapter(getApplicationContext(), options);
        binding.optionrec.setAdapter(adapter);
        adapter.setOnItemClick(new QuizAdapter.OnitemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (options.get(position).getTitle().equals(name)) {
                    binding.lose.showLose.setVisibility(View.INVISIBLE);
                    binding.imgWord.setImageResource(R.drawable.loading);
                    binding.include.showWon.setVisibility(View.VISIBLE);
                    binding.cardSpeaker.setVisibility(View.INVISIBLE);
                    binding.imgWord.setVisibility(View.INVISIBLE);
                } else {
                    binding.lose.showLose.setVisibility(View.VISIBLE);
                    binding.include.showWon.setVisibility(View.INVISIBLE);
                    binding.imgWord.setImageResource(R.drawable.loading);
                    binding.lose.tvResult.setText(name+" was the correct Answer");
                    binding.cardSpeaker.setVisibility(View.INVISIBLE);
                    binding.imgWord.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onaddclick(int position) {

            }
        });
        binding.lose.okayBtnOfflineDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.lose.showLose.setVisibility(View.INVISIBLE);
                Reload();
            }
        });
        binding.include.okayBtnWorkDoneDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.include.showWon.setVisibility(View.INVISIBLE);
                Reload();
            }
        });
        binding.cardSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(name,TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    textToSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void Restart() {
        startActivity(new Intent(QuizActivity.this, QuizActivity.class));
        finish();
    }

    public void Home() {
        startActivity(new Intent(QuizActivity.this, Dashboard.class));
        finish();
    }
}