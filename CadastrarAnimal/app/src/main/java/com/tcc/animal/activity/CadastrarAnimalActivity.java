package com.tcc.animal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.tcc.animal.dao.Animal;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.MainActivity;
import com.tcc.main.R;
import com.tcc.main.ObjectBox;

import com.tcc.relatorio.activity.ListarRelatorioPorFazendaActivity;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CadastrarAnimalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, ListarAnimaisActivity.class);

            //inicializar a tela
            super.onCreate(savedInstanceState);
            setContentView(R.layout.cadastrar_animal);
            String nome = "";
            int quantidade = 0;


            BoxStore boxStore = ObjectBox.get();

            long id = Invernada.getId_temp();
            Box<Invernada> invernadaBox = boxStore.boxFor(Invernada.class);
            Box<Animal> animalBox = boxStore.boxFor(Animal.class);
            Button salvar = (Button) findViewById(R.id.btSalvar);
            salvar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                            EditText quantidadeedt = (EditText) findViewById(R.id.edtCompr);
                            Spinner spinner = (Spinner) findViewById(R.id.spinnerTipo);
                            RadioGroup radiog = (RadioGroup) findViewById(R.id.rg);

                            int checked=0;
                            RadioButton choice = (RadioButton) findViewById(radiog.getCheckedRadioButtonId());


                            Invernada invernada = invernadaBox.get(Invernada.getId_temp());
                            Animal animal = new Animal(Integer.parseInt(quantidadeedt.getText().toString()), spinner.getSelectedItem().toString(), (String) choice.getText());
                            animal.invernada.setTarget(invernada);
                            animalBox.put(animal);

                            invernada.animais.reset();
                            invernada.animais.add(animal);
                            invernadaBox.put(invernada);
                            startActivity(intent);

                    }
                    catch (Exception e){
                        startActivity(intent);
                    }
                }
            });


        Spinner spinner = findViewById(R.id.spinnerTipo);
            String[] tiposRebanho = new String[]{"Vacas de cria", "Touro", "Novilha de 2 a 3 anos", "Novilhas de 1 a 2 anos", "Bezerras", "Garrote de 2 a 3 anos", "Garrote de 1 a 2 anos", "Bezerros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tiposRebanho);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        myChildToolbar.setTitle("Cadastro Animal");
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
