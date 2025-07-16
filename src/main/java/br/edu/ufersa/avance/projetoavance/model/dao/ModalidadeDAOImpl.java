package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoAvance.model.entities.Professor;
import br.edu.ufersa.avance.projetoAvance.model.enums.TipoModalidade;
import br.edu.ufersa.avance.projetoAvance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class ModalidadeDAOImpl implements ModalidadeDAO{
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Modalidade modalidade) {
        if (modalidade.getId() > 0)
            throw new IllegalArgumentException("Esta modalidade já existe!");
        else {
            EntityManager em = null;
            EntityTransaction ts = null;
            try {
                em = emf.createEntityManager();
                ts = em.getTransaction();
                ts.begin();

                if (modalidade.getProfessor() == null || modalidade.getProfessor().getId() == 0)
                    throw new IllegalArgumentException("Professor não encontrado!");
                else {
                    Professor professor = em.find(Professor.class, modalidade.getProfessor().getId());
                    modalidade.setProfessor(professor);
                }

                em.persist(modalidade);
                ts.commit();
            }catch (Throwable e) {
                if (ts != null && ts.isActive())
                    ts.rollback();
                throw new RuntimeException("Falha ao criar EntityManager " + e);
            }
            finally {
                if (em != null && em.isOpen())
                    em.close();
            }
        }
    }

    @Override
    public void atualizar(Modalidade modalidade) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();

            ts.begin();
            em.merge(modalidade);
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
    public void excluir(Modalidade modalidade) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            Modalidade modalidadeGerenciada = em.find(Modalidade.class, modalidade.getId());

            for(Aluno aluno : new ArrayList<>(modalidadeGerenciada.getAlunos())) {
                modalidadeGerenciada.removerAluno(aluno);
            }

            em.remove(em.contains(modalidade) ? modalidade : em.merge(modalidade));
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
    public Modalidade buscarPorId(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Modalidade.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Modalidade> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Modalidade", Modalidade.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Modalidade buscarPorProfessor(Professor professor) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Modalidade m WHERE m.professor = :professor", Modalidade.class)
                    .setParameter("professor", professor)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Modalidade> buscarPorNome(String nome) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT m FROM Modalidade m WHERE LOWER(m.nome) LIKE LOWER(:nome)", Modalidade.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Modalidade> buscarPorTipo(TipoModalidade tipo) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Modalidade m WHERE m.tipo = :tipo", Modalidade.class)
                    .setParameter("tipo", tipo)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Modalidade> buscarAbertas() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Modalidade m WHERE m.vagas > 0", Modalidade.class)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
