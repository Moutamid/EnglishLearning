package app.com.englishlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PremiumAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_access);
        findViewById(R.id.btnBasic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PremiumAccessActivity.this,LearningChoiceActivity.class));
            }
        });
        findViewById(R.id.btnPrenium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PremiumAccessActivity.this,LearningChoiceActivity.class));
            }
        });
        findViewById(R.id.imgHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PremiumAccessActivity.this, Dashboard.class));
            }
        });
    }
}