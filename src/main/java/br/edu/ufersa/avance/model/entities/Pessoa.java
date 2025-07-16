package br.edu.ufersa.avance.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Pessoas")
public abstract class Pessoa {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 15)
    private String cpf;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, name = "data_nascimento")
    private LocalDate nascimento;

    //Getters
    public long getId() { return id; }
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public LocalDate getNascimento() { return nascimento; }

    //Setters

    public void setId(long id) {
        if(id>0) this.id = id;
        else throw new IllegalArgumentException("Id inválida!");
    }
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

    //Métodos
    public int calcIdade(){
        if(this.getNascimento() == null) throw new IllegalStateException("Data de nascimento não informada!");
        else return Period.between(this.getNascimento(), LocalDate.now()).getYears();
    }
}
