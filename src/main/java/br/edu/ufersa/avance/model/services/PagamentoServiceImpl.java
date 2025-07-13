package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.PagamentoDAO;
import br.edu.ufersa.avance.model.dao.PagamentoDAOImpl;
import br.edu.ufersa.avance.model.entities.Pagamento;
import br.edu.ufersa.avance.model.entities.Pessoa;
import br.edu.ufersa.avance.model.enums.StatusPagamento;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PagamentoServiceImpl implements PagamentoService{
    private final PagamentoDAO pagamentoDAO = new PagamentoDAOImpl();

    @Override
    public void cadastrar(Pagamento pagamento) {
        Pagamento pagamentoEncontrado = pagamentoDAO.buscarPorId(pagamento.getId());
        if(pagamentoEncontrado != null)
            throw new IllegalArgumentException("Pagamento já cadastrado!");
        else pagamentoDAO.cadastrar(pagamento);
    }

    @Override
    public void atualizar(Pagamento pagamento) {
        Pagamento pagamentoEncontrado = pagamentoDAO.buscarPorId(pagamento.getId());
        if(pagamentoEncontrado == null)
            throw new IllegalArgumentException("Pagamento inexistente!");
        else pagamentoDAO.atualizar(pagamento);
    }

    @Override
    public void excluir(Pagamento pagamento) {
        Pagamento pagamentoEncontrado = pagamentoDAO.buscarPorId(pagamento.getId());
        if(pagamentoEncontrado == null)
            throw new IllegalArgumentException("Pagamento inexistente!");
        else pagamentoDAO.excluir(pagamento);
    }

    @Override
    public List<Pagamento> buscarTodos() { return pagamentoDAO.buscarTodos(); }

    @Override
    public Pagamento buscarPorId(long id) { return pagamentoDAO.buscarPorId(id); }

    @Override
    public List<Pagamento> buscarPorPagador(Pessoa pagador) {
        if(pagador != null) return pagamentoDAO.buscarPorPagador(pagador);
        else return null;
    }

    @Override
    public List<Pagamento> buscarPorData(LocalDate dataPagamento) {
        if(dataPagamento != null) return pagamentoDAO.buscarPorData(dataPagamento);
        else return null;
    }

    @Override
    public List<Pagamento> buscarPorMes(YearMonth mesRef) {
        if(mesRef != null) return pagamentoDAO.buscarPorMes(mesRef);
        else return null;
    }

    @Override
    public List<Pagamento> buscarPorStatus(StatusPagamento status) {
        if(status != null) return pagamentoDAO.buscarPorStatus(status);
        else return null;
    }
}
