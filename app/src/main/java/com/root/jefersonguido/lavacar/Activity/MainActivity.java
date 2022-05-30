package com.root.jefersonguido.lavacar.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.root.jefersonguido.lavacar.Adapters.Carro;
import com.root.jefersonguido.lavacar.Adapters.DBAdapter;
import com.root.jefersonguido.lavacar.Adapters.Singleton;
import com.root.jefersonguido.lavacar.R;

public class MainActivity extends ListActivity{
    static final int ADICIONAR_CONTATO = 1;
    static final String idCarro = "idCarro";

    private Button novocarro;
    private ListAdapter adapter;
    private DBAdapter datasource;
    private TextView idcarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idcarro = (TextView) findViewById(R.id.idcarro);

        novocarro = findViewById(R.id.btn_novo_carro);

        datasource = new DBAdapter(this);
        datasource.open();
        // Captura os carros
        Cursor cursor = datasource.getCarros();

        cursor.moveToFirst(); // posição 0
        for (int i = 1; i <= cursor.getCount(); i++){
            Singleton.getIntance().addIdCarro(cursor.getLong(0));
            if (i <= cursor.getCount());
            cursor.moveToNext();
        }

        cursor.moveToFirst(); // posição 0

        // Cria colunas com os contatos
        String[] columns = new String[]{ "nome", "telefone" , "_id"};
        int[] to = new int[] { R.id.nome, R.id.telefone , R.id.idcarro};

        adapter = new SimpleCursorAdapter(this, R.layout.carro_list_item, cursor, columns, to);
        this.setListAdapter(adapter); // Define a lista do adaptador para o layout
        datasource.close(); // Fecha a base de dados

        novocarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NovoCarroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, ADICIONAR_CONTATO);
            }
        });
    }

    // Método para atualizar a lista de contatos sempre que ela deixar o primeiro plano
    @Override
    protected void onPause() {
        super.onPause();
        datasource.open(); // Abre o banco de dados

        // Captura os carros
        Cursor cursor = datasource.getCarros();

        cursor.moveToFirst(); // posição 0
        for (int i = 1; i <= cursor.getCount(); i++){
            Singleton.getIntance().addIdCarro(cursor.getLong(0)); // Atualiza um array que possui todos os códigos dos carros no banco de dados
            if (i <= cursor.getCount());
            cursor.moveToNext();
        }

        cursor.moveToFirst(); // posição 0

        // Cria colunas com os contatos
        String[] columns = new String[]{ "nome", "telefone" , "_id"};
        int[] to = new int[] { R.id.nome, R.id.telefone , R.id.idcarro};

        // Adiciona os itens ao adaptador
        adapter = new SimpleCursorAdapter(this, R.layout.carro_list_item, cursor, columns, to);
        this.setListAdapter(adapter); // Define a lista do adaptador para o layout
        datasource.close(); // Fecha a base de dados
    }


    // Método para atualizar a lista de contatos sempre que ela voltar para o primeiro plano
    protected void onResume(){
        super.onResume();
        datasource.open(); // Abre o banco de dados

        // Captura os carros
        Cursor cursor = datasource.getCarros();

        cursor.moveToFirst(); // posição 0
        for (int i = 1; i <= cursor.getCount(); i++){
            Singleton.getIntance().addIdCarro(cursor.getLong(0)); // Atualiza um array que possui todos os códigos dos carros no banco de dados
            if (i <= cursor.getCount());
                cursor.moveToNext();
        }

        cursor.moveToFirst(); // posição 0

        // Cria colunas com os contatos
        String[] columns = new String[]{ "nome", "telefone" , "_id"};
        int[] to = new int[] { R.id.nome, R.id.telefone , R.id.idcarro};

        // Adiciona os itens ao adaptador
        adapter = new SimpleCursorAdapter(this, R.layout.carro_list_item, cursor, columns, to);
        this.setListAdapter(adapter); // Define a lista do adaptador para o layout
        datasource.close(); // Fecha a base de dados
    }

    // Método para exibir os detalhes do contato selecionado na lista
    protected void onListItemClick(ListView l, View v, int position, long id){
        datasource.open(); // Abre o banco de dados

        // Captura os carros
        Cursor cursor = datasource.getCarros();

        cursor.moveToFirst(); // posição 0
        for (int i = 1; i <= cursor.getCount(); i++){
            Singleton.getIntance().addIdCarro(cursor.getLong(0)); // Atualiza um array que possui todos os códigos dos carros no banco de dados
            if (i <= cursor.getCount());
            cursor.moveToNext();
        }

        cursor.moveToFirst(); // posição 0
        datasource.close(); // Fecha a base de dados

        Intent intent = new Intent(this, DetalhesActivity.class);
        intent.putExtra("idCarro", Singleton.getIntance().getIdCarros(position)); // Envia a posção seleionada para a proxima activity
        startActivity(intent);
    }

}
