package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.ResponsavelDAO;
import br.edu.ufersa.avance.model.dao.ResponsavelDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

public class ResponsavelServiceImpl extends PessoaServiceImpl implements ResponsavelService{
    private final ResponsavelDAO responsavelDAO = new ResponsavelDAOImpl();

    @Override
    public Responsavel buscarPorAluno(Aluno aluno) {
        if(aluno != null) return responsavelDAO.buscarPorAluno(aluno);
        else return null;
    }
}
