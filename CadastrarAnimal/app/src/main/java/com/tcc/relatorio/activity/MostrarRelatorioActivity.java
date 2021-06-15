package com.tcc.relatorio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.tcc.animal.dao.Animal;
import com.tcc.bebedouro.dao.Bebedouro;
import com.tcc.bebedouro.dao.BebedouroCircular;
import com.tcc.bebedouro.dao.BebedouroRetangular;
import com.tcc.fazenda.dao.Fazenda;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import io.objectbox.BoxStore;

import java.util.ArrayList;

import java.util.HashMap;


public class MostrarRelatorioActivity extends AppCompatActivity {
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_relatorio);
        BoxStore boxStore = ObjectBox.get();

        HashMap<Integer, ArrayList<Float>> tabela = new HashMap<Integer, ArrayList<Float>>();
        ArrayList<Float> uas = new ArrayList<Float>();

        uas.add(0, (float) 0.8);
        uas.add(1, (float) 1);
        tabela.put(0, uas);

        uas.add(0, (float) 1.2);
        uas.add(1, (float) 1);
        tabela.put(1, uas);

        uas.add(0, (float) 0.6);
        uas.add(1, (float) 0.75);
        tabela.put(2, uas);

        uas.add(0, (float) 0.4);
        uas.add(1, (float) 0.5);
        tabela.put(3, uas);

        uas.add(0, (float) 0.2);
        uas.add(1, (float) 0.25);
        tabela.put(4, uas);

        uas.add(0, (float) 0.6);
        uas.add(1, (float) 0.75);
        tabela.put(5, uas);

        uas.add(0, (float) 0.4);
        uas.add(1, (float) 0.5);
        tabela.put(6, uas);

        uas.add(0, (float) 0.3);
        uas.add(1, (float) 0.25);
        tabela.put(7, uas);

        String[] tiposRebanho = new String[]{"Vacas de cria", "Touro", "Novilha de 2 a 3 anos", "Novilhas de 1 a 2 anos", "Bezerras", "Garrote de 2 a 3 anos", "Garrote de 1 a 2 anos", "Bezerros"};

        final TextView dispay= (TextView) findViewById(R.id.txtDisplay);

        long fazendaId = Fazenda.getId_temp();
        Fazenda fazenda = boxStore.boxFor(Fazenda.class).get(fazendaId);

        // TODO: 1 - Calcular o requerimento de água do rebanho de cada invernada
        ArrayList<Float> listaRequerimento = new ArrayList<Float>();
        Float requerimento = (float) 0;
        for (Invernada invernada : fazenda.invernada) {

            Float uaTotal = (float) 0;

            for (Animal animal : invernada.animais) {
                int index = 0;
                ArrayList<Float> ua = new ArrayList<Float>();

                for ( String item: tiposRebanho ) {
                    if (animal.tipo.equals(item)) {
                        ua = tabela.get(index);
                    }
                    index+=1;
                }
                if (animal.relevo.equals("planicie")) {

                    uaTotal += animal.getQuantidade() * ua.get(0);

                } else if (animal.relevo.equals("planalto")) {

                    uaTotal += animal.getQuantidade()  * ua.get(1);

                }
            }

            //calculo
            requerimento = (40 * uaTotal) / 1000;

        }

        // TODO: 2 - Calcular o número de bebedouros (artifical / natural) de cada invernada
        for (Invernada invernada : fazenda.invernada) {
            for (BebedouroCircular bebedouro : invernada.bebedourosCir){
                float volume = 0.0F;
                float pi = (float) 3.14;
                float raio = Float.parseFloat(bebedouro.raio);
                float h = bebedouro.altura;

                volume = (float) (pi * Math.pow(raio, 2) * h); //

                dispay.setText("" + String.valueOf(volume));
            }
            for (BebedouroRetangular bebedouro : invernada.bebedourosRet){
                dispay.setText("" + bebedouro.toString());
                float volume = 0.0F;
                Double pi = 3.14;
                float largura = Float.parseFloat(bebedouro.largura);
                float altura = bebedouro.altura;
                float comprimento = Float.parseFloat(bebedouro.comprimento);

                volume = comprimento * largura * altura;
                dispay.setText("" + String.valueOf(volume));
            }
        }



        // TODO: 3 - Calcular o volume (V) da água disponível para:
            // TODO: 3.1 - Bebedouro circular
            // TODO: 3.2 - Bebedouro retangular

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }


}
