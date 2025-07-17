package br.edu.ufersa.avance.projetoavance.model.dao;

import br.edu.ufersa.avance.projetoavance.model.entities.Usuario;

public interface UsuarioDAO extends GeralDAO<Usuario>{
    Usuario buscarPorEmail(String email);
}
