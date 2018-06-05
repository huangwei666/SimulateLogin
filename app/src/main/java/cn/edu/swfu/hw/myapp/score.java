package cn.edu.swfu.hw.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class score extends AppCompatActivity {
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = (TextView) findViewById(R.id.score);
        Intent score1 = this.getIntent();
        String scorehtml= score1.getStringExtra("name");
        score.setText(scorehtml);
    }
}
