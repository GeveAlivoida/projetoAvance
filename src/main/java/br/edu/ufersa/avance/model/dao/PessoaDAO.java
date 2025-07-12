package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Pessoa;

import java.util.List;

public interface PessoaDAO extends GeralDAO<Pessoa> {
    List<Pessoa> buscarPorNome(String nome);
    Pessoa buscarPorCpf(String cpf);
    Pessoa buscarPorEmail(String email);
    Pessoa buscarPorTelefone(String telefone);
}
