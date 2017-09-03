package com.example.vitor.nactest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText edtCodigo;
    private TextView resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resposta = (TextView) findViewById(R.id.txtResposta);
        edtCodigo = (EditText) findViewById(R.id.edtCodigo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuCadastro){
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuListar){
            Intent intent = new Intent(this, ListarActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscar (View view){
        BuscaTask task = new BuscaTask();
        int codigo = Integer.parseInt(edtCodigo.getText().toString());

        task.execute(codigo);
    }


    private class BuscaTask extends AsyncTask<Integer, Void, String>{
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, "Aguarde", "Buscando Dados");
        }

        @Override
        protected String doInBackground(Integer... params) {
            try{
                URL url = new URL("http://10.0.2.2:8000/categoria/"+params[0]+"/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                String tokenAuth = "d7c672b6e88c030f94713afdddcec98461910d9f";

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Token "
                        + tokenAuth);

                if (conn.getResponseCode() == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder json = new StringBuilder();
                    String linha;
                    //Ler todas as linhas retornadas pelo ws
                    while ((linha = reader.readLine()) != null) {
                        //Adiciona cada linha no builder
                        json.append(linha);
                    }
                    conn.disconnect();
                    return json.toString();
                }


            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            if (s != null){
                try{
                    JSONObject json = new JSONObject(s);
                    String categoria = json.getString("name");

                    resposta.setText("Categoria: "+categoria);

                }catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(MainActivity.this, "Resposta nula", Toast.LENGTH_LONG).show();
            }

        }
    }
}
