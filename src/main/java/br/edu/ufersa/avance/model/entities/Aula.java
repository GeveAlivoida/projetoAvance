package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.StatusAula;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Aulas")
public class Aula {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_modalidade")
    private Modalidade modalidade;

    @ManyToMany
    @JoinTable(
            name = "aula_aluno",
            joinColumns = @JoinColumn(name = "id_aula"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    private List<Aluno> alunos;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_professor")
    private Professor professor;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, length = 100)
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusAula status;

    //Getters
    public long getId() { return id; }
    public Modalidade getModalidade() { return modalidade; }
    public List<Aluno> getAluno() { return alunos; }
    public Professor getProfessor() { return professor; }
    public LocalDate getData() { return data; }
    public String getLocal() { return local; }
    public StatusAula getTipo() { return status; }

    //Setters
    public void setModalidade(Modalidade modalidade) {
        if (modalidade != null) this.modalidade = modalidade;
        else throw new IllegalArgumentException("A modalidade não pode estar vazia!");
    }
    public void setAluno(List<Aluno> alunos) {
        if (alunos != null) this.alunos = alunos;
        else throw new IllegalArgumentException("O campo alunos não pode estar vazio!");
    }
    public void setProfessor(Professor professor) {
        if (professor != null) this.professor = professor;
        else throw new IllegalArgumentException("O professor não pode estar vazio!");
    }
    public void setData(LocalDate data) {
        if (data != null) this.data = data;
        else throw new IllegalArgumentException("A data da aula não pode estar vazia!");
    }
    public void setLocal(String local) {
        if (local != null) this.local = local;
        else throw new IllegalArgumentException("O local da aula não pode estar vazio!");
    }
    public void setStatus(StatusAula status) {
        if (status != null) this.status = status;
        else throw new IllegalArgumentException("O tipo da aula não pode estar vazio!");}

    //Construtores
    public Aula(){}
    public Aula(Modalidade modalidade, List<Aluno> alunos, Professor professor, LocalDate data, String local,
                StatusAula status){
        setModalidade(modalidade);
        setAluno(alunos);
        setProfessor(professor);
        setData(data);
        setLocal(local);
        setStatus(status);
    }

    //Métodos
    public void adicionarAluno(Aluno aluno){
        if(aluno != null) alunos.add(aluno);
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }

    public void removerAluno(Aluno aluno){
        if(aluno != null)
            if (alunos.contains(aluno)) alunos.remove(aluno);
            else throw new IllegalArgumentException("Esse aluno não está presente na lista de alunnos desta aula!");
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }
}
