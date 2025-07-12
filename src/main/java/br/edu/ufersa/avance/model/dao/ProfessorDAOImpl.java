package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.StatusProfessor;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProfessorDAOImpl extends PessoaDAOImpl implements ProfessorDAO{
    @Override
    public List<Professor> buscarPorStatus(StatusProfessor status) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.status = :status", Professor.class)
                    .setParameter("status", status)
                    .getResultList();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
