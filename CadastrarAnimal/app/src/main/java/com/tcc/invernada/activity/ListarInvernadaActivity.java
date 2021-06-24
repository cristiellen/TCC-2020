package com.tcc.invernada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.tcc.animal.dao.Animal;
import com.tcc.bebedouro.dao.BebedouroCircular;
import com.tcc.bebedouro.dao.BebedouroRetangular;
import com.tcc.fazenda.dao.Fazenda;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import com.tcc.relatorio.activity.ListarRelatorioPorFazendaActivity;
import io.objectbox.Box;
import io.objectbox.BoxStore;

import java.util.ArrayList;

public class ListarInvernadaActivity extends AppCompatActivity {
    private ArrayList<Invernada> invernadas;
    private Invernada invernada;

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
        listaView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //vai chamar o menu criado na linha 93
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                invernada = invernadas.get(position);//pegando o elemento
                return false;
            }
        });
        registerForContextMenu(listaView); //habilitando menu de contexto(clicar e segurar pra abrir opções)


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Deletar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (Animal animal : invernada.animais) {
                    ObjectBox.get().boxFor(Animal.class).remove(animal);
                }

                for (BebedouroRetangular bebedouro : invernada.bebedourosRet){
                    ObjectBox.get().boxFor(BebedouroRetangular.class).remove(bebedouro);
                }

                for (BebedouroCircular bebedouro : invernada.bebedourosCir) {
                    ObjectBox.get().boxFor(BebedouroCircular.class).remove(bebedouro);
                }

                invernada.fazenda.setTarget(null);
                ObjectBox.get().boxFor(Invernada.class).remove(invernada);
                Intent intent = new Intent(v.getContext(), ListarInvernadaActivity.class);
                alerta("Invernada '"+invernada.getNome()+"' removida");
                startActivity(intent);
                return false;
            }
        });

        menuItem = menu.add("Editar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Invernada.setId_temp(invernada.getId());

                Intent intent = new Intent(v.getContext(), AtualizarInvernadaActivity.class);
                startActivity(intent);
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alerta(String mensagem){
        //funcao pra exibir mensagem básica ao usuario
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
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


    //TODO: PRIMEIRO LISTAR E CADASTRAR A APARECER DEVE SER O DA FAZENDA
    //TODO: LISTAR OS BEBEDOUROS E NESSA TELA MOSTRAR UM BOTÃO CADASTRO
    //TODO: PLANÍCIE E PLANALTO ACTIVITY MAIN

}





