package com.tcc.invernada.activity;

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
import com.tcc.fazenda.dao.Fazenda;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import io.objectbox.Box;
import io.objectbox.BoxStore;

import java.util.ArrayList;

public class ListarInvernadaActivity extends AppCompatActivity {
    private ArrayList<Invernada> invernadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_invernada);
        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Invernadas");
        setSupportActionBar(toolbar);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // oculta a seta da primeira tela
        ab.setDisplayHomeAsUpEnabled(true);

        BoxStore  boxStore = ObjectBox.get();
        Box<Invernada> invernadaBox = boxStore.boxFor(Invernada.class);

        invernadas = (ArrayList<Invernada>) invernadaBox.getAll();
        ArrayList<Invernada> newList = new ArrayList<Invernada>();
        for (Invernada obj : invernadas) {
            if (obj.fazenda.getTargetId() == Fazenda.getId_temp()){
                newList.add(obj);
            }
        }

        Intent intent = new Intent(this, CadastrarInvernadaActivity.class);
        //newList.size instead
        if (invernadas.size() <= 0) {
            startActivity(intent);
        }

        ListView listaView = (ListView) findViewById(R.id.lista);
        listaView.setOnItemClickListener(this::onItemClick);

        ArrayAdapter<Invernada> adapter = new ArrayAdapter<Invernada>(this, android.R.layout.simple_list_item_1, newList );
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
        intent.setClass(this, MostrarInvernadaActivity.class);
        Invernada.setId_temp(invernadas.get(position).getId());
        startActivity(intent);
    }

    //TODO: PRIMEIRO LISTAR E CADASTRAR A APARECER DEVE SER O DA FAZENDA
    //TODO: LISTAR OS BEBEDOUROS E NESSA TELA MOSTRAR UM BOTÃO CADASTRO
    //TODO: PLANÍCIE E PLANALTO ACTIVITY MAIN

}





