package br.edu.ufersa.avance.projetoAvance.controller;

import br.edu.ufersa.avance.projetoAvance.view.View;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class DashboardController {
    @FXML void abrirAlunos(MouseEvent event) { View.alunoCadastro(); }
    @FXML void abrirProfessores(MouseEvent event) { View.professorCadastro(); }
    @FXML void abrirResponsaveis(MouseEvent event) { View.responsavelCadastro();}
    @FXML void abrirModalidades(MouseEvent event) { View.modalidadeCadastro(); }
    @FXML void abrirAulas(MouseEvent event) { View.aulaCadastro(); }
    @FXML void abrirPagamentos(MouseEvent event) { View.pagamentoCadastro(); }

    @FXML void voltarLogin(MouseEvent event) { View.telaLogin(); }
}
