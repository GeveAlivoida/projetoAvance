package br.edu.ufersa.avance.model.dao;

import java.util.List;

public interface GeralDAO<T> {
    public List<T> buscarTodos();
    public T buscarPorId(Long id);
    public void cadastrar(T entity);
    public void atualizar(T entity);
    public void excluir(T entity);
}
