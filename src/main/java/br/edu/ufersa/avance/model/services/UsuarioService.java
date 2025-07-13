package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Usuario;

public interface UsuarioService extends GeralService<Usuario> {
    public Usuario buscarPorEmail(String email);
    public Usuario buscarPorSenha(String senha);
    public void validarUsuario(Usuario usuario) throws Exception;
}
