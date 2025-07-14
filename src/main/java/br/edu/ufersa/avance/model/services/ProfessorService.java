package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.model.entities.Professor;

import java.util.List;

public interface ProfessorService extends PessoaService<Professor> {
    public List<Professor> buscarPorStatus(StatusProfessor status);
    public List<Professor> buscarPorTodosCampos(String termo);
    public void alterarStatus(Professor professor, StatusProfessor status);
}
