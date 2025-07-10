package br.edu.ufersa.avance.model.entities;

import java.time.LocalDate;

public class Responsavel extends Pessoa {
    //Atributos
    private long id;

    //Getters
    public long getId() { return id; }

    //Construtores
    public Responsavel(){}
    public Responsavel(String cpf, String nome, String email, String telefone, LocalDate nascimento){
        super(cpf, nome, email, telefone, nascimento);
    }
}
