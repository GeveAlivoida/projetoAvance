package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.StatusProfessor;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Professores")
public class Professor extends Pessoa{
    //Atributos
    @Column(nullable = false)
    private double salario;

    @Column(nullable = false, length = 20)
    private String especializacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private StatusProfessor status;

    //Getters
    public double getSalario() { return salario; }
    public String getEspecializacao() { return especializacao; }
    public StatusProfessor getStatus(){ return status; }

    //Setters
    public void setSalario(double salario) {
        if (salario >= 0.0) this.salario = salario;
        else throw new IllegalArgumentException("O salário não pode ser negativo!");
    }
    public void setEspecializacao(String especializacao) {
        if (especializacao != null && !especializacao.isEmpty()) this.especializacao = especializacao;
        else throw new IllegalArgumentException("A especialização não pode estar vazia!");
    }
    public void setStatus(StatusProfessor status) {
        if (status != null) this.status = status;
        else throw new IllegalArgumentException("O status não pode estar vazio!");
    }

    //Construtores
    public Professor(){}
    public Professor(String cpf, String nome, String email, String telefone, LocalDate nascimento,
            double salario, String especializacao, StatusProfessor status){
        super(cpf, nome, email, telefone, nascimento);
        setSalario(salario);
        setEspecializacao(especializacao);
        setStatus(status);
    }
}
