package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Pessoa;

public interface PessoaService<Entity extends Pessoa> extends GeralService<Entity> {
    Entity buscarPorCpf(Entity entity);
    Entity buscarPorEmail(Entity entity);
    Entity buscarPorTelefone(Entity entity);
}
