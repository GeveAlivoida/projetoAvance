package br.edu.ufersa.avance.model.services;

import java.util.List;

public interface GeralService<Entity> {
    public List<Entity> buscarTodos();
    public Entity buscarPorId(Entity entity);
    public void criar(Entity e);
    public void atualizar(Entity e);
    public void deletar(Entity e);
}
