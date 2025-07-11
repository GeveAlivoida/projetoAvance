package br.edu.ufersa.avance.model.dao;

import java.util.List;

public interface GeralDAO<Entity> {
    public List<Entity> buscarTodos();
    public Entity buscarPorId(Entity entity);
    public void criar(Entity entity);
    public void atualizar(Entity entity);
    public void deletar(Entity entity);
}
