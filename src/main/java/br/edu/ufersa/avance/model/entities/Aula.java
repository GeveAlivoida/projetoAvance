package br.edu.ufersa.avance.model.entities;

import java.time.LocalDate;

public class Aula {
    //Atributos
    private long id;
    private Modalidade modalidade;
    private LocalDate data;
    private String local;
    private boolean status;

    //Getters
    public long getId() { return id; }
    public Modalidade getModalidade() { return modalidade; }
    public LocalDate getData() { return data; }
    public String getLocal() { return local; }
    public boolean getStatus() { return status; }

    //Setters
    public void setModalidade(Modalidade modalidade) {
        if (modalidade != null) this.modalidade = modalidade;
        else throw new IllegalArgumentException("A modalidade não pode estar vazia!");
    }
    public void setData(LocalDate data) {
        if (data != null) this.data = data;
        else throw new IllegalArgumentException("A data da aula não pode estar vazia!");
    }
    public void setLocal(String local) {
        if (local != null) this.local = local;
        else throw new IllegalArgumentException("O local da aula não pode estar vazio!");
    }
    public void setStatus(boolean status) { this.status = status; }

    //Construtores
    public Aula(){}
    public Aula(Modalidade modalidade, LocalDate data, String local, boolean status){
        setModalidade(modalidade);
        setData(data);
        setLocal(local);
        setStatus(status);
    }
}
