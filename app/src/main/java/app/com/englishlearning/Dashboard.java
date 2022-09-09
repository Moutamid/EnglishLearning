package app.com.englishlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.com.englishlearning.utilities.Utils;


public class Dashboard extends AppCompatActivity {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final long ONE_DAY = 24 * 60 * 60 * 1000;
    Boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        TextView textView = findViewById(R.id.tvName);
        SharedPreferences sharedPreferences = getSharedPreferences("savelogin", MODE_PRIVATE);
        String installDate = sharedPreferences.getString("InstallDate", null);
        Date before = null;
        try {
            before = (Date) formatter.parse(installDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        long diff = now.getTime() - before.getTime();
        long days = diff / ONE_DAY;
        if (days > 3) { // More than 3 days?
            state = true;
        }

        if (Utils.getBoolean("isSubscribed", false)) {
            state = false;
        }

        check();
        textView.setText("Welcome " + sharedPreferences.getString("name", "ali"));
        findViewById(R.id.cardWords).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state) {
                    Toast.makeText(Dashboard.this, "Please subscribe to premium to access all the features", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Dashboard.this, LearningChoiceActivity.class));
                }
            }
        });

        findViewById(R.id.materialCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state) {
                    Toast.makeText(Dashboard.this, "Please subscribe to premium to access all the features", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Dashboard.this, ChattyViewerActivity.class));
                }
            }
        });
        findViewById(R.id.cardPremium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state) {
                    Toast.makeText(Dashboard.this, "Please subscribe to premium to access all the features", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Dashboard.this, SpeakingLessonsActivity.class));
                }
            }
        });
        findViewById(R.id.cardSpokenEnglish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Dashboard.this, PremiumAccessActivity.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);

    }

    public void check() {
        ImageView lock, learning, speaking;
        lock = findViewById(R.id.imgChatLock);
        learning = findViewById(R.id.imgLearnLock);
        speaking = findViewById(R.id.imgSpeakingLock);
        if (state) {
            lock.setVisibility(View.VISIBLE);
            learning.setVisibility(View.VISIBLE);
            speaking.setVisibility(View.VISIBLE);
        }
    }
}