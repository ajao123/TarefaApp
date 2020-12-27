package com.allisson.tarefas.model;

import android.text.Editable;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class Tarefa implements Serializable {

    private String id = "";
    private String nomeTarefa;
    private Boolean concluida;
    private Long timestamp;
    public Tarefa() {

    }

    public Tarefa(String id, String nomeTarefa, Boolean concluida, Long timestamp) {
        this.id = id;
        this.nomeTarefa = nomeTarefa;
        this.concluida = concluida;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "nomeTarefa='" + nomeTarefa + '\'' +
                ", concluida=" + concluida +
                '}';
    }
}
