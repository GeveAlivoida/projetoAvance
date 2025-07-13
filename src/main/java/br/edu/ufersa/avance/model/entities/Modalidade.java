package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.TipoModalidade;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Modalidades")
public class Modalidade {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_professor")
    private Professor professor;

    @ManyToMany
    @JoinTable(
            name = "modalidade_aluno",
            joinColumns = @JoinColumn(name = "id_modalidade"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    private List<Aluno> alunos;

    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 11)
    private TipoModalidade tipo;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private int vagas;

    @Column(nullable = false, name = "idade_minima")
    private int idadeMin;

    //Getters
    public long getId() {return id;}
    public Professor getProfessor() { return professor; }
    public List<Aluno> getAlunos() { return alunos; }
    public String getNome() { return nome; }
    public TipoModalidade getTipo() { return tipo; }
    public double getValor() { return valor; }
    public int getVagas() { return vagas; }
    public int getIdadeMin() { return idadeMin; }

    //Setters
    public void setProfessor(Professor professor) {
        if (professor != null) this.professor = professor;
        else throw new IllegalArgumentException("O campo professor não pode estar vazio!");
    }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }
    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) this.nome = nome;
        else throw new IllegalArgumentException("O nome não pode estar vazio!");
    }
    public void setTipo(TipoModalidade tipo) {
        if (tipo != null) this.tipo = tipo;
        else throw new IllegalArgumentException("O tipo não pode estar vazio!");
    }
    public void setValor(double valor) {
        if (valor >= 0.0) this.valor = valor;
        else throw new IllegalArgumentException("O valor da aula não pode ser negativo!");
    }
    public void setVagas(int vagas) {
        if (vagas >= 0) this.vagas = vagas;
        else throw new IllegalArgumentException("O número de vagas não pode ser negativo!");
    }
    public void setIdadeMin(int idadeMin) {
        if (idadeMin >= 0) this.idadeMin = idadeMin;
        else throw new IllegalArgumentException("Idade não pode ser um número negativo!");
    }

    //Construtores
    public Modalidade(){}
    public Modalidade(Professor professor, List<Aluno> alunos, String nome, TipoModalidade tipo, double valor, int vagas, int idadeMin){
        setProfessor(professor);
        setAlunos(alunos);
        setNome(nome);
        setTipo(tipo);
        setValor(valor);
        setVagas(vagas);
        setIdadeMin(idadeMin);
    }

    //Métodos
    public boolean temVaga(){ return alunos.size() < vagas; }

    public void adicionarAluno(Aluno aluno){
        if(aluno != null) {
            alunos.add(aluno);
            aluno.getModalidades().add(this);
        }
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }
    public void removerAluno(Aluno aluno){
        if(aluno != null) {
            if (alunos.contains(aluno)) {
                alunos.remove(aluno);
                aluno.getModalidades().remove(this);
            } else throw new IllegalArgumentException("Esse aluno não está presente na lista de alunos desta modalidade!");
        }
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }
}
