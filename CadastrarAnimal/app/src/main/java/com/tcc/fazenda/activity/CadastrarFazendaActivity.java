package com.tcc.fazenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tcc.fazenda.dao.Fazenda;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;

import com.tcc.relatorio.activity.ListarRelatorioPorFazendaActivity;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CadastrarFazendaActivity extends AppCompatActivity {
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_fazenda);

        BoxStore boxStore = ObjectBox.get();
        Box<Fazenda> fazendaBox = boxStore.boxFor(Fazenda.class);

        Button salvar= (Button) findViewById(R.id.btSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nomeedt = (EditText) findViewById(R.id.edtFazenda);

                Fazenda fazenda = new Fazenda(nomeedt.getText().toString());
                id = fazendaBox.put(fazenda);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
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
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        myChildToolbar.setTitle("Cadastro Fazenda");
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.relatorio:
                relatorio();
                return true;
            case R.id.home:
                home();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void relatorio() {
        Intent intent = new Intent(this, ListarRelatorioPorFazendaActivity.class);
        startActivity(intent);
    }

    public void home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}



