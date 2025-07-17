package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Aula;
import br.edu.ufersa.avance.projetoavance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoavance.util.JPAUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AulaDAOImpl implements AulaDAO{
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Aula aula) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();

            if (aula.getModalidade() != null && aula.getModalidade().getId() > 0) {
                aula.setModalidade(em.merge(aula.getModalidade()));
            }
            if (aula.getProfessor() != null && aula.getProfessor().getId() > 0) {
                aula.setProfessor(em.merge(aula.getProfessor()));
            }
            if (aula.getAlunos() != null) {
                aula.setAlunos(aula.getAlunos().stream()
                        .map(em::merge)
                        .collect(Collectors.toList()));
            }

            em.persist(aula);
            em.getTransaction().commit();
        }
        catch (Throwable e) {
            if (ts != null && ts.isActive())
                ts.rollback();
            throw new RuntimeException("Falha ao criar EntityManager " + e);
        }
        finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void atualizar(Aula aula) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            em.merge(aula);
            ts.commit();
        }
        catch (Throwable e) {
            if (ts != null && ts.isActive())
                ts.rollback();
            throw new RuntimeException("Falha ao criar EntityManager " + e);
        }
        finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void excluir(Aula aula) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();

            Aula aulaGerenciada = em.merge(aula);
            em.remove(aulaGerenciada);

            ts.begin();
            ts.commit();
        }
        catch (Throwable e) {
            if (ts != null && ts.isActive())
                ts.rollback();
            throw new RuntimeException("Falha ao criar EntityManager " + e);
        }
        finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public Aula buscarPorId(long id) {
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
