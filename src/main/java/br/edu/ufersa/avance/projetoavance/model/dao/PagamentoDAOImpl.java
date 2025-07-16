package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.entities.Pagamento;
import br.edu.ufersa.avance.projetoAvance.model.enums.StatusPagamento;
import br.edu.ufersa.avance.projetoAvance.util.JPAUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PagamentoDAOImpl implements PagamentoDAO{
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Pagamento pagamento) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            if (pagamento.getAluno() != null) {
                Aluno aluno = em.find(Aluno.class, pagamento.getAluno().getId());
                if (aluno == null)
                    throw new IllegalArgumentException("Aluno não encontrado!");
                pagamento.setAluno(aluno);
            }

            em.persist(pagamento);
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
    public void atualizar(Pagamento pagamento) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();
            em.merge(pagamento);
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
    public void excluir(Pagamento pagamento) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            Pagamento pagamentoGerenciado = em.find(Pagamento.class, pagamento.getId());
            if (pagamentoGerenciado == null) {
                throw new IllegalArgumentException("Pagamento não encontrado!");
            }

            em.remove(pagamentoGerenciado);
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
    public Pagamento buscarPorId(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Pagamento.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pagamento> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Pagamento", Pagamento.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pagamento> buscarPorAluno(Aluno aluno) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Pagamento p WHERE p.aluno = :aluno", Pagamento.class)
                    .setParameter("pagador", aluno)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pagamento> buscarPorData(LocalDate dataPagamento) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Pagamento p WHERE p.dataPagamento = :data", Pagamento.class)
                    .setParameter("data", dataPagamento)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pagamento> buscarPorMes(YearMonth mesRef) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Pagamento p WHERE p.mesRef = :mes", Pagamento.class)
                    .setParameter("mes", mesRef)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pagamento> buscarPorStatus(StatusPagamento status) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Pagamento p WHERE p.status = :status", Pagamento.class)
                    .setParameter("status", status)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
