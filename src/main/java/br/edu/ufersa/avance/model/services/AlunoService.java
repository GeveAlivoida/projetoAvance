package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

import java.util.List;

public interface AlunoService extends PessoaService {
    public List<Aluno> buscarPorResponsavel(Responsavel responsavel);
}
