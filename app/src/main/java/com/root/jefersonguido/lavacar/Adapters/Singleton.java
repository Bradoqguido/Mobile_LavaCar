package com.root.jefersonguido.lavacar.Adapters;

import java.util.ArrayList;

/**
 * Created by Jeferson Eduardo on 01/11/2017.
 */

public class Singleton {
    private static Singleton instance = null;

    private Singleton(){

    }

    public static Singleton getIntance(){
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    public Long getIdCarros(int posicao) {
        return idcarros.get(posicao);
    }

    public void addIdCarro(Long idcarros) {
        this.idcarros.add(idcarros);
    }

    public boolean removeIdCarro(long posicao) {
        return idcarros.remove(posicao);
    }

    private ArrayList<Long> idcarros = new ArrayList<>();


}
