package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class AulaDAOImpl implements AulaDAO{
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Aula aula) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(aula);
            em.getTransaction().commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(Aula aula) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.merge(aula);
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(Aula aula) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(aula) ? aula : em.merge(aula));
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Aula buscarPorId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Aula.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Aula", Aula.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarPorAluno(Aluno aluno) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aula a JOIN a.alunos al WHERE al = :aluno", Aula.class)
                    .setParameter("aluno", aluno)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarPorModalidade(Modalidade modalidade) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aula a WHERE a.modalidade = :modalidade", Aula.class)
                    .setParameter("modalidade", modalidade)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aula a WHERE a.data BETWEEN :inicio AND :fim", Aula.class)
                    .setParameter("inicio", dataInicio)
                    .setParameter("fim", dataFim)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
