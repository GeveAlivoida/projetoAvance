package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Pessoa;
import br.edu.ufersa.avance.model.entities.Responsavel;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AlunoDAOImpl extends PessoaDAOImpl implements AlunoDAO{
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
