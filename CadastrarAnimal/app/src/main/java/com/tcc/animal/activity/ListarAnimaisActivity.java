package com.tcc.animal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;
import com.tcc.animal.dao.Animal;
import com.tcc.bebedouro.activity.CadastrarBebedouroActivity;
import com.tcc.invernada.activity.ListarInvernadaActivity;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;

import java.util.ArrayList;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ListarAnimaisActivity extends AppCompatActivity {
    private Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_animal);
        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Animais");
        setSupportActionBar(toolbar);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // oculta a seta da primeira tela
        ab.setDisplayHomeAsUpEnabled(true);

        BoxStore  boxStore = ObjectBox.get();

        Box<Animal> animalBox = boxStore.boxFor(Animal.class);

        long  id= Invernada.getId_temp();
        ArrayList<Animal> animais = (ArrayList<Animal>) animalBox.getAll();
        ArrayList<Animal> newList = new ArrayList<Animal>();
        for (Animal obj : animais) {
            if (obj.invernada.getTargetId() == Invernada.getId_temp()){
                newList.add(obj);
            }
        }

        //Condição para mudar a tela, caso não haja conteúdo cadastrado na lista
        //vai abrir o cadastrar se não vai para a tela do listar.
        Intent intent = new Intent(this, CadastrarAnimalActivity.class);
        if (animais.size() <= 0) {
            startActivity(intent);
        }

        //instanciando uma listView para ser conectada a lista da activity main
        ListView listaView = (ListView) findViewById(R.id.lista);
        listaView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //vai chamar o menu criado na linha 93
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                animal = animais.get(position);//pegando o elemento
                return false;
            }
        });
        registerForContextMenu(listaView); //habilitando menu de contexto(clicar e segurar pra abrir opções)


        //adapter necessário para passar a forma de que será adionado o conteúdo como a seguir, em simple_list_item_1
        //possui dados de um  text view e também é passado a lista de resultados que possui os objetos cadastrados
        ArrayAdapter<Animal> adapter = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, newList );
        // listView setando o adapter que será demonstrado na tela
        listaView.setAdapter(adapter);


        FloatingActionButton cadastrar= (FloatingActionButton) findViewById(R.id.btCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Intent intent2 = new Intent(this, MainActivity.class);

    }

    //Mudar para mostrar
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, CadastrarBebedouroActivity.class);
        intent.putExtra("position", position);
        // Or / And
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Deletar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                animal.invernada.setTarget(null);
                ObjectBox.get().boxFor(Animal.class).remove(animal);
                Intent intent = new Intent(v.getContext(), ListarAnimaisActivity.class);
                alerta("Registro de '"+animal.getTipo()+"' removida");
                startActivity(intent);
                return false;
            }
        });

        menuItem = menu.add("Editar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Animal.setId_temp(animal.getId());

                Intent intent = new Intent(v.getContext(), AtualizarAnimalActivity.class);
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

    //TODO: PRIMEIRO LISTAR E CADASTRAR A APARECER DEVE SER O DA FAZENDA
    //TODO: LISTAR OS BEBEDOUROS E NESSA TELA MOSTRAR UM BOTÃO CADASTRO
    //TODO: PLANÍCIE E PLANALTO ACTIVITY MAIN

}





