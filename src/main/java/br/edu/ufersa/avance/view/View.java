package br.edu.ufersa.avance.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        telaLogin();
    }

    public static void loadTela(String arquivo){
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/br/edu/ufersa/avance/" + arquivo));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void telaLogin(){
        stage.setTitle("Login");
        loadTela("TelaLogin.fxml");
    }
    public static void dashboard(){
        stage.setTitle("Dashboard");
        loadTela("Dashboard.fxml");
    }
    public static void alunoCadastro(){
        stage.setTitle("Cadastro de Alunos");
        loadTela("AlunoCadastro.fxml");
    }
    public static void aulaCadastro(){

    }
    public static void modalidadeCadastro(){
        stage.setTitle("Cadastro de Modalidades");
        loadTela("ModalidadeCadastro.fxml");
    }
    public static void professorCadastro(){
        stage.setTitle("Cadastro de Professores");
        loadTela("ProfessorCadastro.fxml");
    }
    public static void pagamentoCadastro(){

    }
    public static void responsavelCadastro(){

    }

    public static void main(String[] args) {
        launch();
    }
}