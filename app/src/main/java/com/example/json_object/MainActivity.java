package com.example.json_object;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView m1_txt1,m1_txt2;
    private void AnhXa(){
        m1_txt1=(TextView) findViewById(R.id.m1_txt1);
        m1_txt2=(TextView) findViewById(R.id.m1_txt2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        new ReadJSONObject().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json");
        new ReadJSONArray().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json");
    }
    private class ReadJSONObject extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line ="";
                while((line=bufferedReader.readLine())!=null){
                    content.append(line+"\n");
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject object = new JSONObject(s);
                String monhoc = object.getString("monhoc");
                String noihoc = object.getString("noihoc");
                URL url = new URL(object.getString("website"));
               m1_txt1.setText(monhoc+"\n"+noihoc+"\n"+url);
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }
    private class ReadJSONArray extends AsyncTask<String, Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line ="";
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            StringBuilder string = new StringBuilder();
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("danhsach");
                for(int i=0;i<array.length();i++){
                    JSONObject objectDS = array.getJSONObject(i);
                    String name = objectDS.getString("khoahoc");
                    string.append(name+"\n");
                }
                m1_txt2.setText(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}