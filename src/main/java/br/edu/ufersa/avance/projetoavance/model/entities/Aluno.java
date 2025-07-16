package br.edu.ufersa.avance.projetoAvance.model.entities;

import br.edu.ufersa.avance.projetoAvance.exceptions.FullVacanciesException;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Alunos")
public class Aluno extends Pessoa {
    //Atributos
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(nullable = true, name = "id_responsavel")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Responsavel responsavel;

    @ManyToMany(mappedBy = "alunos",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private List<Modalidade> modalidades;

    @ManyToMany(mappedBy = "alunos",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Aula> aulas;

    @OneToMany(mappedBy = "aluno",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Pagamento> pagamentos;

    //Getters
    public Responsavel getResponsavel() { return responsavel; }
    public List<Modalidade> getModalidades() { return modalidades; }
    public List<Aula> getAulas() { return aulas; }
    public List<Pagamento> getPagamentos() { return pagamentos; }

    //Setters
    //Não há verificação se o responsável é null ou não pois um aluno pode não ter responsável
    public void setResponsavel(Responsavel responsavel) { this.responsavel = responsavel; }
    public void setModalidades(List<Modalidade> modalidades) {
        this.modalidades = modalidades != null ? modalidades : new ArrayList<>();;
    }
    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas != null ? aulas : new ArrayList<>();
    }
    public void setPagamentos(List<Pagamento> pagamentos) { this.pagamentos = pagamentos; }

    //Construtores
    public Aluno(){
        this.modalidades = new ArrayList<>();
        this.aulas = new ArrayList<>();
    }
    public Aluno(String cpf, String nome, String email, String telefone, LocalDate nascimento,
                 Responsavel responsavel, List<Modalidade> modalidades, List<Aula> aulas){
        super(cpf, nome, email, telefone, nascimento);
        setResponsavel(responsavel);
        setModalidades(modalidades);
        setAulas(aulas);
    }

    //Métodos
    public void adicionarAula(Aula aula){
        if(aula != null){
            if(!aulas.contains(aula)) {
                this.aulas.add(aula);
                aula.getAlunos().add(this);
            }
            else throw new IllegalArgumentException("Esse aluno já está cadastrado nessa aula!");
        }
        else throw new IllegalArgumentException("A aula não pode estar vazia!");
    }

    public void removerAula(Aula aula){
        if(aula != null){
            if(aulas.contains(aula)) {
                this.aulas.remove(aula);
                aula.getAlunos().remove(this);
            }
            else throw new IllegalArgumentException("Essa aula não está presente na lista de aulas deste aluno!");
        }
        else throw new IllegalArgumentException("A aula não pode ser vazia!");
    }

    public void adicionarModalidade(Modalidade modalidade){
        if(modalidade != null){
            if(!modalidades.contains(modalidade)) {
                if (modalidade.temVaga()) {
                    this.modalidades.add(modalidade);
                    modalidade.getAlunos().add(this);
                }
                else throw new FullVacanciesException();
            }
            else throw new IllegalArgumentException("Esse aluno já está matriculado nessa modalidade!");
        }
        else throw new IllegalArgumentException("A aula não pode estar vazia!");
    }

    public void removerModalidade(Modalidade modalidade){
        if(modalidade != null){
            if(modalidades.contains(modalidade)) {
                this.modalidades.remove(modalidade);
                modalidade.getAlunos().remove(this);
            }
            else throw new IllegalArgumentException("Esse aluno não está matriculado nessa modalidade!");
        }
        else throw new IllegalArgumentException("A aula não pode ser vazia!");
    }
}
