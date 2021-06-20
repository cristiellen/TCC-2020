package com.tcc.main;

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
import com.tcc.fazenda.activity.AtualizarFazendaActivity;
import com.tcc.fazenda.activity.CadastrarFazendaActivity;
import com.tcc.fazenda.activity.MostrarFazendaActivity;
import com.tcc.fazenda.dao.Fazenda;

import java.util.List;

import com.tcc.invernada.activity.ListarInvernadaActivity;
import com.tcc.relatorio.activity.ListarRelatorioPorFazendaActivity;
import io.objectbox.Box;

public class MainActivity extends AppCompatActivity {
    private   List<Fazenda> fazendasLista;
    private Fazenda fazenda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Fazendas");
        setSupportActionBar(toolbar);

        Box<Fazenda> fazendaBox = ObjectBox.get().boxFor(Fazenda.class);
        fazendasLista = fazendaBox.getAll();
        Intent intent = new Intent(this, CadastrarFazendaActivity.class);
        if (fazendasLista.size()  <= 0) {
            startActivity(intent);
        }

        ListView listaView = (ListView) findViewById(R.id.lista);
        listaView.setOnItemClickListener(this::onItemClick);
        listaView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //vai chamar o menu criado na linha 93
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                fazenda = fazendasLista.get(position);//pegando o elemento
                return false;
            }
        });
        registerForContextMenu(listaView); //habilitando menu de contexto(clicar e segurar pra abrir opções)

        ArrayAdapter<Fazenda> adapter = new ArrayAdapter<Fazenda>(this, android.R.layout.simple_list_item_1, fazendasLista );
        listaView.setAdapter(adapter);

        FloatingActionButton cadastrar= (FloatingActionButton) findViewById(R.id.btCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // oculta a seta da primeira tela
        ab.setDisplayHomeAsUpEnabled(false);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void relatorio() {
        Intent intent = new Intent(this, ListarRelatorioPorFazendaActivity.class);
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, ListarInvernadaActivity.class);
        Fazenda.setId_temp(fazendasLista.get(position).getId());
        // Or / And
        //sem essas tres linhas esta dando o erro do id
        //int idInt= Integer.parseInt(Long.toString(id));

       // Fazenda fazenda= fazendasLista.get(idInt);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Deletar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ObjectBox.get().boxFor(Fazenda.class).remove(fazenda);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                alerta("Fazenda '"+fazenda.getNome()+"' removida");
                startActivity(intent);
                return false;
            }
        });

        menuItem = menu.add("Editar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fazenda.setId_temp(fazenda.getId());
                Intent intent = new Intent(v.getContext(), AtualizarFazendaActivity.class);
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



    //DIVERSAS ANOTAÇÕES
    // TODO: FAZER FUNCIONAR O CADASTRAR DO BEBEDOURO E LISTAR
    //TODO: RELACIONAMENTO DOS ANIMAIS E DOS BEBEDOUROS COM A FAZENDA
        //TODO: Mandar um dado para a proxima janela

    //TODO: CALCULAR A DISPONIBILIDADE DE ÁGUA DOS BEBEDOUROS
    //TODO: BEBEDOURO NÃO ESTÁ LISTANDO, CONFERIR!

    //TODO: CALCULAR A NECESSIDADE TOTAL DE ÁGUA PARA OS ANIMAIS

    //usabilidade
    //TODO: REVISAR SE ESTÁ INTUITIVO PARA O USUÁRIO
    //TODO: O QUE INCOMODA SÃO OS TIPOS DE VOLTAR QUE SURGEM E NAO SAO INTUITIVOS;
    //TODO: AS CORES TAMBÉM ESTÃO MEIO APAGADAS NAO COMBINAM

    //PRÓXIMO
    //TODO: PLANÍCIE E PLANALTO ACTIVITY MAIN

}





