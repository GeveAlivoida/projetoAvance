package br.edu.ufersa.avance.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Aluno")
public class Aluno extends Pessoa {
    //Atributos
    private Responsavel responsavel;

    //Getters
    public Responsavel getResponsavel() { return responsavel; }

    //Setters
    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    //Construtores
    public Aluno(){}
    public Aluno(String cpf, String nome, String email, String telefone, LocalDate nascimento){
        super(cpf, nome, email, telefone, nascimento);
    }
    public Aluno(String cpf, String nome, String email, String telefone, LocalDate nascimento, Responsavel responsavel){
        this(cpf, nome, email, telefone, nascimento);
        setResponsavel(responsavel);
    }
}
