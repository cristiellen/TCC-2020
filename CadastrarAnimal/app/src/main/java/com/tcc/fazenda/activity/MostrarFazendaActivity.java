package com.tcc.fazenda.activity;

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
import com.tcc.invernada.activity.ListarInvernadaActivity;
import com.tcc.main.MainActivity;
import com.tcc.main.R;

public class MostrarFazendaActivity extends AppCompatActivity {
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_fazenda);


        String nome="";
        Button btInvernada= (Button) findViewById(R.id.btInvernada);
        btInvernada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), ListarInvernadaActivity.class);
                startActivity(intent);
            }
        });

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        myChildToolbar.setTitle("Detalhes - Fazenda");
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }


}
