package com.example.vitor.nactest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListarActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        listView = (ListView) findViewById(R.id.listView);
        ListarTask task = new ListarTask();
        task.execute();
    }

    private class ListarTask extends AsyncTask<Void, Void, String>{
        private ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ListarActivity.this, "Aguarde", "Buscando Itens no Servidor");
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL("http://10.0.2.2:8000/categoria/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                String tokenAuth = "d7c672b6e88c030f94713afdddcec98461910d9f";

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Token "
                        + tokenAuth);

                if(conn.getResponseCode() == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String linha;

                    StringBuilder builder = new StringBuilder();

                    while((linha = reader.readLine()) != null){
                        builder.append(linha);
                    }
                    conn.disconnect();
                    return builder.toString();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            try{
                JSONObject json = new JSONObject(s);
                JSONArray array = json.getJSONArray("results");

                List<String> lista = new ArrayList<>();

                for (int i = 0; i< array.length(); i++){
                    JSONObject item = (JSONObject) array.get(i);
                    lista.add(item.getString("name"));
                }

                ListAdapter listAdapter = new ArrayAdapter(ListarActivity.this, android.R.layout.simple_list_item_1, lista);
                listView.setAdapter(listAdapter);
            } catch (JSONException e){
                Toast.makeText(ListarActivity.this,
                        "Erro",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
