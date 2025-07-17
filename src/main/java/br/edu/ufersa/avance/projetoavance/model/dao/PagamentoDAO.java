package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.enums.StatusPagamento;
import br.edu.ufersa.avance.projetoavance.model.entities.Pagamento;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface PagamentoDAO extends GeralDAO<Pagamento> {
    public List<Pagamento> buscarPorAluno(Aluno aluno);
    public List<Pagamento> buscarPorData(LocalDate dataPagamento);
    public List<Pagamento> buscarPorMes(YearMonth mesRef);
    public List<Pagamento> buscarPorStatus(StatusPagamento status);
}
