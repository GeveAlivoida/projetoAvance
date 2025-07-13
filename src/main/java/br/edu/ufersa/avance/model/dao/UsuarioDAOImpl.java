package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Usuario;
import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO{
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Usuario usuario) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.merge(usuario);
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(Usuario usuario) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
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

    @Override
    public Usuario buscarPorSenha(String senha) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT u FROM Usuario u WHERE u.senha = :senha", Usuario.class)
                    .setParameter("senha", senha)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
