package com.tcc.bebedouro.dao;

import com.tcc.invernada.dao.Invernada;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class BebedouroCircular extends Bebedouro {

    public String raio;
    public String vazao;
    public ToOne<Invernada> invernada;


    public BebedouroCircular(float altura, String condicaoAcesso, String limpeza, String raio, String vazao, String distanciaAcesso) {
        super(altura, condicaoAcesso, limpeza, distanciaAcesso);
        this.raio = raio;
        this.vazao = vazao;
    }

    public BebedouroCircular( ) {

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRaio() {
        return raio;
    }

    public void setRaio(String raio) {
        this.raio = raio;
    }

    public String getVazao() {
        return vazao;
    }

    public void setVazao(String vazao) {
        this.vazao = vazao;
    }

    @Override
    public String toString(){
        return "raio: " + this.raio+", vazão: "+ this.vazao + ", altura: " + this.altura + ", Condição acesso: " + this.condicaoAcesso + ", limpeza: " + this.limpeza  + ", distancia: " + this.distanciaAcesso;
    }
}
