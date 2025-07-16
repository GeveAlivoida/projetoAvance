package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.List;

public class ProfessorDAOImpl implements ProfessorDAO{
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Professor professor) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.persist(professor);
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
    public void atualizar(Professor professor) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.merge(professor);
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
    public void excluir(Professor professor) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(professor) ? professor : em.merge(professor));
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
    public Professor buscarPorId(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Professor.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Professor> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Professor", Professor.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Professor> buscarPorNome(String nome) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE LOWER(p.nome) LIKE LOWER(:nome)", Professor.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Professor buscarPorCpf(String cpf) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.cpf = :cpf", Professor.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Professor buscarPorEmail(String email) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.email = :email", Professor.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Professor buscarPorTelefone(String telefone) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.telefone = :telefone", Professor.class)
                    .setParameter("telefone", telefone)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<Professor> buscarPorStatus(StatusProfessor status) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.status = :status", Professor.class)
                    .setParameter("status", status)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
