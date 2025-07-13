package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Pagamento;
import br.edu.ufersa.avance.model.entities.Pessoa;
import br.edu.ufersa.avance.model.enums.StatusPagamento;
import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PagamentoDAOImpl implements PagamentoDAO{
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Pagamento pagamento) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(pagamento);
            em.getTransaction().commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(Pagamento pagamento) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.merge(pagamento);
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(Pagamento pagamento) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(pagamento) ? pagamento : em.merge(pagamento));
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
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
    public List<Pagamento> buscarPorPagador(Pessoa pagador) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Pagamento p WHERE p.pagador = :pagador", Pagamento.class)
                    .setParameter("pagador", pagador)
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
