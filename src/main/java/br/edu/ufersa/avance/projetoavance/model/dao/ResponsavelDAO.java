package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.entities.Responsavel;

public interface ResponsavelDAO extends PessoaDAO<Responsavel> {
    public Responsavel buscarPorAluno(Aluno aluno);
}
