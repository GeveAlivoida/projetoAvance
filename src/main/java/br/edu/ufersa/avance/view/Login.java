package br.edu.ufersa.avance.view;

import br.edu.ufersa.avance.model.entities.Usuario;
import br.edu.ufersa.avance.model.services.UsuarioService;
import br.edu.ufersa.avance.model.services.UsuarioServiceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    }

    public static void main(String[] args) {
        UsuarioService usuario1 = new UsuarioServiceImpl();
        usuario1.cadastrar(new Usuario("joao", "jv@email.com", "20i329"));

        System.out.println(usuario1.buscarPorId(1).getNome());

        launch();
    }
}