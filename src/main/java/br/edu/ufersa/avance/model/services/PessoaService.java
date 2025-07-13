package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Pessoa;

import java.util.List;

public interface PessoaService<T extends Pessoa> extends GeralService<T> {
    List<T> buscarPorNome(String nome);
    T buscarPorCpf(String cpf);
    T buscarPorEmail(String email);
    T buscarPorTelefone(String telefone);
}
