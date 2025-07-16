package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.StatusAula;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Aulas")
public class Aula {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_modalidade")
    private Modalidade modalidade;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "aula_aluno",
            joinColumns = @JoinColumn(name = "id_aula"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    private List<Aluno> alunos;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_professor")
    private Professor professor;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusAula status;

    //Getters
    public long getId() { return id; }
    public Modalidade getModalidade() { return modalidade; }
    public List<Aluno> getAlunos() { return alunos; }
    public Professor getProfessor() { return professor; }
    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public StatusAula getStatus() { return status; }

    //Setters
    public void setId(long id) {
        if(id>0) this.id = id;
        else throw new IllegalArgumentException("Id inválida!");
    }
    public void setModalidade(Modalidade modalidade) {
        if (modalidade != null) this.modalidade = modalidade;
        else throw new IllegalArgumentException("A modalidade não pode estar vazia!");
    }
    public void setAlunos(List<Aluno> alunos) {
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
    public void setHorario(LocalTime horario) {
        if(horario != null) this.horario = horario;
        else throw new IllegalArgumentException("O horário não pode estar vazio!");
    }
    public void setStatus(StatusAula status) {
        if (status != null) this.status = status;
        else throw new IllegalArgumentException("O tipo da aula não pode estar vazio!");}

    //Construtores
    public Aula(){}
    public Aula(Modalidade modalidade, List<Aluno> alunos, Professor professor, LocalDate data,
                LocalTime horario, StatusAula status){
        setModalidade(modalidade);
        setAlunos(alunos);
        setProfessor(professor);
        setData(data);
        setHorario(horario);
        setStatus(status);
    }

    //Métodos
    public void adicionarAluno(Aluno aluno){
        if(aluno != null) {
            alunos.add(aluno);
            aluno.getAulas().add(this);
        }
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }
    public void removerAluno(Aluno aluno){
        if(aluno != null) {
            if (alunos.contains(aluno)) {
                alunos.remove(aluno);
                aluno.getAulas().remove(this);
            } else throw new IllegalArgumentException("Esse aluno não está presente na lista de alunos desta aula!");
        }
        else throw new IllegalArgumentException("O aluno não pode estar vazio!");
    }
}
