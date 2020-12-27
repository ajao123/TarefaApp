package com.allisson.tarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.allisson.tarefas.model.ITarefaDAO;
import com.allisson.tarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        cv.put("concluida", tarefa.getConcluida());

        try{
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        cv.put("concluida", tarefa.getConcluida());

        String[] args = {"1"};

        try{
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{
            String[] args = {"1"};
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Tarefa removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar(Boolean status) {
        List<Tarefa> tarefas = new ArrayList<>();
        String[] args = (status)?new String[]{"1"}: new String[]{"0"};


        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " WHERE concluida=?1;";
        Cursor c = le.rawQuery(sql, args);

        while (c.moveToNext()){
            Tarefa tarefa = new Tarefa();
            Long id =  c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            Integer conclusao = c.getInt(c.getColumnIndex("concluida"));
            Boolean concluida = (conclusao == 1)? true : false;

            //tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefa.setConcluida(concluida);
            Log.i("Teste: ", "Conclusao: " + conclusao + " - Concluida:" + concluida);
            tarefas.add(tarefa);
        }

        return tarefas;
    }
}







