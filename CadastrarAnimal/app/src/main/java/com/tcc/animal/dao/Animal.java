package com.tcc.animal.dao;

import com.tcc.fazenda.dao.Fazenda;

import com.tcc.invernada.dao.Invernada;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Animal {
    @Id
    public long id;
    public int quantidade;
    public String tipo;
    public String relevo;


    public ToOne<Invernada> invernada;

    public Animal(int quantidade, String tipo, String relevo) {
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.relevo = relevo;
    }

    public Animal( ) {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRelevo() {
        return relevo;
    }

    public void setRelevo(String relevo) {
        this.relevo = relevo;
    }

    @Override
    public String toString(){
        return "Tipo: " + this.tipo + ", " + "Quantidade: " + this.quantidade + ", " + this.relevo;
    }
}