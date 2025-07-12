package br.edu.ufersa.avance.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Responsaveis")
public class Responsavel extends Pessoa {
    //Atributos
    @OneToMany(mappedBy = "responsavel",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    List<Aluno> dependentes = new ArrayList<>();

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

    //Métodos
    public void adicionarDependente(Aluno aluno){
        if(aluno != null) {
            dependentes.add(aluno);
            aluno.setResponsavel(this);
        }
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }

    public void removerDependente(Aluno aluno){
        if(aluno != null)
            if (dependentes.contains(aluno)) {
                dependentes.remove(aluno);
                aluno.setResponsavel(null);
            }
            else throw new IllegalArgumentException("Esse aluno não está presente na lista de dependentes deste responsável!");
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }
}
