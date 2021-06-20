package com.tcc.animal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import com.tcc.animal.dao.Animal;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AtualizarAnimalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Animal animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, ListarAnimaisActivity.class);

        //inicializar a tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualizar_animal);
        String nome = "";
        int quantidade = 0;
        String[] tiposRebanho = new String[]{"Vacas de cria", "Touro", "Novilha de 2 a 3 anos", "Novilhas de 1 a 2 anos", "Bezerras", "Garrote de 2 a 3 anos", "Garrote de 1 a 2 anos", "Bezerros"};



        BoxStore boxStore = ObjectBox.get();

        long id = Invernada.getId_temp();
        Box<Invernada> invernadaBox = boxStore.boxFor(Invernada.class);
        Box<Animal> animalBox = boxStore.boxFor(Animal.class);
        animal = animalBox.get(Animal.getId_temp());


        EditText quantidadeedt = (EditText) findViewById(R.id.edtCompr);
        quantidadeedt.setText(String.valueOf(animal.getQuantidade()));



        String[] relevo = new String[]{"planicie", "planalto"};
        RadioGroup radiog = (RadioGroup) findViewById(R.id.rg);
        Integer index = 0;
        for(String item : relevo) {
            if (item.equals(animal.getRelevo())) {
                ((RadioButton) radiog.getChildAt(index)).setChecked(true);
            }
            index = index + 1;
        }


        Button salvar = (Button) findViewById(R.id.btSalvar);
            salvar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                            EditText quantidadeedt = (EditText) findViewById(R.id.edtCompr);
                            Spinner spinner = (Spinner) findViewById(R.id.spinnerTipo);
                            RadioGroup radiog = (RadioGroup) findViewById(R.id.rg);
                            RadioButton choice = (RadioButton) findViewById(radiog.getCheckedRadioButtonId());

                            animal.setQuantidade(Integer.parseInt(quantidadeedt.getText().toString()));
                            animal.setTipo(spinner.getSelectedItem().toString());
                            animal.setRelevo((String) choice.getText());

                            animalBox.put(animal);

                            startActivity(intent);
                    }
                    catch (Exception e){
                        startActivity(intent);
                    }
                }
            });

        Spinner spinner = findViewById(R.id.spinnerTipo);
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
    public void onResume() {
        super.onResume();
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTipo);
        Integer index = 0;
        String[] tiposRebanho = new String[]{"Vacas de cria", "Touro", "Novilha de 2 a 3 anos", "Novilhas de 1 a 2 anos", "Bezerras", "Garrote de 2 a 3 anos", "Garrote de 1 a 2 anos", "Bezerros"};

        for (String item : tiposRebanho) {
            if (item.equals(animal.getTipo())) {
                spinner.setSelection(index);
            }
            index = index + 1;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
