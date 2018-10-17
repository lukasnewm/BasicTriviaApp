package com.example.lukas.homework04;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetQuestions extends AsyncTask<Void, Void, String> {
    ProgressDialog progDia;
    ArrayList<Questions> myQuestions = new ArrayList<>();
    theQuestions myQs;

    public GetQuestions(MainActivity activity){
        this.myQs = activity;
        progDia = new ProgressDialog(activity);
        progDia.setMessage("Loading Trivia");
        progDia.show();
    }

    @Override
    protected void onPostExecute(String s) {
        myQs.theData(myQuestions);
        progDia.dismiss();
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;

        URL url = null;
        try {
            url = new URL( "http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    String[] fullQuestion = line.split(";");
                    makeQuestions(fullQuestion);
                }
                result = stringBuilder.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static interface theQuestions{
        public void theData(ArrayList<Questions> myQuestions);
    }

    public void makeQuestions(String[] fullQuestion){

        Questions newQuestion;
        String questNum = fullQuestion[0];
        String theQuestion = fullQuestion[1];
        String theURL = fullQuestion[2];
        ArrayList<String> answers = new ArrayList<>();

        for (int i = 3; i < fullQuestion.length - 1; i++)
        {
            answers.add(fullQuestion[i]);
        }
        String correctIndex = fullQuestion[fullQuestion.length - 1];

        newQuestion = new Questions(theURL, theQuestion, answers, Integer.parseInt(correctIndex), Integer.parseInt(questNum));
        myQuestions.add(newQuestion);

    }
}
