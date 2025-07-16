package br.edu.ufersa.avance.projetoAvance.model.services;

import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.entities.Responsavel;

public interface ResponsavelService extends PessoaService<Responsavel> {
    public Responsavel buscarPorAluno(Aluno aluno);
    public void cadastrarAluno(Responsavel responsavel, Aluno aluno);
}
