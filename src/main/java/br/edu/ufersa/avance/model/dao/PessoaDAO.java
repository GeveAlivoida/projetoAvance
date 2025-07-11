package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Pessoa;

public interface PessoaDAO<Entity extends Pessoa> extends GeralDAO<Entity> {
    Entity buscarPorCpf(Entity entity);
    Entity buscarPorEmail(Entity entity);
    Entity buscarPorTelefone(Entity entity);
}
