package com.root.jefersonguido.lavacar.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.root.jefersonguido.lavacar.Adapters.Carro;
import com.root.jefersonguido.lavacar.Adapters.DBAdapter;
import com.root.jefersonguido.lavacar.Adapters.Singleton;
import com.root.jefersonguido.lavacar.R;

import java.util.ArrayList;

public class NovoCarroActivity extends Activity{
    private EditText nome, telefone, placa, cor, modelo;
    private Button cadastrar;
    private DBAdapter datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_carro);

        iniciaObjetos();
        cadastraCarro();

        }

    public void iniciaObjetos(){
        nome = (EditText) findViewById(R.id.edt_nome);
        telefone = (EditText) findViewById(R.id.edt_telefone);
        placa = (EditText) findViewById(R.id.edt_placa);
        cor = (EditText) findViewById(R.id.edt_cor);
        modelo = (EditText) findViewById(R.id.edt_modelo);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);
    }

    // Método para cadastrar novo carro no Banco de Dados
    public void cadastraCarro(){
        datasource = new DBAdapter(this); // Inicia o Banco

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.open(); // Abre o banco de dados

                // Adiciona o carro
                Carro carro = datasource.createCarro(nome.getText().toString(), telefone.getText().toString(), placa.getText().toString(), cor.getText().toString(), modelo.getText().toString());

                datasource.close(); // Fecha o banco de dados

                // Cria mensagem para o novo carro
                AlertDialog.Builder dialogo = new AlertDialog.Builder(NovoCarroActivity.this);
                dialogo.setTitle("Aviso");
                dialogo.setMessage("Proprietário: " + carro.getNome());
                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                dialogo.show(); // Mostra o dialogo
            }
        });
    }
}
