package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;
import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.List;

public class ResponsavelDAOImpl implements ResponsavelDAO{
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Responsavel responsavel) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(responsavel);
            em.getTransaction().commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(Responsavel responsavel) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.merge(responsavel);
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(Responsavel responsavel) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(responsavel) ? responsavel : em.merge(responsavel));
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Responsavel buscarPorId(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Responsavel.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Responsavel> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Responsavel", Responsavel.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Responsavel> buscarPorNome(String nome) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Responsavel r WHERE LOWER(r.nome) LIKE LOWER(:nome)", Responsavel.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Responsavel buscarPorCpf(String cpf) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Responsavel r WHERE r.cpf = :cpf", Responsavel.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Responsavel buscarPorEmail(String email) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Responsavel r WHERE r.email = :email", Responsavel.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Responsavel buscarPorTelefone(String telefone) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Responsavel r WHERE r.telefone = :telefone", Responsavel.class)
                    .setParameter("telefone", telefone)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Responsavel buscarPorAluno(Aluno aluno) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Responsavel r JOIN r.dependentes a WHERE a = :aluno", Responsavel.class)
                    .setParameter("aluno", aluno)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
