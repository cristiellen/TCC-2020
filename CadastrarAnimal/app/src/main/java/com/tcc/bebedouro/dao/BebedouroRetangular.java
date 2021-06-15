package com.tcc.bebedouro.dao;

import com.tcc.invernada.dao.Invernada;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class BebedouroRetangular extends Bebedouro {

    public String comprimento;
    public String largura;
    public ToOne<Invernada> invernada;


    public BebedouroRetangular(float altura, String condicaoAcesso, String limpeza, String comprimento, String largura) {
        super(altura, condicaoAcesso, limpeza);
        this.comprimento = comprimento;
        this.largura = largura;
    }

    public BebedouroRetangular() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComprimento() {
        return comprimento;
    }

    public void setComprimento(String comprimento) {
        this.comprimento = comprimento;
    }

    public String getLargura() {
        return largura;
    }

    public void setLargura(String largura) {
        this.largura = largura;
    }

    @Override
    public String toString(){
        return "comprimento: " + this.comprimento+", largura: "+ this.largura + ", altura: " + this.altura;
    }
}
