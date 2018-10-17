package com.example.lukas.homework04;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImage.myBitmap {

    Bitmap triviaImage;
    ImageView showImage;
    int qNum = 0;
    ArrayList<Questions> questionList = new ArrayList<Questions>();
    int correctNum = 0;
    int counter = -1;

    TextView theQuestion;
    TextView theQuestionNumber;
    TextView timer;
    RadioGroup theAnswerGroup;
    Button btnExit; Button btnNext;

    static String CORRECT_KEY = "CORRECT";
    static String TOTAL_KEY = "TOTAL";
    static String TRIVIAQS = "TRIVIA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        showImage = findViewById(R.id.TriviaImage);
        btnExit = findViewById(R.id.btnTriviaExit);
        btnNext = findViewById(R.id.btnTriviaNext);
        theQuestionNumber = findViewById(R.id.textQuestionNumber);
        theQuestion = findViewById(R.id.textTheQuestion);
        theAnswerGroup = findViewById(R.id.radGroupAnswers);
        timer = findViewById(R.id.textTimer);

        questionList = (ArrayList<Questions>) getIntent().getSerializableExtra(TRIVIAQS);

        showNextQuestion();
        //qNum = questionList.get(counter).questionNumber + 1;
        //new GetImage(TriviaActivity.this).execute(questionList.get(counter).url);
        //theQuestionNumber.setText("Q" + qNum);
        //theQuestion.setText(questionList.get(counter).theQuestion);

        //countdownTimer
        new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long l) {
                int secondsLeft = (int) (l / 1000);
                timer.setText("Time: " + secondsLeft + " seconds");
            }

            @Override
            public void onFinish() {
                goStats();

            }
        }.start();



        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exiting = new Intent(TriviaActivity.this, MainActivity.class);
                startActivity(exiting);
                finish();

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != -1){
                    checkAnswer();
                }

                if (counter < questionList.size() - 1) {
                    showNextQuestion();
                }
                else {
                    goStats();
                }
            }
        });


    }

    public void handleData(Bitmap myBit) {
        triviaImage = myBit;
        showImage.setImageBitmap(triviaImage);
    }

    public void showNextQuestion(){
        counter++;

        qNum = questionList.get(counter).questionNumber + 1;

        if (questionList.get(counter).url != "") {
            new GetImage(TriviaActivity.this).execute(questionList.get(counter).url);
        }
        else
        {
            showImage.setImageResource(android.R.color.transparent);
        }
        theQuestionNumber.setText("Q" + qNum);
        theQuestion.setText(questionList.get(counter).theQuestion);

        for(int i = 0; i < questionList.get(counter).answerChoices.size(); i++) {
            RadioButton anAnswer = new RadioButton(this);
            anAnswer.setText(questionList.get(counter).answerChoices.get(i));
            anAnswer.setId(i);
            theAnswerGroup.addView(anAnswer);
        }



    }

    public void checkAnswer() {
        if (theAnswerGroup.getCheckedRadioButtonId() == questionList.get(counter).correctIndex)
        {
            correctNum++;
        }

        theAnswerGroup.removeAllViews();
        theAnswerGroup.clearCheck();

    }

    public void goStats(){
        Intent stat = new Intent(TriviaActivity.this, StatsActivity.class);
        stat.putExtra(CORRECT_KEY, correctNum);
        stat.putExtra(TOTAL_KEY, questionList.size());
        stat.putExtra(TRIVIAQS, questionList);
        startActivity(stat);
        finish();
    }
}
