package br.edu.ufersa.avance.controller;

import br.edu.ufersa.avance.exceptions.AuthenticationException;
import br.edu.ufersa.avance.model.entities.Usuario;
import br.edu.ufersa.avance.model.services.UsuarioService;
import br.edu.ufersa.avance.model.services.UsuarioServiceImpl;
import br.edu.ufersa.avance.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.swing.*;

public class LoginController {
    @FXML private Label mensagemErro;
    @FXML private TextField usuarioEmail;
    @FXML private TextField usuarioSenha;

    private final UsuarioService service = new UsuarioServiceImpl();
    private Usuario usuario = new Usuario();

    @FXML
    void autenticar(ActionEvent event) {
        try {
            usuario.setNome("usuario");
            usuario.setEmail(usuarioEmail.getText());
            usuario.setSenha(usuarioSenha.getText());
        } catch (IllegalArgumentException e) {
            mensagemErro.setText(e.getMessage());
            mensagemErro.setTextFill(Color.RED);
            mensagemErro.setVisible(true);
        }

        try {
            usuario = service.autenticar(usuario);
            JOptionPane.showMessageDialog(null,
                    "Autenticado com sucesso, bem vindo " + usuario.getNome() + "!");
            View.dashboard();
        } catch (AuthenticationException e){
            mensagemErro.setText(e.getMessage());
            mensagemErro.setTextFill(Color.RED);
            mensagemErro.setVisible(true);
        }
    }
}
