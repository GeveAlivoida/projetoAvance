package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.*;

public abstract class GeralDAOImpl<T> implements GeralDAO<T> {
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void salvar(T entity) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Throwable e) {
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(T entity) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.merge(entity);
            ts.commit();
        } catch (Throwable e) {
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(T entity) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            ts.commit();
        } catch (Throwable e) {
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
