package br.edu.ufersa.avance.projetoAvance.model.services;

import br.edu.ufersa.avance.projetoAvance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.projetoAvance.model.entities.Professor;

import java.util.List;

public interface ProfessorService extends PessoaService<Professor> {
    public List<Professor> buscarPorStatus(StatusProfessor status);
    public void alterarStatus(Professor professor, StatusProfessor status);
}
