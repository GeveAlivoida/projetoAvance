package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.AlunoDAO;
import br.edu.ufersa.avance.model.dao.AlunoDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

import java.util.List;

public class AlunoServiceImpl extends PessoaServiceImpl implements AlunoService {
    private final AlunoDAO alunoDAO = new AlunoDAOImpl();

    @Override
    public List<Aluno> buscarPorResponsavel(Responsavel responsavel) {
        if(responsavel != null) return alunoDAO.buscarPorResponsavel(responsavel);
        else return null;
    }
}
