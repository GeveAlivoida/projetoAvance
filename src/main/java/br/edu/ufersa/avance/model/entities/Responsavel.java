package br.edu.ufersa.avance.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Responsavel extends Pessoa {
    //Atributos
    List<Aluno> dependentes = new ArrayList<Aluno>();

    //Getters
    public List<Aluno> getDependentes() { return dependentes; }

    //Setters
    public void setDependentes(List<Aluno> dependentes) {
        if(dependentes != null && !dependentes.isEmpty()) this.dependentes = dependentes;
        else throw new IllegalArgumentException("O campo dependentes não pode estar vazio!");
    }

    //Construtores
    public Responsavel(){}
    public Responsavel(String cpf, String nome, String email, String telefone, LocalDate nascimento,
                       List<Aluno> dependentes){
        super(cpf, nome, email, telefone, nascimento);
        setDependentes(dependentes);
    }
}
