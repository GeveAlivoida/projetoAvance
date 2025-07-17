package br.edu.ufersa.avance.projetoavance.model.services;

import br.edu.ufersa.avance.projetoavance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.projetoavance.model.entities.Professor;

import java.util.List;

public interface ProfessorService extends PessoaService<Professor> {
    public List<Professor> buscarPorStatus(StatusProfessor status);
    public void alterarStatus(Professor professor, StatusProfessor status);
}
