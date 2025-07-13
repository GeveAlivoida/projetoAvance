package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Modalidade;

import java.time.LocalDate;
import java.util.List;

public interface AulaService extends GeralService<Aula> {
    public List<Aula> buscarPorAluno(Aluno aluno);
    public List<Aula> buscarPorModalidade(Modalidade modalidade);
    public List<Aula> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
    public void agendarAula(Aula aula);
    public void cancelarAula(Aula aula);
    public void concluirAula(Aula aula);
    public void adiarAula(Aula aula, LocalDate novaData);
}
