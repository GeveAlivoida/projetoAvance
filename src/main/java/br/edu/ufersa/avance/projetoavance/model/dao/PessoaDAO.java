package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Pessoa;

import java.util.List;

public interface PessoaDAO<T extends Pessoa> extends GeralDAO<T> {
    List<T> buscarPorNome(String nome);
    T buscarPorCpf(String cpf);
    T buscarPorEmail(String email);
    T buscarPorTelefone(String telefone);
}
