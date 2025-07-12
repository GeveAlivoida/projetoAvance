package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.entities.Responsavel;
import jakarta.persistence.EntityManager;

public class ResponsavelDAOImpl extends PessoaDAOImpl implements ResponsavelDAO{
    @Override
    public Responsavel buscarPorAluno(Aluno aluno) {
        try (EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Responsavel r JOIN r.dependentes a WHERE a = :aluno", Responsavel.class)
                    .setParameter("aluno", aluno)
                    .getSingleResult();
        }catch (Throwable e){
            System.err.println("Falha ao criar EntityManager " + e);
            throw new RuntimeException(e);
        }
    }
}
