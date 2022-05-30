package com.root.jefersonguido.lavacar.Adapters;

/**
 * Created by Jeferson Eduardo on 01/11/2017.
 */

public class Carro {

    private long _id;
    private String nome = "";
    private String telefone = "";
    private String placa = "";
    private String cor = "";
    private String modelo = "";

    public Carro(){

    }

    public Carro(long _id, String nome, String telefone, String placa, String cor, String modelo) {
        this._id = _id;
        this.nome = nome;
        this.telefone = telefone;
        this.placa = placa;
        this.cor = cor;
        this.modelo = modelo;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

}
