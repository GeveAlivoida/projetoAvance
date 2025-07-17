package br.edu.ufersa.avance.projetoavance.controller;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Aula;
import br.edu.ufersa.avance.projetoavance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoavance.model.entities.Professor;
import br.edu.ufersa.avance.projetoavance.model.enums.StatusAula;
import br.edu.ufersa.avance.projetoavance.model.services.*;
import br.edu.ufersa.avance.projetoavance.util.PDFGenerator;
import br.edu.ufersa.avance.projetoavance.view.View;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AulaController {
    @FXML private TableView<Aula> aulaTable;
    @FXML private TableColumn<Aula, String> colData;
    @FXML private TableColumn<Aula, String> colHorario;
    @FXML private TableColumn<Aula, String> colStatus;
    @FXML private TableColumn<Aula, String> colModalidade;
    @FXML private TableColumn<Aula, String> colProfessor;
    @FXML private TableColumn<Aula, String> colAlunos;

    @FXML private TextField consultaField;

    @FXML private DatePicker dataField;
    @FXML private TextField horarioField;
    @FXML private ComboBox<StatusAula> statusField;
    @FXML private ComboBox<Modalidade> modalidadeField;
    @FXML private TextField professorNomeField;
    @FXML private TextArea alunosNomeField;

    @FXML private Button botaoCadastro;
    
    @FXML private Label erroCadastro;
    @FXML private Label erroTabela;

    private final AulaService service = new AulaServiceImpl();
    private Aula novaAula = new Aula();
    private boolean modoEdicao = false;

    private void mostrarMensagem(Label label, String mensagem, Color cor){
        erroCadastro.setVisible(false);
        erroTabela.setVisible(false);

        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }

    private void preencherCampos(){
        final ProfessorService professorService = new ProfessorServiceImpl();
        final AlunoService alunoService = new AlunoServiceImpl();

        novaAula.setData(dataField.getValue());
        novaAula.setHorario(LocalTime.parse(horarioField.getText()));
        novaAula.setStatus(statusField.getValue());
        novaAula.setModalidade(modalidadeField.getValue());

        List<Professor> professores = professorService.buscarPorNome(professorNomeField.getText());
        if (professores == null || professores.isEmpty()) {
            throw new IllegalArgumentException("Professor não encontrado!");
        }
        novaAula.setProfessor(professores.getFirst());

        List<Aluno> alunos = new ArrayList<>();
        if (alunosNomeField.getText() != null && !alunosNomeField.getText().isEmpty()) {
            for (String nome : alunosNomeField.getText().split(",")) {
                nome = nome.trim();
                if (!nome.isEmpty()) {
                    List<Aluno> alunosEncontrados = alunoService.buscarPorNome(nome);
                    if (alunosEncontrados == null || alunosEncontrados.isEmpty()) {
                        throw new IllegalArgumentException("Aluno '" + nome + "' não encontrado!");
                    }
                    alunos.add(alunosEncontrados.getFirst());
                }
            }
        }
        novaAula.setAlunos(alunos);
    }
    private void limparCampos(){
        dataField.setValue(null);
        horarioField.clear();
        statusField.setValue(null);
        modalidadeField.setValue(null);
        professorNomeField.clear();
        alunosNomeField.clear();

        novaAula = new Aula();
        botaoCadastro.setText("Cadastrar");
        modoEdicao = false;
    }

    private void configurarTabela(){
        colData.setCellValueFactory(cellData ->
                (new SimpleStringProperty(cellData.getValue().getData().toString())));
        colHorario.setCellValueFactory(cellData ->
                (new SimpleStringProperty(cellData.getValue().getHorario().toString())));
        colStatus.setCellValueFactory(cellData ->
                (new SimpleStringProperty(cellData.getValue().getStatus().getDescricao())));
        colModalidade.setCellValueFactory(cellData ->{
            Modalidade modalidade = cellData.getValue().getModalidade();
            return new SimpleStringProperty(modalidade.getNome());
        });
        colProfessor.setCellValueFactory(cellData ->{
            Professor professor = cellData.getValue().getProfessor();
            return new SimpleStringProperty(professor.getNome());
        });
        colAlunos.setCellValueFactory(cellData ->{
            List<Aluno> alunos = cellData.getValue().getAlunos();
            String alunosStr = alunos.stream()
                    .map(Aluno::getNome)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(alunosStr);
        });

        atualizarTabela();
    }
    private void atualizarTabela(){
        try {
            List<Aula> aulas = service.buscarTodos();
            aulaTable.getItems().setAll(aulas);
            aulaTable.setPlaceholder(new Label("Nenhuma aula cadastrada"));
        }
        catch (Exception e){
            mostrarMensagem(erroTabela, "Não foi possível carregar os alunos: " + e.getMessage(), Color.RED);
        }
    }

    private void configurarNomes(){
        alunosNomeField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove espaços desnecessários após vírgulas
            String formatted = newValue.replaceAll(",\\s+", ",")
                    .replaceAll("\\s+,", ",");

            if (!newValue.equals(formatted)) {
                alunosNomeField.setText(formatted);
            }
        });
    }
    private void configurarStatus(){
        statusField.getItems().setAll(StatusAula.values());

        statusField.setCellFactory(param -> new ListCell<StatusAula>() {
            @Override
            protected void updateItem(StatusAula status, boolean empty) {
                super.updateItem(status, empty);
                setText(empty || status == null ? null : status.getDescricao());
            }
        });

        statusField.setConverter(new StringConverter<StatusAula>() {
            @Override
            public String toString(StatusAula status) {
                return status != null ? status.getDescricao() : "";
            }

            @Override
            public StatusAula fromString(String string) {
                return null;
            }
        });
    }
    private void configurarModalidades(){
        final ModalidadeService modalidadeService = new ModalidadeServiceImpl();

        modalidadeField.getItems().addAll(modalidadeService.buscarAbertas());

        modalidadeField.setCellFactory(param -> new ListCell<Modalidade>(){
            @Override
            protected void updateItem(Modalidade modalidade, boolean empty) {
                super.updateItem(modalidade, empty);
                if (empty || modalidade == null)
                    setText(null);
                else
                    setText(modalidade.getNome() + " (" + modalidade.getTipo().getDescricao() + ")");
            }
        });

        modalidadeField.setConverter(new StringConverter<Modalidade>() {
            @Override
            public String toString(Modalidade modalidade) {
                if (modalidade == null)
                    return null;
                return modalidade.getNome() + " (" + modalidade.getTipo().getDescricao() + ")";
            }

            @Override
            public Modalidade fromString(String string) {
                return null;
            }
        });
    }

    private void configurarCampoPesquisa(){
        final PauseTransition pause = new PauseTransition(Duration.millis(500));

        consultaField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(e -> realizarPesquisa(newValue));
            pause.playFromStart();
        });

        // Enter para pesquisar
        consultaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                realizarPesquisa(consultaField.getText());
            }
        });
    }
    private void realizarPesquisa(String termo){
        if (termo == null || termo.isEmpty()) {
            atualizarTabela();
        }
        else {
            try {
                List<Aula> resultados = service.buscarPorTodosCampos(termo);
                aulaTable.getItems().setAll(resultados);

                if (resultados.isEmpty()) {
                    aulaTable.getItems().clear();
                    aulaTable.setPlaceholder(new Label("Nenhum aluno encontrado com o termo: " + termo));
                } else {
                    aulaTable.getItems().setAll(resultados);
                    aulaTable.setPlaceholder(new Label(""));
                }
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro na pesquisa: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void initialize(){
        configurarNomes();
        configurarStatus();
        configurarModalidades();

        configurarTabela();
        configurarCampoPesquisa();
    }

    @FXML
    void cadastrarAula(ActionEvent event) {
        try {
            preencherCampos();

            if(!modoEdicao){
                service.cadastrar(novaAula);
                mostrarMensagem(erroCadastro, "Aula cadastrada com sucesso!", Color.WHITE);
            }
            else {
                service.atualizar(novaAula);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;
                mostrarMensagem(erroCadastro, "Aula editada com sucesso!", Color.WHITE);
            }

            limparCampos();
            atualizarTabela();
        }
        catch (IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.YELLOW);
        }
        catch (Exception e){
            mostrarMensagem(erroCadastro, "Erro ao salvar aula: " + e.getMessage(), Color.YELLOW);
        }
    }

    @FXML
    void excluirAula(ActionEvent event) {
        Aula selecionada = aulaTable.getSelectionModel().getSelectedItem();

        if(selecionada == null)
            mostrarMensagem(erroTabela, "Escolha uma aula para excluir!", Color.RED);
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão da aula");
            alert.setContentText("Tem certeza que deseja excluir a aula selecionada?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.excluir(selecionada);
                        mostrarMensagem(erroTabela, "Aluno removido com sucesso!", Color.GREEN);
                        atualizarTabela();
                    }
                    catch (Exception e) {
                        mostrarMensagem(erroTabela, "Erro ao excluir aluno: " + e.getMessage(), Color.RED);
                    }
                }
            });
        }
    }

    @FXML
    void editarAula(ActionEvent event) {
        Aula selecionada = aulaTable.getSelectionModel().getSelectedItem();

        if (selecionada == null)
            mostrarMensagem(erroTabela, "Selecione uma aula para editar", Color.RED);
        else {
            try {
                modoEdicao = true;
                novaAula = selecionada;

                horarioField.setText(selecionada.getHorario().toString());
                statusField.setValue(selecionada.getStatus());
                modalidadeField.setValue(selecionada.getModalidade());

                Professor professor = selecionada.getProfessor();
                professorNomeField.setText(professor != null ? professor.getNome() : "");

                alunosNomeField.setText(
                        selecionada.getAlunos().stream()
                                .map(Aluno::getNome)
                                .collect(Collectors.joining(", "))
                );

                botaoCadastro.setText("Salvar");
            }
            catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar aula: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    private void imprimirPDF() {
        try {
            // Preparar dados para o PDF
            String titulo = "Relatório de Aulas";
            List<String> cabecalhos = List.of("Data", "Horário", "Status", "Modalidade", "Professor", "Alunos");

            List<Map<String, String>> dados = aulaTable.getItems().stream()
                    .map(aula -> Map.of(
                            "Data", aula.getData().toString(),
                            "Horário", aula.getHorario().toString(),
                            "Status", aula.getStatus().getDescricao(),
                            "Modalidade", aula.getModalidade() != null ?
                                    aula.getModalidade().getNome() : "N/A",
                            "Professor", aula.getProfessor() != null ?
                                    aula.getProfessor().getNome() : "N/A",
                            "Alunos", aula.getAlunos().stream()
                                    .map(Aluno::getNome)
                                    .collect(Collectors.joining(", "))
                    ))
                    .toList();

            if (dados.isEmpty()) {
                mostrarMensagem(erroTabela, "Não há dados para gerar o PDF!", Color.YELLOW);
                return;
            }

            // Criar FileChooser para selecionar onde salvar
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Relatório de Aulas");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf"));

            String nomePadrao = "relatorio_aulas_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            fileChooser.setInitialFileName(nomePadrao);

            File arquivo = fileChooser.showSaveDialog(aulaTable.getScene().getWindow());

            if (arquivo != null) {
                String caminhoArquivo = arquivo.getAbsolutePath();
                if (!caminhoArquivo.toLowerCase().endsWith(".pdf")) {
                    caminhoArquivo += ".pdf";
                }

                // Gerar PDF
                PDFGenerator.gerarRelatorio(titulo, cabecalhos, dados, caminhoArquivo);

                mostrarMensagem(erroTabela, "PDF gerado com sucesso em: " + caminhoArquivo, Color.GREEN);

                // Abrir o arquivo (opcional)
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(caminhoArquivo));
                }
            }
        } catch (Exception e) {
            mostrarMensagem(erroTabela, "Erro ao gerar PDF: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    @FXML
    private void voltarDashboard(){
        View.dashboard();
    }
}
