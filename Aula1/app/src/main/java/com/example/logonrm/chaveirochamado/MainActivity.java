package com.example.logonrm.chaveirochamado;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONStringer;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText txtCadastra;
    private Spinner sppiner;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCadastra = (EditText) findViewById(R.id.txtCadastra);
        sppiner = (Spinner) findViewById(R.id.spinner);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Valida o item de menu escolhido
        if (item.getItemId() == R.id.menu_cadastrar){
            //Navegar de tela
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.menu_listar){
            //Navega para a tela de listagem
            Intent intent = new Intent(this,PesquisaActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para o clique do botão
    public void cadastrar(View view){
        CadastroTask task = new CadastroTask();
        boolean checkBoxx = checkBox.isSelected();
        String ok = "true";

        int posicao = sppiner.getSelectedItemPosition();

        task.execute(
            txtCadastra.getText().toString(),
            sppiner.getItemAtPosition(posicao).toString(),
            ok
        );
    }

    private class CadastroTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected void onPostExecute(Integer integer) {
            //201 - HTTP code CREATED
            if (integer == 201){
                Toast.makeText(MainActivity.this,"Sucesso!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,"Erro",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this,"Sucesso!",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL("http://10.20.63.61:8080/AssistenciaApp/rest/chamado/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONStringer json = new JSONStringer().object();
                json.key("codigoFuncionario").value(params[0]);
                json.key("data").value("29/08/2017");
                json.key("finalizado").value(params[2]);
                json.key("descricao").value(params[1]);

                OutputStreamWriter stream = new OutputStreamWriter(connection.getOutputStream());
                stream.write(json.toString());
                stream.close();

                return connection.getResponseCode();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
