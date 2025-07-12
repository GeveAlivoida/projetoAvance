package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Pessoa;
import br.edu.ufersa.avance.model.enums.StatusPagamento;
import br.edu.ufersa.avance.model.entities.Pagamento;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface PagamentoDAO extends GeralDAO<Pagamento> {
    public List<Pagamento> buscarPorPagador(Pessoa pagador);
    public List<Pagamento> buscarPorData(LocalDate dataPagamento);
    public List<Pagamento> buscarPorMes(YearMonth mesRef);
    public List<Pagamento> buscarPorStatus(StatusPagamento status);
}
