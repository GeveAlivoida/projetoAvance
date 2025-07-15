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

    private void mostrarErro(String mensagem){
        mensagemErro.setText(mensagem);
        mensagemErro.setTextFill(Color.RED);
        mensagemErro.setVisible(true);
    }

    private void preencherCampos(){
        usuario.setNome("usuario");
        usuario.setEmail(usuarioEmail.getText());
        usuario.setSenha(usuarioSenha.getText());
    }

    @FXML
    void autenticar(ActionEvent event) {
        try {
            preencherCampos();

            usuario = service.autenticar(usuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Autenticação Bem-sucedida");
            alert.setContentText("Autenticado com sucesso, bem vindo " + usuario.getNome() + "!");

            View.dashboard();
        }
        catch (IllegalArgumentException | AuthenticationException e) {
            mostrarErro(e.getMessage());
        }
        catch (Exception e){
            mostrarErro("Não foi possível fazer login: " + e.getMessage());
        }
    }
}
