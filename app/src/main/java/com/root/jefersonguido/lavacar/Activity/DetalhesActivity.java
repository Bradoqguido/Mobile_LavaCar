package com.root.jefersonguido.lavacar.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.SaveInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.root.jefersonguido.lavacar.Adapters.Carro;
import com.root.jefersonguido.lavacar.Adapters.DBAdapter;
import com.root.jefersonguido.lavacar.Adapters.Singleton;
import com.root.jefersonguido.lavacar.R;

public class DetalhesActivity extends Activity {
    static final int EDITAR_CONTATO = 2;
    static final String IDDETALHESCARRO = "IDDETALHESCARRO";

    private DBAdapter datasource;
    private TextView txtnome, txttelefone, txtplaca, txtcor, txtmodelo;
    private Button voltar, editar, excluir;
    private long idCarro;
    private Carro carro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        txtnome = (TextView) findViewById(R.id.txtnome);
        txttelefone = (TextView) findViewById(R.id.txttelefone);
        txtplaca = (TextView) findViewById(R.id.txtplaca);
        txtcor = (TextView) findViewById(R.id.txtcor);
        txtmodelo = (TextView) findViewById(R.id.txtmodelo);

        iniciaObjetos(); // Inicia os objetos
        datasource = new DBAdapter(this); // Inicia o Banco

        Intent intent = getIntent();
        idCarro = intent.getLongExtra("idCarro", 1); // Recebe o código do carro selecionado na activity anterior
        datasource.open(); // Abre o banco

        carro = new Carro();
        carro = datasource.getCarro(idCarro); // Captura o carro selecionado do banco de dados

        // Debug do ID do banco recebido
        //Toast.makeText(getApplicationContext(), "id: " + carro.get_id(),Toast.LENGTH_SHORT); // Exibe o Código selecionado, Debug Test

        // Muda os textos dos objetos TextView
        txtnome.setText(""+carro.getNome());
        txttelefone.setText(""+carro.getTelefone());
        txtplaca.setText(""+carro.getPlaca());
        txtcor.setText(""+carro.getCor());
        txtmodelo.setText(""+carro.getModelo());

        datasource.close(); // Fecha o banco

        // Volta à activity anterior
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Finaliza a activity
            }
        });

        // Inicia a activity que irá possibilitar a edição do carro
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlteraDados.class);
                intent.putExtra("IDDETALHESCARRO", idCarro); // Envia a posção seleionada para a proxima activity
                startActivityForResult(intent, EDITAR_CONTATO); // Inicia a proxima Activity
                finish(); // Finaliza a Activity
            }
        });

        // Exclui o carro com o id selecionado pelo usuário
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datasource.open(); // Abre o banco de dados
                datasource.eliminarCarro(idCarro); // Elimina o carro do banco
                Singleton.getIntance().removeIdCarro(idCarro); // Elimina o carro da lista
                datasource.close(); // Fecha o banco de dados

                // Cria mensagem para o novo carro
                AlertDialog.Builder dialogo = new AlertDialog.Builder(DetalhesActivity.this);
                dialogo.setTitle("Aviso");
                dialogo.setMessage("Exluindo dados referentes ao carro do Proprietário: " + carro.getNome());
                dialogo.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish(); // Finaliza a activity
                    }
                });
                dialogo.show(); // Exibe o dialogo a cima que é executado pelo botão de excluir
            }
        });

    }

    private void iniciaObjetos() {
        voltar = (Button) findViewById(R.id.btnvoltar);
        editar = (Button) findViewById(R.id.btnEditar);
        excluir = (Button) findViewById(R.id.btnExcluir);
    }

}
