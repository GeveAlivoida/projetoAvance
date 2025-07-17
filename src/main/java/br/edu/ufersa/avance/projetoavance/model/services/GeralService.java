package br.edu.ufersa.avance.projetoavance.model.services;

import java.util.List;

public interface GeralService<T> {
    public List<T> buscarTodos();
    public T buscarPorId(long id);
    public void cadastrar(T entity);
    public void atualizar(T entity);
    public void excluir(T entity);
    public List<T> buscarPorTodosCampos(String termo);
}
