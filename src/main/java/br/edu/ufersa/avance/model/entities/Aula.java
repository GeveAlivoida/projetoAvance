package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.StatusAula;

import java.time.LocalDate;

public class Aula {
    //Atributos
    private long id;
    private Modalidade modalidade;
    private LocalDate data;
    private String local;
    private StatusAula status;

    //Getters
    public long getId() { return id; }
    public Modalidade getModalidade() { return modalidade; }
    public LocalDate getData() { return data; }
    public String getLocal() { return local; }
    public StatusAula getTipo() { return status; }

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
    public void setStatus(StatusAula status) {
        if (status != null) this.status = status;
        else throw new IllegalArgumentException("O tipo da aula não pode estar vazio!");}

    //Construtores
    public Aula(){}
    public Aula(Modalidade modalidade, LocalDate data, String local, StatusAula status){
        setModalidade(modalidade);
        setData(data);
        setLocal(local);
        setStatus(status);
    }
}
