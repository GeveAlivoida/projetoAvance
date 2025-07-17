package br.edu.ufersa.avance.projetoavance.controller;

import br.edu.ufersa.avance.projetoavance.model.entities.Responsavel;
import br.edu.ufersa.avance.projetoavance.model.services.ResponsavelService;
import br.edu.ufersa.avance.projetoavance.model.services.ResponsavelServiceImpl;
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

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ResponsavelController {
    @FXML private TableView<Responsavel> responsavelTable;
    @FXML private TableColumn<Responsavel, String> colCpf;
    @FXML private TableColumn<Responsavel, String> colEmail;
    @FXML private TableColumn<Responsavel, String> colNome;
    @FXML private TableColumn<Responsavel, String> colTelefone;

    @FXML private TextField consultaField;

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField telefoneField;
    @FXML private DatePicker nascimentoField;
    @FXML private TextField emailField;

    @FXML private Button botaoCadastro;

    @FXML private Label erroCadastro;
    @FXML private Label erroTabela;

    private final ResponsavelService service = new ResponsavelServiceImpl();
    private Responsavel novoResponsavel = new Responsavel();
    private boolean modoEdicao = false;

    private void mostrarMensagem(Label label, String mensagem, Color cor){
        erroCadastro.setVisible(false);
        erroTabela.setVisible(false);

        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }
    private void preencherCampos(){
        novoResponsavel.setNome(nomeField.getText());
        novoResponsavel.setCpf(cpfField.getText());
        novoResponsavel.setTelefone(telefoneField.getText());
        novoResponsavel.setNascimento(nascimentoField.getValue());
        novoResponsavel.setEmail(emailField.getText());
    }
    private void limparCampos(){
        nomeField.clear();
        cpfField.clear();
        telefoneField.clear();
        nascimentoField.setValue(null);
        emailField.clear();

        novoResponsavel = new Responsavel();
    }

    private void configurarTabela(){
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome()));
        colCpf.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCpf()));
        colEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));
        colTelefone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefone()));

        atualizarTabela();
    }
    private void atualizarTabela(){
        try {
            List<Responsavel> responsaveis = service.buscarTodos();
            responsavelTable.getItems().setAll(responsaveis);
            responsavelTable.setPlaceholder(new Label("Nenhum responsável cadastrado"));
        }
        catch (Exception e) {
            mostrarMensagem(erroTabela, "Não foi possível carregar os responsáveis: " + e.getMessage(), Color.RED);
        }
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
        if (termo == null || termo.isEmpty())
            atualizarTabela();
        else {
            try {
                List<Responsavel> resultados = service.buscarPorTodosCampos(termo);
                responsavelTable.getItems().setAll(resultados);

                if (resultados.isEmpty()) {
                    responsavelTable.getItems().clear();
                    responsavelTable.setPlaceholder(new Label("Nenhum responsavel encontrado com: " + termo));
                } else {
                    responsavelTable.getItems().setAll(resultados);
                    responsavelTable.setPlaceholder(new Label(""));
                }
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro na pesquisa: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void initialize(){
        configurarTabela();
        configurarCampoPesquisa();
    }

    @FXML
    void cadastrarResponsavel(ActionEvent event) {
        try {
            preencherCampos();

            if(!modoEdicao) {
                service.cadastrar(novoResponsavel);
                mostrarMensagem(erroCadastro, "Responsável cadastrado com sucesso!", Color.WHITE);
            }
            else {
                service.atualizar(novoResponsavel);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;
                mostrarMensagem(erroCadastro, "Responsável editado com sucesso!", Color.WHITE);
            }

            limparCampos();
            atualizarTabela();
        }
        catch (IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.YELLOW);
        }
        catch (Exception e){
            mostrarMensagem(erroCadastro, "Erro ao salvar responsável: " + e.getMessage(), Color.YELLOW);
        }
    }

    @FXML
    void excluirResponsavel(ActionEvent event) {
        Responsavel selecionado = responsavelTable.getSelectionModel().getSelectedItem();

        if(selecionado == null)
            mostrarMensagem(erroTabela, "Escolha um responsável para excluir!", Color.RED);
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão do responsável");
            alert.setContentText("Tem certeza que deseja excluir " + selecionado.getNome() + "?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.excluir(selecionado);
                        mostrarMensagem(erroTabela, "Responsável removido com sucesso!", Color.GREEN);
                        atualizarTabela();
                    }
                    catch (Exception e) {
                        mostrarMensagem(erroTabela, "Erro ao excluir responsável: " + e.getMessage(), Color.RED);
                    }
                }
            });
        }
    }

    @FXML
    void editarResponsavel(ActionEvent event) {
        Responsavel selecionado = responsavelTable.getSelectionModel().getSelectedItem();

        if (selecionado == null)
            mostrarMensagem(erroTabela, "Escolha um responsável para editar!", Color.RED);
        else {
            try {
                modoEdicao = true;
                novoResponsavel = selecionado;

                nomeField.setText(selecionado.getNome());
                cpfField.setText(selecionado.getCpf());
                nascimentoField.setValue(selecionado.getNascimento());
                telefoneField.setText(selecionado.getTelefone());
                emailField.setText(selecionado.getEmail());

                botaoCadastro.setText("Salvar");
            }
            catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar responsável: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    private void imprimirPDF() {
        try {
            // Preparar dados para o PDF
            String titulo = "Relatório de Responsáveis";
            List<String> cabecalhos = List.of("Nome", "CPF", "Telefone", "Data Nascimento", "Email");

            List<Map<String, String>> dados = responsavelTable.getItems().stream()
                    .map(responsavel -> Map.of(
                            "Nome", responsavel.getNome(),
                            "CPF", responsavel.getCpf(),
                            "Telefone", responsavel.getTelefone(),
                            "Data Nascimento", responsavel.getNascimento() != null ?
                                    responsavel.getNascimento().toString() : "N/A",
                            "Email", responsavel.getEmail()
                    ))
                    .toList();

            if (dados.isEmpty()) {
                mostrarMensagem(erroTabela, "Não há dados para gerar o PDF!", Color.YELLOW);
                return;
            }

            // Criar FileChooser para selecionar onde salvar
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Relatório de Responsáveis");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf"));

            String nomePadrao = "relatorio_responsaveis_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            fileChooser.setInitialFileName(nomePadrao);

            File arquivo = fileChooser.showSaveDialog(responsavelTable.getScene().getWindow());

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
