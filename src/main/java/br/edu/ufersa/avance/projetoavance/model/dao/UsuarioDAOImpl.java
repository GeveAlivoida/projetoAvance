package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Usuario;
import br.edu.ufersa.avance.projetoavance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO{
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Usuario usuario) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.persist(usuario);
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
    public void atualizar(Usuario usuario) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.merge(usuario);
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
    public void excluir(Usuario usuario) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
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
    public List<Usuario> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Usuario", Usuario.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario buscarPorId(long id) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Usuario.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
