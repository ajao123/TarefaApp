package com.allisson.tarefas.firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allisson.tarefas.activity.MainActivity;
import com.allisson.tarefas.adapter.TarefaAdapter;
import com.allisson.tarefas.model.Tarefa;
import com.allisson.tarefas.model.TarefaDTO;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ControllerFirebase {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> tarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;
    private ControllerFirebase controllerFirebase;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Boolean concluida;


    public ControllerFirebase(Context context) {
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        this.context = context;
    }

    public ControllerFirebase(Context context, RecyclerView recyclerView, Boolean concluida) {
        FirebaseApp.initializeApp(context);
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            databaseReference = firebaseDatabase.getReference();
            databaseReference.keepSynced(true);
        }catch (Exception e){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
        }

        this.recyclerView = recyclerView;
        this.context = context;
        this.concluida = concluida;
    }

    public void carregarListaTarefas(final RecyclerView recyclerView){
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        tarefas = new ArrayList<>();

        databaseReference.child("tarefas").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tarefas.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.i("timeeee", dataSnapshot.child("timestamp").getValue().toString());

                    Tarefa tarefa = dataSnapshot.getValue(Tarefa.class);
                    if(tarefa.getConcluida() == concluida){
                        tarefas.add(tarefa);
                    }

                }
                tarefaAdapter = new TarefaAdapter(tarefas);
                recyclerView.setAdapter(tarefaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteTarefa(String id){

        databaseReference.child("tarefas").child(id).removeValue();
        //
        // carregarListaTarefas(recyclerView);

    }

    public void salvarTarefa(TarefaDTO tarefa){
        try {
            databaseReference.child("tarefas").child(tarefa.getId()).setValue(tarefa);
        }catch (Exception e){
            databaseReference.onDisconnect().setValue("I disconnected!");
        }

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public TarefaAdapter getTarefaAdapter() {
        return tarefaAdapter;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public Tarefa getTarefaSelecionada() {
        return tarefaSelecionada;
    }

    public ControllerFirebase getControllerFirebase() {
        return controllerFirebase;
    }

    public Context getContext() {
        return context;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }


}
