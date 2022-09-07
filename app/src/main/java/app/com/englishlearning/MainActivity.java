package app.com.englishlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
Boolean status=false;
    EditText ed;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final long ONE_DAY = 24 * 60 * 60 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed=findViewById(R.id.edEmail);
        SharedPreferences saveinfo=getSharedPreferences("savelogin",MODE_PRIVATE);
        status=saveinfo.getBoolean("status",false);
        if(status){
            Intent i = new Intent(MainActivity.this, Dashboard.class);
            startActivity(i);
        }
        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          startup();
            }
        });
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private void startup(){
        if (isEmpty(findViewById(R.id.edEmail))) {
            Toast.makeText(MainActivity.this, "please enter your name", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences saveinfo=getSharedPreferences("savelogin",MODE_PRIVATE);
            SharedPreferences.Editor edit=saveinfo.edit();
            edit.putString("name",ed.getText().toString());
            Date now = new Date();
            String dateString = formatter.format(now);
            Log.d("todayDate",dateString);
            edit.putString("InstallDate", dateString);
            edit.putBoolean("status",true);
            edit.apply();
            Intent i = new Intent(MainActivity.this, Dashboard.class);
            startActivity(i);
        }
    }
}