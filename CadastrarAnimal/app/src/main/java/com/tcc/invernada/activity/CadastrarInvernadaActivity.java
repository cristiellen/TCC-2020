package com.tcc.invernada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tcc.fazenda.dao.Fazenda;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CadastrarInvernadaActivity extends AppCompatActivity {
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_invernada);

        BoxStore boxStore = ObjectBox.get();
        Box<Invernada> invernadaBox = boxStore.boxFor(Invernada.class);
        Box<Fazenda> fazendaBox = boxStore.boxFor(Fazenda.class);

        Button salvar= (Button) findViewById(R.id.btSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nomeedt = (EditText) findViewById(R.id.edtInvernada);

                Fazenda fazenda = fazendaBox.get(Fazenda.getId_temp());
                Invernada invernada = new Invernada(nomeedt.getText().toString());
                invernada.fazenda.setTarget(fazenda);
                invernadaBox.put(invernada);

                fazenda.invernada.reset();
                fazenda.invernada.add(invernada);
                fazendaBox.put(fazenda);

                Intent intent = new Intent(v.getContext(), ListarInvernadaActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        Button voltar= (Button) findViewById(R.id.btVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        voltar = (Button) findViewById(R.id.btVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // my_child_toolbar is defined in the layout file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro Invernada");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    }



