package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Pessoa;
import jakarta.persistence.*;

import java.util.List;

public abstract class PessoaDAOImpl extends GeralDAOImpl<Pessoa> implements PessoaDAO{
    @Override
    public Pessoa buscarPorId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Pessoa.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pessoa> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM " + Pessoa.class, Pessoa.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pessoa> buscarPorNome(String nome) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Pessoa p WHERE p.nome LIKE :nome", Pessoa.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pessoa buscarPorEmail(String email) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Pessoa p WHERE p.email = :email", Pessoa.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pessoa buscarPorTelefone(String telefone) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Pessoa p WHERE p.telefone = :telefone", Pessoa.class)
                    .setParameter("telefone", telefone)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
