package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.StatusProfessor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Professores")
public class Professor extends Pessoa{
    //Atributos
    @Column(nullable = false)
    private double salario;

    @Column(nullable = false, length = 20)
    private String especialidade;

    @Column(nullable = false, length = 50, name = "conta_bancaria")
    private String contaBanco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private StatusProfessor status;

    @OneToMany(mappedBy = "professor",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Modalidade> modalidades;

    @OneToMany(mappedBy = "professor",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Aula> aulas;

    //Getters
    public double getSalario() { return salario; }
    public String getEspecialidade() { return especialidade; }
    public String getContaBanco() { return contaBanco; }
    public StatusProfessor getStatus(){ return status; }

    //Setters
    public void setSalario(double salario) {
        if (salario >= 0.0) this.salario = salario;
        else throw new IllegalArgumentException("O salário não pode ser negativo!");
    }
    public void setEspecialidade(String especialidade) {
        if (especialidade != null && !especialidade.isEmpty()) this.especialidade = especialidade;
        else throw new IllegalArgumentException("A especialização não pode estar vazia!");
    }
    public void setContaBanco(String contaBanco) {
        if(contaBanco != null && !contaBanco.isEmpty()) this.contaBanco = contaBanco;
        else throw new IllegalArgumentException("A conta bancaria não pode estar vazia!");
    }
    public void setStatus(StatusProfessor status) {
        if (status != null) this.status = status;
        else throw new IllegalArgumentException("O status não pode estar vazio!");
    }

    //Construtores
    public Professor(){}
    public Professor(String cpf, String nome, String email, String telefone, LocalDate nascimento,
                     double salario, String especialidade, String contaBanco, StatusProfessor status){
        super(cpf, nome, email, telefone, nascimento);
        setSalario(salario);
        setEspecialidade(especialidade);
        setContaBanco(contaBanco);
        setStatus(status);
    }
}
