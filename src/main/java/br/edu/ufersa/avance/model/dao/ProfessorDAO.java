package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.enums.StatusProfessor;
import br.edu.ufersa.avance.model.entities.Professor;

import java.util.List;

public interface ProfessorDAO extends PessoaDAO<Professor> {
    public List<Professor> buscarPorStatus(StatusProfessor status);
}
