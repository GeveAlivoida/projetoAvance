package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Modalidade;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class AulaDAOImpl extends GeralDAOImpl<Aula> implements AulaDAO{
    @Override
    public Aula buscarPorId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Aula.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Aula", Aula.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarPorAluno(Aluno aluno) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aula a JOIN a.alunos al WHERE al = :aluno", Aula.class)
                    .setParameter("aluno", aluno)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarPorModalidade(Modalidade modalidade) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aula a WHERE a.modalidade = :modalidade", Aula.class)
                    .setParameter("modalidade", modalidade)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aula a WHERE a.data BETWEEN :inicio AND :fim", Aula.class)
                    .setParameter("inicio", dataInicio)
                    .setParameter("fim", dataFim)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
