package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.exceptions.AuthenticationException;
import br.edu.ufersa.avance.model.entities.Usuario;

public interface UsuarioService extends GeralService<Usuario> {
    public Usuario buscarPorEmail(String email);
    public Usuario autenticar(Usuario usuario) throws AuthenticationException;
}
