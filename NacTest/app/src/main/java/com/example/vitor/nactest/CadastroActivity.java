package com.example.vitor.nactest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONStringer;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtCadastro;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        edtCadastro = (EditText) findViewById(R.id.edtCategoria);
    }

    public void cadastrar(View view){
        CadastroTask task = new CadastroTask();
        task.execute(
                edtCadastro.getText().toString(),
                "2017-08-24T01:29:18+0000"
        );
    }

    private class CadastroTask extends AsyncTask<String, Void, Integer>{

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(CadastroActivity.this, "Aguarde", "Processando req");
        }

        @Override
        protected Integer doInBackground(String... params) {
            try{
                URL url = new URL("http://10.0.2.2:8000/categoria/");
                HttpURLConnection conn = (HttpURLConnection)  url.openConnection();

                String tokenAuth = "d7c672b6e88c030f94713afdddcec98461910d9f";

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Token "
                        + tokenAuth);

                JSONStringer json = new JSONStringer().object();
                json.key("name").value(params[0]);
                json.key("created").value(params[1]);
                json.endObject();

                OutputStreamWriter output = new OutputStreamWriter(conn.getOutputStream());
                output.write(json.toString());

                output.close();

                return conn.getResponseCode();

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer inte) {
            progress.dismiss();
            if(inte == 201){
                Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CadastroActivity.this, "Erro!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
