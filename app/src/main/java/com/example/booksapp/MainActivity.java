package com.example.booksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgress= (ProgressBar) findViewById(R.id.progressBar);
        try{
            URL bookURL = ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(bookURL);
        }
       catch (Exception e){
            Log.d("Error", e.getMessage());
       }
    }
    public class BooksQueryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result= null;
            try{
                result=ApiUtil.getJson((searchURL));
            }
            catch (IOException e){
                Log.e("error" ,e.getMessage() );
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tvResult= (TextView) findViewById(R.id.tvResponse);
            TextView tvError= (TextView) findViewById(R.id.tv_error);
            if(result==null){
                tvResult.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);

            }
            else {
                tvResult.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            mLoadingProgress.setVisibility(View.INVISIBLE);
            tvResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}