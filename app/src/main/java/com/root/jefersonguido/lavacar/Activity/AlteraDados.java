package com.root.jefersonguido.lavacar.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.root.jefersonguido.lavacar.Adapters.Carro;
import com.root.jefersonguido.lavacar.Adapters.DBAdapter;
import com.root.jefersonguido.lavacar.R;

public class AlteraDados extends AppCompatActivity {
    private TextView nome;
    private EditText telefone;
    private Button alterar;
    private DBAdapter datasource;
    private Carro carro = new Carro();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_dados);

        iniciaObjetos();
        alteraCarro();

    }

    public void iniciaObjetos(){
        nome = (TextView) findViewById(R.id.altera_nome);
        telefone = (EditText) findViewById(R.id.altera_telefone);
        alterar = (Button) findViewById(R.id.btn_alterar);
    }

    // Método para alterar um carro no Banco de Dados
    public void alteraCarro(){
        datasource = new DBAdapter(this); // Inicia o Banco

        Intent intent = getIntent(); // Faz a captura dos itens da intent que executou a Activity
        final long idCarro = intent.getLongExtra("IDDETALHESCARRO", 1); // Recebe o código do carro selecionado na activity DetalhesActivity

        datasource.open(); // Abre o banco

        carro = datasource.getCarro(idCarro); // Captura o carro selecionado do banco de dados

        // Debug do ID do banco recebido
        //Toast.makeText(getApplicationContext(), "id: " + carro.get_id(),Toast.LENGTH_SHORT); // Exibe o Código selecionado, Debug Test

        // Muda os textos dos objetos TextView
        nome.setText(""+carro.getNome());
        telefone.setText(""+carro.getTelefone());
        alterar.setText("Alterar");

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.open(); // Abre o banco de dados

                // Adiciona o carro
                carro = datasource.createCarro(carro.getNome(), telefone.getText().toString(), carro.getPlaca(), carro.getCor(), carro.getModelo());
                datasource.eliminarCarro(idCarro);

                datasource.close(); // Fecha o banco de dados

                // Cria mensagem para o novo carro
                AlertDialog.Builder dialogo = new AlertDialog.Builder(AlteraDados.this);
                dialogo.setTitle("Aviso");
                dialogo.setMessage("Alterando Telefone do Proprietário: " + carro.getNome());
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
