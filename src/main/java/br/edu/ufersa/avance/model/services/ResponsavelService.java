package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

public interface ResponsavelService extends PessoaService<Responsavel> {
    public Responsavel buscarPorAluno(Aluno aluno);
}
