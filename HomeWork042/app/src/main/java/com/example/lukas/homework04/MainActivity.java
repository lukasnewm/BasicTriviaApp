package com.example.lukas.homework04;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

//Made by: Lukas Newman

public class MainActivity extends AppCompatActivity implements GetQuestions.theQuestions{

    Button btnExit;
    Button btnStart;
    ImageView spashImage;
    ArrayList<Questions> triviaQuestions = new ArrayList<>();
    static String TRIVIAQS = "TRIVIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spashImage = findViewById(R.id.imageView);
        spashImage.setVisibility(View.INVISIBLE);
        btnExit = findViewById(R.id.btnMainExit);
        btnStart = findViewById(R.id.btnMainStart);
        btnStart.setEnabled(false);

        if (isConnected())
        {
            new GetQuestions(MainActivity.this).execute();
            spashImage.setVisibility(View.VISIBLE);
            btnStart.setEnabled(true);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goToTrivia = new Intent(MainActivity.this, TriviaActivity.class);
                    goToTrivia.putExtra(TRIVIAQS, triviaQuestions);
                    startActivity(goToTrivia);
                    finish();
                }
            });

        }
        else
        {

        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }

    public void theData(ArrayList<Questions> myQuestions){
        triviaQuestions = myQuestions;
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

}
