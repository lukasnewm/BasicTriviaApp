package com.example.lukas.homework04;

import java.io.Serializable;
import java.util.ArrayList;

public class Questions implements Serializable {

    String url;
    String theQuestion;
    ArrayList<String> answerChoices;
    int correctIndex;
    int questionNumber;

    public Questions(String url, String theQuestion, ArrayList<String> answerChoices, int correctIndex, int questionNumber) {
        this.url = url;
        this.theQuestion = theQuestion;
        this.answerChoices = answerChoices;
        this.correctIndex = correctIndex;
        this.questionNumber = questionNumber;
    }
}
