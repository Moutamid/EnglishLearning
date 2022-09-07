package app.com.englishlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LearningChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_choice);
        findViewById(R.id.btnThings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,CommonThingsActivity.class));
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,CommonThingsActivity.class));
            }
        });
        findViewById(R.id.btnCommonWord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,WordsActivity.class));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,CommonThingsActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,CommonThingsActivity.class));
            }
        });
        findViewById(R.id.btnPhrase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,CommonPhrasesActivity.class));
            }
        });

        findViewById(R.id.back_pressed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningChoiceActivity.this,Dashboard.class));
                finish();
            }
        });

    }
}