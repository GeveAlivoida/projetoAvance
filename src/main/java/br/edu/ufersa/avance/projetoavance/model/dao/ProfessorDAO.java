package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.projetoAvance.model.entities.Professor;

import java.util.List;

public interface ProfessorDAO extends PessoaDAO<Professor> {
    public List<Professor> buscarPorStatus(StatusProfessor status);
}
