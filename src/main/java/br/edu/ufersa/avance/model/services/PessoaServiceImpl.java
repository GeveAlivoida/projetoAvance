package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.PessoaDAO;
import br.edu.ufersa.avance.model.dao.PessoaDAOImpl;
import br.edu.ufersa.avance.model.entities.Pessoa;

import java.util.List;

public class PessoaServiceImpl implements PessoaService {
    private final PessoaDAO pessoaDAO = new PessoaDAOImpl();

    @Override
    public void cadastrar(Pessoa pessoa) {
        Pessoa pessoaEncontrada = pessoaDAO.buscarPorId(pessoa.getId());
        if(pessoaEncontrada != null)
            throw new IllegalArgumentException("Pessoa já cadastrada!");
        else pessoaDAO.cadastrar(pessoa);
    }

    @Override
    public void atualizar(Pessoa pessoa) {
        Pessoa pessoaEncontrada = pessoaDAO.buscarPorId(pessoa.getId());
        if(pessoaEncontrada == null)
            throw new IllegalArgumentException("Pessoa inexistente!");
        else pessoaDAO.atualizar(pessoa);
    }

    @Override
    public void excluir(Pessoa pessoa) {
        Pessoa pessoaEncontrada = pessoaDAO.buscarPorId(pessoa.getId());
        if(pessoaEncontrada == null)
            throw new IllegalArgumentException("Pessoa inexistente!");
        else pessoaDAO.excluir(pessoa);
    }


    @Override
    public Pessoa buscarPorId(Long id) {
        if(id != null) return pessoaDAO.buscarPorId(id);
        else return null;
    }

    @Override
    public List<Pessoa> buscarTodos() { return pessoaDAO.buscarTodos(); }

    @Override
    public List<Pessoa> buscarPorNome(String nome) {
        if(nome != null && !nome.isEmpty()) return pessoaDAO.buscarPorNome(nome);
        else return null;
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        if(cpf != null && !cpf.isEmpty()) return pessoaDAO.buscarPorCpf(cpf);
        else return null;
    }

    @Override
    public Pessoa buscarPorEmail(String email) {
        if(email != null && !email.isEmpty()) return pessoaDAO.buscarPorEmail(email);
        else return null;
    }

    @Override
    public Pessoa buscarPorTelefone(String telefone) {
        if(telefone != null && !telefone.isEmpty()) return pessoaDAO.buscarPorTelefone(telefone);
        else return null;
    }
}
