package com.example.lukas.homework04;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImage extends AsyncTask<String, Void, Void> {
    Bitmap bitmap = null;
    myBitmap bitmaper;
    ProgressDialog myProgress;

    public GetImage(TriviaActivity activity){
        this.bitmaper = activity;
        myProgress = new ProgressDialog(activity);
        myProgress.setMessage("Loading Image");
        myProgress.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        bitmaper.handleData(bitmap);
        myProgress.dismiss();
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection connection = null;
        bitmap = null;
        try {
            URL url = new URL(params[0]);
            Log.d("demo", params[0].toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (IOException e) {

            //Handle the exceptions
        } finally {
            //Close open connection
            connection.disconnect();

        }
        return null;
    }

    public static interface myBitmap{
        public void handleData(Bitmap myBit);
    }
}
