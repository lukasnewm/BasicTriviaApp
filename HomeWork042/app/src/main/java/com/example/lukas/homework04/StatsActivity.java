package com.example.lukas.homework04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    TextView correctPercent;
    ProgressBar percentBar;
    Button btnExit; Button btnTryAgain;
    int correct = 0; int total = 1; int percent = 0;

    static String CORRECT_KEY = "CORRECT";
    static String TOTAL_KEY = "TOTAL";
    static String TRIVIAQS = "TRIVIA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        correctPercent = findViewById(R.id.textPercentage);
        percentBar = findViewById(R.id.progressBar);
        btnExit = findViewById(R.id.btnStatsExit);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        final ArrayList<Questions> temp = (ArrayList<Questions>)getIntent().getSerializableExtra(TRIVIAQS);

        //Get correct and total from the Intent
        correct = getIntent().getIntExtra(CORRECT_KEY, 0);
        total = getIntent().getIntExtra(TOTAL_KEY, 0);
        Log.d("demo", Integer.toString(correct));
        Log.d("demo", Integer.toString(total));


        percent = (100 * correct) / total;

        correctPercent.setText(percent + "%");
        percentBar.setProgress(percent);


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMain = new Intent(StatsActivity.this, MainActivity.class);
                startActivity(goToMain);
                finish();
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToTrivia = new Intent(StatsActivity.this, TriviaActivity.class);
                goToTrivia.putExtra(TRIVIAQS, temp);
                startActivity(goToTrivia);
                finish();
            }
        });


    }
}
