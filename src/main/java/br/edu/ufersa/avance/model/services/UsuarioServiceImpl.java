package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.UsuarioDAO;
import br.edu.ufersa.avance.model.dao.UsuarioDAOImpl;
import br.edu.ufersa.avance.model.entities.Usuario;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService{
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    public void cadastrar(Usuario usuario) {
        Usuario usuarioEncontrado = usuarioDAO.buscarPorId(usuario.getId());
        if(usuarioEncontrado != null)
            throw new IllegalArgumentException("Usuario já cadastrado!");
        else usuarioDAO.cadastrar(usuario);
    }

    @Override
    public void atualizar(Usuario usuario) {
        Usuario usuarioEncontrado = usuarioDAO.buscarPorId(usuario.getId());
        if(usuarioEncontrado == null)
            throw new IllegalArgumentException("Usuario inexistente!");
        else usuarioDAO.atualizar(usuario);
    }

    @Override
    public void excluir(Usuario usuario) {
        Usuario usuarioEncontrado = usuarioDAO.buscarPorId(usuario.getId());
        if(usuarioEncontrado == null)
            throw new IllegalArgumentException("Usuario inexistente!");
        else usuarioDAO.excluir(usuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return List.of();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        if(id != null) return usuarioDAO.buscarPorId(id);
        else return null;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        if(email != null && !email.isEmpty()) return usuarioDAO.buscarPorEmail(email);
        else return null;
    }

    @Override
    public Usuario buscarPorSenha(String senha) {
        if(senha != null && !senha.isEmpty()) return usuarioDAO.buscarPorEmail(senha);
        else return null;
    }

    @Override
    public void validarUsuario(Usuario usuario) throws Exception {

    }
}
