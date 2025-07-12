package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

public interface ResponsavelDAO extends PessoaDAO {
    public Responsavel buscarPorAluno(Aluno aluno);
}
