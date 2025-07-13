package br.edu.ufersa.avance.model.dao;

import br.edu.ufersa.avance.model.entities.Usuario;

public interface UsuarioDAO extends GeralDAO<Usuario>{
    Usuario buscarPorEmail(String email);
    Usuario buscarPorSenha(String senha);
}
