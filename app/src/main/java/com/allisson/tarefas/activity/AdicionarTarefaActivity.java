package com.allisson.tarefas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.allisson.tarefas.R;
import com.allisson.tarefas.firebase.ControllerFirebase;
import com.allisson.tarefas.helper.TarefaDAO;
import com.allisson.tarefas.model.Tarefa;
import com.allisson.tarefas.model.TarefaDTO;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.ServerValue;

import java.util.UUID;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private CheckBox checkConcluida;

    private Tarefa tarefaAtual;
    private ControllerFirebase controllerFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        controllerFirebase = new ControllerFirebase(AdicionarTarefaActivity.this);

        //databaseReference.child("teste").setValue(ServerValue.TIMESTAMP);
        //databaseReference.child("dolar").setValue("real");
        editTarefa = findViewById(R.id.textTarefa);
        checkConcluida =  findViewById(R.id.checkConcluida);

        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        if( tarefaAtual != null ){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
            checkConcluida.setChecked(tarefaAtual.getConcluida());
        }

    }
    /*
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(AdicionarTarefaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.itemSalvar:
                if (!editTarefa.getText().toString().equals("")) {

                    TarefaDTO tarefa = new TarefaDTO();
                    if (tarefaAtual == null) {
                        tarefa.setId(UUID.randomUUID().toString());
                    } else {
                        tarefa.setId(tarefaAtual.getId());
                    }
                    tarefa.setNomeTarefa(editTarefa.getText().toString());
                    tarefa.setConcluida(checkConcluida.isChecked());
                    tarefa.setTimestamp(ServerValue.TIMESTAMP);

                    //databaseReference.child("tarefas").child(tarefa.getId()).setValue(tarefa);
                    controllerFirebase.salvarTarefa(tarefa);

                    if (tarefa.getConcluida()) {
                        Intent intent = new Intent(getApplicationContext(), TarefaCompleta.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
