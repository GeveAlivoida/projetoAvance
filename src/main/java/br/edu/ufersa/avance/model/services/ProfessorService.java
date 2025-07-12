package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.model.entities.Professor;

import java.util.List;

public interface ProfessorService extends PessoaService {
    public List<Professor> buscarPorStatus(StatusProfessor status);
}
