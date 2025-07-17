package br.edu.ufersa.avance.projetoavance.model.services;

import br.edu.ufersa.avance.projetoavance.exceptions.AuthenticationException;
import br.edu.ufersa.avance.projetoavance.model.entities.Usuario;

public interface UsuarioService extends GeralService<Usuario> {
    public Usuario buscarPorEmail(String email);
    public Usuario autenticar(Usuario usuario) throws AuthenticationException;
}
