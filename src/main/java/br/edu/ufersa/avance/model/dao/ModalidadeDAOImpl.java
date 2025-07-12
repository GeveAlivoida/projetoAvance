package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.TipoModalidade;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ModalidadeDAOImpl extends GeralDAOImpl<Modalidade> implements ModalidadeDAO{
    @Override
    public Modalidade buscarPorId(Long id) {
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
