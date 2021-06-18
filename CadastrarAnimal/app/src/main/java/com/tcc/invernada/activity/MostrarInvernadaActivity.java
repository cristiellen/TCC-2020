package com.tcc.invernada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.tcc.animal.activity.ListarAnimaisActivity;
import com.tcc.bebedouro.activity.ListarBebedouroActivity;
import com.tcc.fazenda.dao.Fazenda;
import com.tcc.main.MainActivity;
import com.tcc.main.R;

public class MostrarInvernadaActivity extends AppCompatActivity {
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_invernada);


        String nome="";
        // quem está preenchendo esse valor?
        id= Fazenda.getId_temp();
        Button btanimal= (Button) findViewById(R.id.btAnimal);
        btanimal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), ListarAnimaisActivity.class);
                startActivity(intent);
            }
        });

        Button btBebedouro= (Button) findViewById(R.id.btBebedouro);
        btBebedouro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), ListarBebedouroActivity.class);
                startActivity(intent);
            }
        });



        // my_child_toolbar is defined in the layout file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalhes - Invernada");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }


}
