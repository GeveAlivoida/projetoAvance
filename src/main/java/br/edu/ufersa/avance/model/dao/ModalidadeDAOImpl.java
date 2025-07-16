package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.TipoModalidade;
import br.edu.ufersa.avance.util.JPAUtil;
import jakarta.persistence.*;

import java.util.List;

public class ModalidadeDAOImpl implements ModalidadeDAO{
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    @Override
    public void cadastrar(Modalidade modalidade) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Reanexar o professor ao contexto do EntityManager
            Professor professor = modalidade.getProfessor();
            if (professor != null && professor.getId() != 0) {
                Professor gerenciado = em.find(Professor.class, professor.getId());
                modalidade.setProfessor(gerenciado);
            }

            em.persist(modalidade);
            em.getTransaction().commit();
            if (professor == null) {
                throw new IllegalArgumentException("Professor não encontrado");
            }

        } catch (Throwable e) {
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void atualizar(Modalidade modalidade) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Reanexar professor
            Professor professor = modalidade.getProfessor();
            if (professor != null && professor.getId() != 0) {
                professor = em.find(Professor.class, professor.getId());
                modalidade.setProfessor(professor);
            }

            em.merge(modalidade); // <- só merge, sem persist
            em.getTransaction().commit();
        } catch (Throwable e) {
            System.err.println("Erro ao atualizar Modalidade: " + e);
            throw new RuntimeException(e);
        }
    }



    @Override
    public void excluir(Modalidade modalidade) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.remove(em.contains(modalidade) ? modalidade : em.merge(modalidade));
            ts.commit();
        }catch(Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
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
