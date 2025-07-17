package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Responsavel;

public interface ResponsavelDAO extends PessoaDAO<Responsavel> {
    public Responsavel buscarPorAluno(Aluno aluno);
}
