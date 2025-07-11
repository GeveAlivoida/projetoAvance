package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.enums.StatusPagamento;
import br.edu.ufersa.avance.model.entities.Pagamento;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface PagamentoService extends GeralService<Pagamento> {
    public List<Pagamento> buscarPorPessoa();
    public List<Pagamento> buscarPorData(LocalDate dataPagamento);
    public List<Pagamento> buscarPorMes(YearMonth mesRef);
    public List<Pagamento> buscarPorStatus(StatusPagamento status);
}
