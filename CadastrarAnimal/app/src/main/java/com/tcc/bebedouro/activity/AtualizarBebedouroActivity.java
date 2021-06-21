package com.tcc.bebedouro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import com.tcc.bebedouro.dao.Bebedouro;
import com.tcc.bebedouro.dao.BebedouroCircular;
import com.tcc.bebedouro.dao.BebedouroRetangular;
import com.tcc.invernada.dao.Invernada;
import com.tcc.main.ObjectBox;
import com.tcc.main.R;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AtualizarBebedouroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private boolean circular = true;
    private int checked;

    private Box<BebedouroCircular> circularBox;
    private Box<BebedouroRetangular> retangularBox;
    private BebedouroRetangular bebRet;
    private BebedouroCircular bebCirc ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualizar_bebedouro);

        findViewById(R.id.txtRaio).setVisibility(View.VISIBLE);
        findViewById(R.id.edtRaio).setVisibility(View.VISIBLE);
        findViewById(R.id.txtAltura).setVisibility(View.VISIBLE);
        findViewById(R.id.edtAltura).setVisibility(View.VISIBLE);
        findViewById(R.id.txtVazao).setVisibility(View.VISIBLE);
        findViewById(R.id.edtVazao).setVisibility(View.VISIBLE);

        findViewById(R.id.edtComprimento).setVisibility(View.GONE);
        findViewById(R.id.txtComprimento).setVisibility(View.GONE);
        findViewById(R.id.edtLargura).setVisibility(View.GONE);
        findViewById(R.id.txtLargura).setVisibility(View.GONE);



        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Ideal", "Moderado", "Ruim"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        Spinner dropdownLimpeza = findViewById(R.id.spinnerLimpeza);
        String[] itemsLimpeza = new String[]{"Ideal", "Moderado", "Ruim"};
        ArrayAdapter<String> adapterLimpeza = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsLimpeza);
        dropdownLimpeza.setAdapter(adapterLimpeza);
        dropdownLimpeza.setOnItemSelectedListener(this);

        Spinner dropdownDistancia = findViewById(R.id.spinnerDistancia);
        String[] itemsDistancia = new String[]{"Ideal", "Moderado", "Ruim"};
        ArrayAdapter<String> adapterDistancia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsDistancia);
        dropdownDistancia.setAdapter(adapterDistancia);
        dropdownDistancia.setOnItemSelectedListener(this);

        RadioGroup groupRadio= findViewById(R.id.groupRadio);
        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                conferirRadio(checkedId);
                checked=checkedId;
            }
        });

        BoxStore boxStore = ObjectBox.get();

        //animal box está funcionando para receber o animal da classe
        circularBox = boxStore.boxFor(BebedouroCircular.class);
        retangularBox = boxStore.boxFor(BebedouroRetangular.class);
        Box<Invernada> invernadaBox = boxStore.boxFor(Invernada.class);


        Button salvar= (Button) findViewById(R.id.btSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             // int opcao = conferirRadio(checked);
                Intent intent = new Intent(v.getContext(), ListarBebedouroActivity.class);

                if ( findViewById(R.id.txtRaio).getVisibility()==v.VISIBLE && circular == true){
                  //BEBEDOURO CIRCULAR
                  String[] items = {"Ideal", "Moderado", "Ruim"};
                  EditText raio = (EditText) findViewById(R.id.edtRaio);
                  EditText vazao = (EditText) findViewById(R.id.edtVazao);

                  EditText altura = (EditText) findViewById(R.id.edtAltura);
                  Spinner condicaoAcesso = (Spinner) findViewById(R.id.spinner1);
                  Spinner limpeza = (Spinner) findViewById(R.id.spinnerLimpeza);
                  Spinner distancia = (Spinner) findViewById(R.id.spinnerDistancia);

                  bebCirc.setAltura(Float.parseFloat(altura.getText().toString()));
                  bebCirc.setCondicaoAcesso(condicaoAcesso.getSelectedItem().toString());
                  bebCirc.setLimpeza(limpeza.getSelectedItem().toString());
                  bebCirc.setRaio(raio.getText().toString());
                  bebCirc.setVazao(vazao.getText().toString());
                  bebCirc.setDistanciaAcesso(distancia.getSelectedItem().toString());

                  circularBox.put(bebCirc);

                  startActivity(intent);

              }
              else if (findViewById(R.id.txtRaio).getVisibility()==v.GONE && circular == false) {
                  //BEBEDOURO RETANGULAR
                  String[] items = {"Ideal", "Moderado", "Ruim"};

                  EditText comprimento = (EditText) findViewById(R.id.edtComprimento);
                  EditText largura = (EditText) findViewById(R.id.edtLargura);

                  EditText altura = (EditText) findViewById(R.id.edtAltura);
                  Spinner condicaoAcesso = (Spinner) findViewById(R.id.spinner1);
                  Spinner limpeza = (Spinner) findViewById(R.id.spinnerLimpeza);
                  Spinner distancia = (Spinner) findViewById(R.id.spinnerDistancia);

                  bebRet.setAltura(Float.parseFloat(altura.getText().toString()));
                  bebRet.setCondicaoAcesso(condicaoAcesso.getSelectedItem().toString());
                  bebRet.setLimpeza(limpeza.getSelectedItem().toString());
                  bebRet.setComprimento(comprimento.getText().toString());
                  bebRet.setLargura(largura.getText().toString());
                  bebRet.setDistanciaAcesso(distancia.getSelectedItem().toString());

                  retangularBox.put(bebRet);
                  startActivity(intent);
              } else {
                    alerta("Não é possivel atualizar o tipo do bebedouro");
              }
            }
        });

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        myChildToolbar.setTitle("Cadastro Bebedouro");
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onResume () {
        super.onResume();

        bebRet = retangularBox.get(Bebedouro.getId_temp());
        bebCirc = circularBox.get(Bebedouro.getId_temp());
        String[] items = new String[]{"Ideal", "Moderado", "Ruim"};
        Integer index = 0;

        if (bebCirc != null) {
            //BEBEDOURO CIRCULAR
            RadioGroup radiog = (RadioGroup) findViewById(R.id.groupRadio);
            ((RadioButton) radiog.getChildAt(0)).setChecked(true);

            findViewById(R.id.rbRetangular).setVisibility(View.GONE);


            EditText raio = (EditText) findViewById(R.id.edtRaio);
            raio.setText(bebCirc.getRaio());

            EditText vazao = (EditText) findViewById(R.id.edtVazao);
            vazao.setText(bebCirc.getVazao());

            EditText altura = (EditText) findViewById(R.id.edtAltura);
            altura.setText(String.valueOf(bebCirc.getAltura()));

            Spinner condicaoAcesso = (Spinner) findViewById(R.id.spinner1);
            index = 0;
            for (String item : items){
                if (item.equals(bebCirc.getCondicaoAcesso())) {
                    condicaoAcesso.setSelection(index);
                }

                index = index + 1;
            }

            Spinner limpeza = (Spinner) findViewById(R.id.spinnerLimpeza);
            index = 0;
            for (String item : items){
                if (item.equals(bebCirc.getLimpeza())) {
                    limpeza.setSelection(index);
                }

                index = index + 1;
            }

            Spinner distancia = (Spinner) findViewById(R.id.spinnerDistancia);
            index = 0;
            for (String item : items){
                if (item.equals(bebCirc.getDistanciaAcesso())) {
                    distancia.setSelection(index);
                }

                index = index + 1;
            }

            circular = true;
        } else {
            //BEBEDOURO RETANGULAR
            RadioGroup radiog = (RadioGroup) findViewById(R.id.groupRadio);
            ((RadioButton) radiog.getChildAt(1)).setChecked(true);

            findViewById(R.id.rbCircular).setVisibility(View.GONE);

            EditText comprimento = (EditText) findViewById(R.id.edtComprimento);
            comprimento.setText(bebRet.getComprimento());

            EditText largura = (EditText) findViewById(R.id.edtLargura);
            largura.setText(bebRet.getLargura());

            EditText altura = (EditText) findViewById(R.id.edtAltura);
            altura.setText(String.valueOf(bebRet.getAltura()));

            Spinner condicaoAcesso = (Spinner) findViewById(R.id.spinner1);
            index = 0;
            for (String item : items) {
                if (item.equals(bebRet.getCondicaoAcesso())) {
                    condicaoAcesso.setSelection(index);
                }
                index = index + 1;
            }

            Spinner limpeza = (Spinner) findViewById(R.id.spinnerLimpeza);
            index = 0;
            for (String item : items) {
                if (item.equals(bebRet.getLimpeza())) {
                    limpeza.setSelection(index);
                }
                index = index + 1;
            }

            Spinner distancia = (Spinner) findViewById(R.id.spinnerDistancia);
            index = 0;
            for (String item : items){
                if (item.equals(bebRet.getDistanciaAcesso())) {
                    distancia.setSelection(index);
                }

                index = index + 1;
            }

            circular = false;
        }

    }

    public void conferirRadio(int checkedId)
    {
        if(checkedId==R.id.rbCircular)
        {
            findViewById(R.id.txtRaio).setVisibility(View.VISIBLE);
            findViewById(R.id.edtRaio).setVisibility(View.VISIBLE);

            findViewById(R.id.txtAltura).setVisibility(View.VISIBLE);
            findViewById(R.id.edtAltura).setVisibility(View.VISIBLE);

            findViewById(R.id.txtVazao).setVisibility(View.VISIBLE);
            findViewById(R.id.edtVazao).setVisibility(View.VISIBLE);

            findViewById(R.id.edtComprimento).setVisibility(View.GONE);
            findViewById(R.id.txtComprimento).setVisibility(View.GONE);

            findViewById(R.id.edtLargura).setVisibility(View.GONE);
            findViewById(R.id.txtLargura).setVisibility(View.GONE);


        }
        else if(checkedId==R.id.rbRetangular)
        {
            findViewById(R.id.txtComprimento).setVisibility(View.VISIBLE);
            findViewById(R.id.edtComprimento).setVisibility(View.VISIBLE);

            findViewById(R.id.txtAltura).setVisibility(View.VISIBLE);
            findViewById(R.id.edtAltura).setVisibility(View.VISIBLE);

            findViewById(R.id.txtLargura).setVisibility(View.VISIBLE);
            findViewById(R.id.edtLargura).setVisibility(View.VISIBLE);

            findViewById(R.id.txtRaio).setVisibility(View.GONE);
            findViewById(R.id.edtRaio).setVisibility(View.GONE);


            findViewById(R.id.txtVazao).setVisibility(View.GONE);
            findViewById(R.id.edtVazao).setVisibility(View.GONE);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void alerta(String mensagem){
        //funcao pra exibir mensagem básica ao usuario
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }


}
