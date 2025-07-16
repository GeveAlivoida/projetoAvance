package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.entities.Usuario;

public interface UsuarioDAO extends GeralDAO<Usuario>{
    Usuario buscarPorEmail(String email);
}
