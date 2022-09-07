package app.com.englishlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.com.englishlearning.adapter.WordsAdapter;
import app.com.englishlearning.model.ImgUrlModel;
import app.com.englishlearning.model.WordsModel;
import app.com.englishlearning.network.APIClient;
import app.com.englishlearning.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordsActivity extends AppCompatActivity {
    List<WordsModel> models;
    WordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        RecyclerView recyclerView = findViewById(R.id.rec);
        models = new ArrayList<>();
        setData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemViewCacheSize(10);
        adapter = new WordsAdapter(getApplicationContext(), models);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btnQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WordsActivity.this, QuizActivity.class));
            }
        });
        findViewById(R.id.imgHomes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WordsActivity.this, Dashboard.class));
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
                getUrls(name);

            }
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void getUrls(String name) {

        WordsModel data = new WordsModel("dfaa", name);
        models.add(data);

    }


}