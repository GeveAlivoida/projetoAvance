package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.projetoavance.model.entities.Professor;

import java.util.List;

public interface ProfessorDAO extends PessoaDAO<Professor> {
    public List<Professor> buscarPorStatus(StatusProfessor status);
}
