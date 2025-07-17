package br.edu.ufersa.avance.projetoavance.model.services;

import br.edu.ufersa.avance.projetoavance.model.dao.PagamentoDAO;
import br.edu.ufersa.avance.projetoavance.model.dao.PagamentoDAOImpl;
import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Pagamento;
import br.edu.ufersa.avance.projetoavance.model.enums.StatusPagamento;

import java.time.YearMonth;
import java.util.Collections;
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
    public List<Pagamento> buscarPorTodosCampos(String termo) {
        final AlunoService alunoService = new AlunoServiceImpl();

        if (termo == null || termo.trim().isEmpty()) {
            return buscarTodos();
        }

        Aluno aluno = alunoService.buscarPorCpf(termo);
        if (aluno != null) {
            return pagamentoDAO.buscarPorAluno(aluno);
        }

        try {
            StatusPagamento status = StatusPagamento.valueOf(termo.toUpperCase());
            return pagamentoDAO.buscarPorStatus(status);
        } catch (IllegalArgumentException e) {
            // Não é um status válido, continua a busca
        }

        if (termo.matches("\\d{1,2}/\\d{4}")) {
            String[] partes = termo.split("/");
            YearMonth mesRef = YearMonth.of(
                    Integer.parseInt(partes[1]),
                    Integer.parseInt(partes[0])
            );
            return pagamentoDAO.buscarPorMes(mesRef);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Pagamento> buscarTodos() { return pagamentoDAO.buscarTodos(); }

    @Override
    public Pagamento buscarPorId(long id) { return pagamentoDAO.buscarPorId(id); }

    @Override
    public List<Pagamento> buscarPorAluno(Aluno aluno) {
        if(aluno != null) return pagamentoDAO.buscarPorAluno(aluno);
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
