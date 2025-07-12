package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Pagamento;
import br.edu.ufersa.avance.model.entities.Pessoa;
import br.edu.ufersa.avance.model.enums.StatusPagamento;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PagamentoDAOImpl extends GeralDAOImpl<Pagamento> implements PagamentoDAO{
    @Override
    public Pagamento buscarPorId(Long id) {
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
