package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

public interface ResponsavelDAO extends PessoaDAO<Responsavel> {
    public Responsavel buscarPorAluno(Aluno aluno);
}
