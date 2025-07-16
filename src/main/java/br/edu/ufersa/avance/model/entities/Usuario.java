package br.edu.ufersa.avance.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String senha;

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    public void setId(long id) {
        if(id>0) this.id = id;
        else throw new IllegalArgumentException("Id inválida!");
    }
    public void setNome(String nome) {
        if(nome != null) this.nome = nome;
        else throw new IllegalArgumentException("O nome não pode estar vazio!");
    }
    public void setEmail(String email) {
        if(email != null) this.email = email;
        else throw new IllegalArgumentException("O email não pode estar vazio!");
    }
    public void setSenha(String senha) {
        if(senha != null && !senha.isEmpty()) this.senha = senha;
        else throw new IllegalArgumentException("A senha não pode estar vazia!");
    }

    public Usuario(){}
    public Usuario(String nome, String email, String senha){
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }
}
