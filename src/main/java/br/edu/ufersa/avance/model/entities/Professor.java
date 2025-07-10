package br.edu.ufersa.avance.model.entities;

import java.time.LocalDate;

public class Professor extends Pessoa{
    //Atributos
    private long id;
    private double salario;
    private boolean status;

    //Getters
    public long getId() { return id; }
    public double getSalario() { return salario; }
    public boolean getStatus(){ return status; }

    //Setters
    public void setSalario(double salario) {
        if (salario >= 0.0) this.salario = salario;
        else throw new IllegalArgumentException("O salário não pode ser negativo!");
    }
    public void setStatus(boolean status) { this.status = status; }

    //Construtores
    public Professor(){}
    public Professor(String cpf, String nome, String email, String telefone, LocalDate nascimento,
            double salario, boolean status){
        super(cpf, nome, email, telefone, nascimento);
        setSalario(salario);
        setStatus(status);
    }
}
