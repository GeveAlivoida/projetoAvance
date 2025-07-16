package br.edu.ufersa.avance.controller;

import br.edu.ufersa.avance.model.entities.Usuario;
import br.edu.ufersa.avance.model.services.UsuarioService;
import br.edu.ufersa.avance.model.services.UsuarioServiceImpl;
import br.edu.ufersa.avance.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class UsuarioController {
    @FXML private TextField usuarioNome;
    @FXML private TextField usuarioEmail;
    @FXML private TextField usuarioSenha;

    @FXML private Label mensagemErro;

    UsuarioService service = new UsuarioServiceImpl();
    Usuario novoUsuario = new Usuario();

    private void mostrarErro(String mensagem){
        mensagemErro.setText(mensagem);
        mensagemErro.setTextFill(Color.RED);
        mensagemErro.setVisible(true);
    }

    private void preencherCampos(){
        novoUsuario.setNome(usuarioNome.getText());
        novoUsuario.setEmail(usuarioEmail.getText());
        novoUsuario.setSenha(usuarioSenha.getText());
    }

    @FXML
    void cadastrarUsuario(ActionEvent event) {
        try {
            preencherCampos();
            service.cadastrar(novoUsuario);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Cadastro bem-sucedido");
            alert.setContentText("Cadastrado com sucesso, clique no OK para voltar à tela de login");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    View.telaLogin();
                }
            });
        }
        catch (IllegalArgumentException e){
            mostrarErro(e.getMessage());
        }
        catch (Exception e){
            mostrarErro("Não foi possível fazer o cadastro: " + e.getMessage());
        }
    }

}
