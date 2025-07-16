package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.entities.Aula;
import br.edu.ufersa.avance.projetoAvance.model.entities.Modalidade;

import java.time.LocalDate;
import java.util.List;

public interface AulaDAO extends GeralDAO<Aula> {
    public List<Aula> buscarPorAluno(Aluno aluno);
    public List<Aula> buscarPorModalidade(Modalidade modalidade);
    public List<Aula> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
}
