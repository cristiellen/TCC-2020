package com.tcc.relatorio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tcc.fazenda.activity.CadastrarFazendaActivity;
import com.tcc.fazenda.dao.Fazenda;
import com.tcc.invernada.activity.CadastrarInvernadaActivity;
import com.tcc.invernada.activity.MostrarInvernadaActivity;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import io.objectbox.Box;
import io.objectbox.BoxStore;

import java.util.ArrayList;

public class ListarRelatorioPorFazendaActivity extends AppCompatActivity {
    private ArrayList<Fazenda> fazendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_relatorio_por_fazenda);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // oculta a seta da primeira tela
        ab.setDisplayHomeAsUpEnabled(true);

        BoxStore  boxStore = ObjectBox.get();

        Box<Fazenda> fazendaBox = boxStore.boxFor(Fazenda.class);

        fazendas = (ArrayList<Fazenda>) fazendaBox.getAll();
        ArrayList<Fazenda> newList = new ArrayList<Fazenda>();
        for (Fazenda obj : fazendas) {
                newList.add(obj);
        }

        Intent intent = new Intent(this, CadastrarFazendaActivity.class);
        //newList.size instead
        if (fazendas.size() <= 0) {
            startActivity(intent);
        }

        ListView listaView = (ListView) findViewById(R.id.lista);
        listaView.setOnItemClickListener(this::onItemClick);

        ArrayAdapter<Fazenda> adapter = new ArrayAdapter<Fazenda>(this, android.R.layout.simple_list_item_1, newList );
        listaView.setAdapter(adapter);


        FloatingActionButton cadastrar= (FloatingActionButton) findViewById(R.id.btCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Intent intent2 = new Intent(this, MainActivity.class);

    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, MostrarRelatorioActivity.class);
        Fazenda.setId_temp(fazendas.get(position).getId());
        startActivity(intent);
    }

    //TODO: PRIMEIRO LISTAR E CADASTRAR A APARECER DEVE SER O DA FAZENDA
    //TODO: LISTAR OS BEBEDOUROS E NESSA TELA MOSTRAR UM BOTÃO CADASTRO
    //TODO: PLANÍCIE E PLANALTO ACTIVITY MAIN

}





