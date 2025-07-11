package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

import java.util.List;

public interface AlunoDAO extends PessoaDAO<Aluno> {
    public List<Aluno> buscarPorResponsavel(Responsavel responsavel);
}
