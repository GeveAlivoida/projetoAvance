package br.edu.ufersa.avance.projetoAvance.model.services;

import br.edu.ufersa.avance.projetoAvance.exceptions.AuthenticationException;
import br.edu.ufersa.avance.projetoAvance.model.entities.Usuario;

public interface UsuarioService extends GeralService<Usuario> {
    public Usuario buscarPorEmail(String email);
    public Usuario autenticar(Usuario usuario) throws AuthenticationException;
}
