package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Pessoa;

import java.util.List;

public interface PessoaService extends GeralService<Pessoa> {
    List<Pessoa> buscarPorNome(String nome);
    Pessoa buscarPorCpf(String cpf);
    Pessoa buscarPorEmail(String email);
    Pessoa buscarPorTelefone(String telefone);
}
