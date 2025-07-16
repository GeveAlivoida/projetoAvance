package br.edu.ufersa.avance.projetoAvance.model.services;

import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.enums.StatusPagamento;
import br.edu.ufersa.avance.projetoAvance.model.entities.Pagamento;

import java.time.YearMonth;
import java.util.List;

public interface PagamentoService extends GeralService<Pagamento> {
    public List<Pagamento> buscarPorAluno(Aluno aluno);
    public List<Pagamento> buscarPorMes(YearMonth mesRef);
    public List<Pagamento> buscarPorStatus(StatusPagamento status);
}
