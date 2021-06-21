package com.tcc.bebedouro.dao;

import com.tcc.fazenda.dao.Fazenda;

import com.tcc.invernada.dao.Invernada;
import io.objectbox.annotation.BaseEntity;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@BaseEntity
public abstract class Bebedouro {

    @Id
    public long id;
    public float altura;
    public String condicaoAcesso;
    public String limpeza;
    public String distanciaAcesso;

    @Transient
    private static long id_temp;


    public Bebedouro(float altura, String condicaoAcesso, String limpeza, String distanciaAcesso) {
        this.altura = altura;
        this.condicaoAcesso = condicaoAcesso;
        this.limpeza = limpeza;
        this.distanciaAcesso = distanciaAcesso;
    }

    public Bebedouro( ) {

    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getCondicaoAcesso() {
        return condicaoAcesso;
    }

    public void setCondicaoAcesso(String condicaoAcesso) {
        this.condicaoAcesso = condicaoAcesso;
    }

    public String getLimpeza() {
        return limpeza;
    }

    public void setLimpeza(String limpeza) {
        this.limpeza = limpeza;
    }

    public static long getId_temp() {
        return id_temp;
    }

    public static void setId_temp(long id_temp) {
        Bebedouro.id_temp = id_temp;
    }

    public String getDistanciaAcesso() {
        return distanciaAcesso;
    }

    public void setDistanciaAcesso(String distanciaAcesso) {
        this.distanciaAcesso = distanciaAcesso;
    }

    @Override
    public String toString(){
        return "altura: " + this.altura+"; condição de acesso: " + this.condicaoAcesso+"; limpeza: "+this.limpeza;
    }
}

