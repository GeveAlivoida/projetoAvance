package br.edu.ufersa.avance.model.dao;

import java.util.List;

public interface GeralDAO<Entity> {
    public List<Entity> buscarTodos();
    public void criar(Entity e);
    public void atualizar(Entity e);
    public void deletar(Entity e);
}
