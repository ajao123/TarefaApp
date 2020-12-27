package com.allisson.tarefas.model;

import java.io.Serializable;
import java.util.Map;

public class TarefaDTO implements Serializable {

    private String id = "";
    private String nomeTarefa;
    private Boolean concluida;
    private Map<String, String> timestamp;

    public TarefaDTO() {

    }

    public TarefaDTO(String id, String nomeTarefa, Boolean concluida, Map<String, String> timestamp) {
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

    public void setTimestamp(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

}
