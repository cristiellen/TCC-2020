package com.tcc.relatorio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
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
import org.w3c.dom.Text;

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

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.8);
        uas.add(1, (float) 1);
        tabela.put(0, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 1.2);
        uas.add(1, (float) 1);
        tabela.put(1, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.6);
        uas.add(1, (float) 0.75);
        tabela.put(2, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.4);
        uas.add(1, (float) 0.5);
        tabela.put(3, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.2);
        uas.add(1, (float) 0.25);
        tabela.put(4, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.6);
        uas.add(1, (float) 0.75);
        tabela.put(5, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.4);
        uas.add(1, (float) 0.5);
        tabela.put(6, uas);

        uas = new ArrayList<Float>();
        uas.add(0, (float) 0.3);
        uas.add(1, (float) 0.25);
        tabela.put(7, uas);

        String[] tiposRebanho = new String[]{"Vacas de cria", "Touro", "Novilha de 2 a 3 anos", "Novilhas de 1 a 2 anos", "Bezerras", "Garrote de 2 a 3 anos", "Garrote de 1 a 2 anos", "Bezerros"};

        long fazendaId = Fazenda.getId_temp();
        Fazenda fazenda = boxStore.boxFor(Fazenda.class).get(fazendaId);

        // TODO: 1 - Calcular o requerimento de água do rebanho de cada invernada
        ArrayList<Float> listaRequerimento = new ArrayList<Float>();
        Float requerimentoTotal = (float) 0;
        Integer numeroAnimaisTotal = 0;
        for (Invernada invernada : fazenda.invernada) {

            Float uaTotal = (float) 0;

            for (Animal animal : invernada.animais) {
                int index = 0;
                ArrayList<Float> ua = new ArrayList<Float>();

                numeroAnimaisTotal = numeroAnimaisTotal + animal.getQuantidade();

                for ( String item: tiposRebanho ) {
                    if (animal.tipo.equals(item)) {
                        ua = tabela.get(index);
                        Log.e("ua", String.valueOf(ua));
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
            requerimentoTotal = requerimentoTotal + (40 * uaTotal);

        }

        // TODO: 2 - Calcular o número de bebedouros (artifical / natural) de cada invernada
        float volumeTotal = 0;
        float capacidadeArmazenamento = 0;
        float perimetroTotal = 0;
        Integer ideal = 0;
        Integer moderado = 0;
        Integer ruim = 0;

        Integer idealCond = 0;
        Integer moderadoCond = 0;
        Integer ruimCond = 0;

        Integer idealDist = 0;
        Integer moderadoDist = 0;
        Integer ruimDist = 0;

        Integer idealIara = 0;
        Integer moderadoIara = 0;
        Integer ruimIara = 0;

        Integer totalBebedouro = 0;
        for (Invernada invernada : fazenda.invernada) {
            for (BebedouroCircular bebedouro : invernada.bebedourosCir){
                float volume = 0.0F;
                float pi = (float) 3.14;
                float raio = Float.parseFloat(bebedouro.raio);
                float h = bebedouro.altura;
                volume = (float) (pi * Math.pow(raio, 2) * h); //

                capacidadeArmazenamento = capacidadeArmazenamento + volume;

                perimetroTotal = perimetroTotal + 2 * pi * raio;

                if (bebedouro.limpeza.equals("Ideal")) {
                    ideal = ideal + 1;
                } else if (bebedouro.limpeza.equals("Moderado")) {
                    moderado = moderado + 1;
                } else if (bebedouro.limpeza.equals("Ruim")) {
                    ruim = ruim + 1;
                }

                if (bebedouro.condicaoAcesso.equals("Ideal")) {
                    idealCond = idealCond + 1;
                } else if (bebedouro.condicaoAcesso.equals("Moderado")) {
                    moderadoCond = moderadoCond + 1;
                } else if (bebedouro.condicaoAcesso.equals("Ruim")) {
                    ruimCond = ruimCond + 1;
                }

                if (bebedouro.distanciaAcesso.equals("Ideal")) {
                    idealDist = idealDist + 1;
                } else if (bebedouro.distanciaAcesso.equals("Moderado")) {
                    moderadoDist = moderadoDist + 1;
                } else if (bebedouro.distanciaAcesso.equals("Ruim")) {
                    ruimDist = ruimDist + 1;
                }

            }
            for (BebedouroRetangular bebedouro : invernada.bebedourosRet){
                float volume = 0.0F;
                Double pi = 3.14;
                float largura = Float.parseFloat(bebedouro.largura);
                float altura = bebedouro.altura;
                float comprimento = Float.parseFloat(bebedouro.comprimento);
                volume = comprimento * largura * altura;

                capacidadeArmazenamento = capacidadeArmazenamento + volume;

                perimetroTotal = perimetroTotal + (2 * comprimento) + (2 * largura);

                if (bebedouro.limpeza.equals("Ideal")) {
                    ideal = ideal + 1;
                } else if (bebedouro.limpeza.equals("Moderado")) {
                    moderado = moderado + 1;
                } else if (bebedouro.limpeza.equals("Ruim")) {
                    ruim = ruim + 1;
                }

                if (bebedouro.condicaoAcesso.equals("Ideal")) {
                    idealCond = idealCond + 1;
                } else if (bebedouro.condicaoAcesso.equals("Moderado")) {
                    moderadoCond = moderadoCond + 1;
                } else if (bebedouro.condicaoAcesso.equals("Ruim")) {
                    ruimCond = ruimCond + 1;
                }

                if (bebedouro.distanciaAcesso.equals("Ideal")) {
                    idealDist = idealDist + 1;
                } else if (bebedouro.distanciaAcesso.equals("Moderado")) {
                    moderadoDist = moderadoDist + 1;
                } else if (bebedouro.distanciaAcesso.equals("Ruim")) {
                    ruimDist = ruimDist + 1;
                }

            }

            totalBebedouro = totalBebedouro + invernada.bebedourosCir.size();
            totalBebedouro = totalBebedouro + invernada.bebedourosRet.size();
        }


        //Dividir o Volume total dos bebedouros (disponibilidade de água) pelo número de animais e verificar se atende ou não o requerimento diário do rebanho
        TextView dispay= (TextView) findViewById(R.id.txtDisplayAguaPorAnimal);
        float litrosTotalDiario = 35 * numeroAnimaisTotal;
        float litrosPorHora = litrosTotalDiario / 24;
        volumeTotal = (litrosPorHora * 2 ) + capacidadeArmazenamento; // um dia, 24 horas

        float disponibilidade = volumeTotal / numeroAnimaisTotal;


        if (disponibilidade > 50){
            dispay.setText("Ideal");

            idealIara = idealIara + 1;

            dispay.setText(dispay.getText() + "\n Requerimento total de água: " + requerimentoTotal);
            dispay.setText(dispay.getText() + "\n Volume total dos bebedouros: " + volumeTotal);
        } else if (disponibilidade >= 30 && disponibilidade <= 50) {
            dispay.setText("Moderado");

            moderadoIara = moderadoIara + 1;
            dispay.setText(dispay.getText() + "\n Requerimento total de água: " + requerimentoTotal);
            dispay.setText(dispay.getText() + "\n Volume total dos bebedouros: " + volumeTotal);
        }
        else if (disponibilidade < 30) {
            dispay.setText("Ruim");

            ruimIara = ruimIara + 1;
            dispay.setText(dispay.getText() + "\n Requerimento total de água: " + requerimentoTotal);
            dispay.setText(dispay.getText() + "\n Volume total dos bebedouros: " + volumeTotal);
        }

        //Soma do contorno de todos os bebedouros e dividir pelo número de animais do rebanho
        dispay= (TextView) findViewById(R.id.txtDisplayMetroLinearAnimalBebedouro);
        float metroLinear = perimetroTotal / numeroAnimaisTotal;
        if (disponibilidade > 0.1){
            dispay.setText("Ideal");
            idealIara = idealIara + 1;
        } else if (disponibilidade >= 0.04 && disponibilidade <= 0.1) {
            dispay.setText("Moderado");

            moderadoIara = moderadoIara + 1;
        }
        else if (disponibilidade < 0.04) {
            dispay.setText("Ruim");

            ruimIara = ruimIara + 1;
        }


        dispay= (TextView) findViewById(R.id.txtDisplayLimpeza);
        if (ideal != 0) {
            dispay.setText(dispay.getText() +"\nIdeal: " + (ideal*100)/totalBebedouro + "%");
            idealIara = idealIara + 1;
        }
        if (moderado != 0) {
            dispay.setText(dispay.getText() +"\nModerado: " + (moderado*100)/totalBebedouro + "%");

            moderadoIara = moderadoIara + 1;
        }
        if (ruim != 0) {
            dispay.setText(dispay.getText() +"\nRuim: " + (ruim*100)/totalBebedouro + "%");
            ruimIara = ruimIara + 1;
        }


        dispay= (TextView) findViewById(R.id.txtDisplayCondicaoAcesso);
        if (idealCond != 0) {
            dispay.setText(dispay.getText() +"\nIdeal: " + (idealCond*100)/totalBebedouro + "%");
            idealIara = idealIara + 1;
        }
        if (moderadoCond != 0) {
            dispay.setText(dispay.getText() +"\nModerado: " + (moderadoCond*100)/totalBebedouro + "%");

            moderadoIara = moderadoIara + 1;
        }
        if (ruimCond != 0) {
            dispay.setText(dispay.getText() +"\nRuim: " + (ruimCond*100)/totalBebedouro + "%");

            ruimIara = ruimIara + 1;
        }

        dispay= (TextView) findViewById(R.id.txtDisplayDistancia);
        if (idealDist != 0) {
            dispay.setText(dispay.getText() +"\nIdeal: " + (idealDist*100)/totalBebedouro + "%");

            idealIara = idealIara + 1;
        }
        if (moderadoDist != 0) {
            dispay.setText(dispay.getText() +"\nModerado: " + (moderadoDist*100)/totalBebedouro + "%");

            moderadoIara = moderadoIara + 1;
        }
        if (ruimDist != 0) {
            dispay.setText(dispay.getText() +"\nRuim: " + (ruimDist*100)/totalBebedouro + "%");

            ruimIara = ruimIara + 1;
        }

        dispay= (TextView) findViewById(R.id.txtDisplayIara);
        if (idealIara >= 4) {
            dispay.setText(dispay.getText() +"\nEscore 3 - Adequado");
        } else if (moderadoIara >= 1) {
            dispay.setText(dispay.getText() +"\nEscore 2 - Moderado");
        } else if (ruimIara >= 1) {
            dispay.setText(dispay.getText() +"\nEscore 3 - Ruim ");
        }

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        myChildToolbar.setTitle("Relatório");
        setSupportActionBar(myChildToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void alerta(String mensagem){
        //funcao pra exibir mensagem básica ao usuario
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    //TODO: Em caso de ruim, quantos litros faltam pros animais, diferença de numero pra 30 vezes total de animal (para chegar no moderado, ou para chegar no ideal (fazendeiro que escolhe)) (por em uma nova tela chamada projeção, acesso pelo botao no canto da tela do relatorio) (nivel fazenda)
    //TODO: Projeção de necessidade do rebanho (nivel fazenda)
}
