package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Aula;
import br.edu.ufersa.avance.projetoavance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoavance.model.entities.Responsavel;
import br.edu.ufersa.avance.projetoavance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAOImpl implements AlunoDAO{
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Aluno aluno) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();

            if (aluno.getResponsavel() != null) {
                Responsavel responsavel = em.merge(aluno.getResponsavel());
                aluno.setResponsavel(responsavel);
            }

            if (aluno.getModalidades() != null) {
                List<Modalidade> modalidadesGerenciadas = new ArrayList<>();
                for (Modalidade modalidade : aluno.getModalidades()) {
                    if (modalidade.getId() > 0) {
                        modalidadesGerenciadas.add(em.merge(modalidade));
                    } else {
                        throw new IllegalArgumentException("Modalidade não persistida: " + modalidade.getNome());
                    }
                }
                aluno.setModalidades(modalidadesGerenciadas);
            }

            em.persist(aluno);
            em.getTransaction().commit();
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
    public void atualizar(Aluno aluno) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();
            ts.begin();

            Aluno alunoExistente = em.find(Aluno.class, aluno.getId());
            if (alunoExistente == null) {
                throw new IllegalArgumentException("Esse aluno não existe!");
            }

            em.merge(aluno);
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
    public void excluir(Aluno aluno) {
        EntityManager em = null;
        EntityTransaction ts = null;
        try {
            em = emf.createEntityManager();
            ts = em.getTransaction();

            Aluno alunoGerenciado = em.find(Aluno.class, aluno.getId());
            if (alunoGerenciado != null) {
                for (Modalidade m : new ArrayList<>(alunoGerenciado.getModalidades()))
                    alunoGerenciado.removerModalidade(m);
                for (Aula a : new ArrayList<>(alunoGerenciado.getAulas()))
                    alunoGerenciado.removerAula(a);
                em.remove(alunoGerenciado);
            }

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
    public Aluno buscarPorId(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Aluno.class, id);
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aluno> buscarTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM Aluno", Aluno.class).getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aluno> buscarPorNome(String nome) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aluno a WHERE LOWER(a.nome) LIKE LOWER(:nome)", Aluno.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Aluno buscarPorCpf(String cpf) {
        try (EntityManager em = emf.createEntityManager()){
            List<Aluno> resultados = em.createQuery("SELECT a FROM Aluno a WHERE a.cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.getFirst();
        } catch (Throwable e){
            System.err.println("Falha ao criar EntityManager: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Aluno buscarPorEmail(String email) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aluno a WHERE LOWER(a.email) = LOWER(:email)", Aluno.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Aluno buscarPorTelefone(String telefone) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aluno a WHERE a.telefone = :telefone", Aluno.class)
                    .setParameter("telefone", telefone)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aluno> buscarPorAula(Aula aula) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT al FROM Aluno al JOIN al.aulas a WHERE a = :aula", Aluno.class)
                    .setParameter("aula", aula)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aluno> buscarPorResponsavel(Responsavel responsavel) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT a FROM Aluno a WHERE a.responsavel = :responsavel", Aluno.class)
                    .setParameter("responsavel", responsavel)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
