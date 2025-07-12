package br.edu.ufersa.avance.model.dao;

import java.util.List;

public interface GeralDAO<T> {
    public List<T> buscarTodos();
    public T buscarPorId(Long id);
    public void salvar(T entity);
    public void atualizar(T entity);
    public void deletar(T entity);
}
