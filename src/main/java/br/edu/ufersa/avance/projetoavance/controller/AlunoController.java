package br.edu.ufersa.avance.projetoavance.controller;

import br.edu.ufersa.avance.projetoavance.exceptions.FullVacanciesException;
import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoavance.model.entities.Responsavel;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlunoController {
    @FXML private TableView<Aluno> alunoTable;
    @FXML private TableColumn<Aluno, String> colNome;
    @FXML private TableColumn<Aluno, String> colCpf;
    @FXML private TableColumn<Aluno, String> colTelefone;
    @FXML private TableColumn<Aluno, String> colEmail;
    @FXML private TableColumn<Aluno, String> colModalidade;

    @FXML private TextField consultaField;

    @FXML private TextField nomeField;
    @FXML private TextField cpfAlunoField;
    @FXML private TextField cpfRespField;
    @FXML private DatePicker nascimentoField;
    @FXML private TextField telefoneField;
    @FXML private TextField emailField;
    @FXML private ComboBox<Modalidade> modalidadeField;

    @FXML private Button botaoCadastro;

    @FXML private Label erroCadastro;
    @FXML private Label erroTabela;

    private final AlunoService service = new AlunoServiceImpl();
    private Aluno novoAluno = new Aluno();
    private boolean modoEdicao = false;

    private void mostrarMensagem(Label label, String mensagem, Color cor){
        erroCadastro.setVisible(false);
        erroTabela.setVisible(false);

        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }

    private void preencherCampos(){
        final ResponsavelService responsavelService = new ResponsavelServiceImpl();

        novoAluno.setNome(nomeField.getText());
        novoAluno.setCpf(cpfAlunoField.getText());
        novoAluno.setNascimento(nascimentoField.getValue());
        novoAluno.setTelefone(telefoneField.getText());
        novoAluno.setEmail(emailField.getText());

        if (!cpfRespField.getText().isEmpty()) {
            Responsavel responsavel = responsavelService.buscarPorCpf(cpfRespField.getText());
            novoAluno.setResponsavel(responsavel);
        }
        else novoAluno.setResponsavel(null);

        if (modalidadeField.getValue() != null)
            novoAluno.adicionarModalidade(modalidadeField.getValue());
        else
            novoAluno.setModalidades(new ArrayList<>());
    }
    private void limparCampos(){
        nomeField.clear();
        cpfAlunoField.clear();
        cpfRespField.clear();
        nascimentoField.setValue(null);
        telefoneField.clear();
        emailField.clear();
        modalidadeField.setValue(null);

        novoAluno = new Aluno();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome()));
        colCpf.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCpf()));
        colTelefone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefone()));
        colEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));

        // exibição da coluna de modalidades
        colModalidade.setCellValueFactory(cellData -> {
            Aluno aluno = cellData.getValue();
            String modalidades = aluno.getModalidades().stream()
                    .map(Modalidade::getNome)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(modalidades);
        });

        atualizarTabela();
    }
    private void atualizarTabela() {
        try {
            List<Aluno> alunos = service.buscarTodos();
            alunoTable.getItems().setAll(alunos);
            alunoTable.setPlaceholder(new Label("Nenhum aluno cadastrado"));
        }
        catch (Exception e) {
            mostrarMensagem(erroTabela, "Não foi possível carregar os alunos: " + e.getMessage(), Color.RED);
        }
    }

    private void configurarModalidades(){
        final ModalidadeService modalidadeService = new ModalidadeServiceImpl();

        modalidadeField.getItems().addAll(modalidadeService.buscarAbertas());

        modalidadeField.setCellFactory(param -> new ListCell<Modalidade>() {
            @Override
            protected void updateItem(Modalidade modalidade, boolean empty) {
                super.updateItem(modalidade, empty);
                if (empty || modalidade == null) {
                    setText(null);
                } else {
                    setText(modalidade.getNome() + " (" + modalidade.getTipo().getDescricao() + ")");
                }
            }
        });

        modalidadeField.setConverter(new StringConverter<Modalidade>() {
            @Override
            public String toString(Modalidade modalidade) {
                if (modalidade == null) {
                    return null;
                }
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
                List<Aluno> resultados = service.buscarPorTodosCampos(termo);
                alunoTable.getItems().setAll(resultados);

                if (resultados.isEmpty()) {
                    alunoTable.getItems().clear();
                    alunoTable.setPlaceholder(new Label("Nenhum aluno encontrado com o termo: " + termo));
                } else {
                    alunoTable.getItems().setAll(resultados);
                    alunoTable.setPlaceholder(new Label(""));
                }
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro na pesquisa: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void initialize(){
        configurarModalidades();

        configurarTabela();
        configurarCampoPesquisa();
    }

    @FXML
    void cadastrarAluno(ActionEvent event) {
        try {
            preencherCampos();

            if(!modoEdicao) {
                if (service.buscarPorCpf(novoAluno.getCpf()) != null) {
                    throw new IllegalArgumentException("Aluno já cadastrado!");
                }
                service.cadastrar(novoAluno);
                mostrarMensagem(erroCadastro, "Aluno cadastrado com sucesso!", Color.WHITE);
            }
            else {
                Aluno alunoAtual = service.buscarPorId(novoAluno.getId());
                if (alunoAtual == null) {
                    throw new IllegalArgumentException("Aluno não existe!");
                }
                service.atualizar(novoAluno);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;
                mostrarMensagem(erroCadastro, "Aluno editado com sucesso!", Color.WHITE);
            }

            limparCampos();
            atualizarTabela();
        }
        catch (FullVacanciesException | IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.YELLOW);
        }
        catch (Exception e){
            mostrarMensagem(erroCadastro, "Erro ao salvar aluno: " + e.getMessage(), Color.YELLOW);
        }
    }

    @FXML
    void excluirAluno(ActionEvent event) {
        Aluno selecionado = alunoTable.getSelectionModel().getSelectedItem();

        if(selecionado == null)
            mostrarMensagem(erroTabela, "Escolha um aluno para excluir!", Color.RED);
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão do aluno");
            alert.setContentText("Tem certeza que deseja excluir " + selecionado.getNome() + "?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.excluir(selecionado);
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
    void editarAluno(ActionEvent event) {
        Aluno selecionado = alunoTable.getSelectionModel().getSelectedItem();

        if(selecionado == null)
            mostrarMensagem(erroTabela, "Escolha um aluno para editar!", Color.RED);
        else {
            try {
                modoEdicao = true;
                novoAluno = selecionado;

                nomeField.setText(selecionado.getNome());
                cpfAlunoField.setText(selecionado.getCpf());
                if (selecionado.getResponsavel() != null)
                    cpfRespField.setText(selecionado.getResponsavel().getCpf());
                else
                    cpfRespField.clear();
                nascimentoField.setValue(selecionado.getNascimento());
                telefoneField.setText(selecionado.getTelefone());
                emailField.setText(selecionado.getEmail());
                if (!selecionado.getModalidades().isEmpty())
                    modalidadeField.setValue(selecionado.getModalidades().getLast());
                else
                    modalidadeField.setValue(null);

                botaoCadastro.setText("Salvar");
            }
            catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar aluno: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    private void imprimirPDF() {
        try {
            // Preparar dados para o PDF
            String titulo = "Relatório de Alunos";
            List<String> cabecalhos = List.of("Nome", "CPF", "Telefone", "Email", "Modalidades", "Responsável");

            List<Map<String, String>> dados = alunoTable.getItems().stream()
                    .map(aluno -> Map.of(
                            "Nome", aluno.getNome(),
                            "CPF", aluno.getCpf(),
                            "Telefone", aluno.getTelefone(),
                            "Email", aluno.getEmail(),
                            "Modalidades", aluno.getModalidades().stream()
                                    .map(Modalidade::getNome)
                                    .collect(Collectors.joining(", ")),
                            "Responsável", aluno.getResponsavel() != null ?
                                    aluno.getResponsavel().getNome() : "N/A"
                    ))
                    .toList();

            if (dados.isEmpty()) {
                mostrarMensagem(erroTabela, "Não há dados para gerar o PDF!", Color.YELLOW);
                return;
            }

            // Criar FileChooser para selecionar onde salvar
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Relatório de Alunos");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf"));

            String nomePadrao = "relatorio_alunos_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            fileChooser.setInitialFileName(nomePadrao);

            File arquivo = fileChooser.showSaveDialog(alunoTable.getScene().getWindow());

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
