package br.edu.ufersa.avance.model.entities;

import java.time.LocalDate;

public abstract class Pessoa {
    //Atributos
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate nascimento;

    //Getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public LocalDate getNascimento() { return nascimento; }

    //Setters
    public void setCpf(String cpf) {
        if(cpf != null && !cpf.isEmpty()) this.cpf = cpf;
        else throw new IllegalArgumentException("O CPF não pode estar vazio!");
    }
    public void setNome(String nome) {
        if(nome != null && !nome.isEmpty()) this.nome = nome;
        else throw new IllegalArgumentException("O nome não pode estar vazio!");
    }
    public void setEmail(String email) {
        if(email != null && !email.isEmpty()) this.email = email;
        else throw new IllegalArgumentException("O email não pode estar vazio!");
    }
    public void setTelefone(String telefone) {
        if(telefone != null && !telefone.isEmpty()) this.telefone = telefone;
        else throw new IllegalArgumentException("O número de telefone não pode estar vazio!");
    }
    public void setNascimento(LocalDate nascimento) {
        if(nascimento != null) this.nascimento = nascimento;
        else throw new IllegalArgumentException("A data de nascimento não pode estar vazia!");
    }

    //Construtores
    public Pessoa(){}
    public Pessoa(String cpf, String nome, String email, String telefone, LocalDate nascimento){
        setCpf(cpf);
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
        setNascimento(nascimento);
    }
}
