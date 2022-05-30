package com.root.jefersonguido.lavacar.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

/**
 * Created by Jeferson Eduardo on 03/10/2017.
 */

public class DBAdapter {
	private SQLiteDatabase database;
	private DBHelper dbHelper;

    private String[] allColumns = {DBHelper.ID, DBHelper.NOME, DBHelper.TELEFONE, DBHelper.PLACA, DBHelper.COR, DBHelper.MODELO};

	public DBAdapter(Context context) {
		dbHelper = new DBHelper(context);
	}

	// Dá permissão de escrita no banco
	public void open() throws SQLiteException{
		database = dbHelper.getWritableDatabase();
	}

	// Fecha o banco de dados
	public void close(){
		dbHelper.close();
	}

	// Método que devolve o carro por um cursor
	private Carro cursorToCarro(Cursor cursor){

		// Faz a captura do carro como se fosse um array utilizando o cursor
		Carro carro = new Carro(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        Singleton.getIntance().addIdCarro(carro.get_id());
		// Retorna o objeto carro
		return carro;
	}

	// Método para criar um novo carro
	public Carro createCarro (String nome, String telefone, String placa, String cor, String modelo){

		// Faz a captura dos dados do carro
		ContentValues values = new ContentValues();
		values.put(DBHelper.NOME, nome);
		values.put(DBHelper.TELEFONE, telefone);
		values.put(DBHelper.PLACA, placa);
		values.put(DBHelper.COR, cor);
		values.put(DBHelper.MODELO, modelo);
		long insertId = database.insert(DBHelper.TABLE_NAME, null, values); // ID do item
		Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, DBHelper.ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
		return cursorToCarro(cursor); // Retorna o carro
	}

	// Elimina o carro selecionado
	public void eliminarCarro(long idCarro){
		database.delete(DBHelper.TABLE_NAME, DBHelper.ID + " = " + idCarro, null);
	}

	// Retorna todos os carros
	public Cursor getCarros(){
		Cursor cursor = database.rawQuery("select _id, nome, telefone, placa, cor, modelo from carros2", null);
		return cursor;
	}

	// Faz a captura do carro selecionado pelo ID
	public Carro getCarro (long idCarro){
		Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, DBHelper.ID + " = " + idCarro, null, null, null, null);
		cursor.moveToFirst();
		return cursorToCarro(cursor);
	}

}
