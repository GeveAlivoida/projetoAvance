package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.ProfessorDAO;
import br.edu.ufersa.avance.model.dao.ProfessorDAOImpl;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.StatusProfessor;

import java.util.List;

public class ProfessorServiceImpl extends PessoaServiceImpl implements ProfessorService{
    private final ProfessorDAO professorDAO = new ProfessorDAOImpl();

    @Override
    public List<Professor> buscarPorStatus(StatusProfessor status) {
        if(status != null) return professorDAO.buscarPorStatus(status);
        else return null;
    }
}
