package br.edu.ufersa.avance.projetoavance.model.services;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Aula;
import br.edu.ufersa.avance.projetoavance.model.entities.Responsavel;

import java.util.List;

public interface AlunoService extends PessoaService<Aluno> {
    public List<Aluno> buscarPorAula(Aula aula);
    public List<Aluno> buscarPorResponsavel(Responsavel responsavel);
}
