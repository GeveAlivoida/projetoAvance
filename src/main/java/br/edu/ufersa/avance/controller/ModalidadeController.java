package br.edu.ufersa.avance.controller;

import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.TipoModalidade;
import br.edu.ufersa.avance.model.services.ModalidadeService;
import br.edu.ufersa.avance.model.services.ModalidadeServiceImpl;
import br.edu.ufersa.avance.model.services.ProfessorService;
import br.edu.ufersa.avance.model.services.ProfessorServiceImpl;
import br.edu.ufersa.avance.view.View;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.List;

public class ModalidadeController {
    @FXML private TableView<Modalidade> modalidadeTable;
    @FXML private TableColumn<Modalidade, String> colNome;
    @FXML private TableColumn<Modalidade, String> colProfessor;
    @FXML private TableColumn<Modalidade, String> colTipo;
    @FXML private TableColumn<Modalidade, String> colVagas;
    @FXML private TableColumn<Modalidade, String> colValor;

    @FXML private TextField consultaField;

    @FXML private TextField nomeField;
    @FXML private TextField professorField;
    @FXML private ComboBox<TipoModalidade> tipoField;
    @FXML private TextField vagasField;
    @FXML private TextField valorField;

    @FXML private Button botaoCadastro;

    @FXML private Label erroCadastro;
    @FXML private Label erroTabela;

    private final ModalidadeService service = new ModalidadeServiceImpl();
    private Modalidade novaModalidade = new Modalidade();
    private boolean modoEdicao = false;

    private void mostrarMensagem(Label label, String mensagem, Color cor){
        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }

    private void preencherCampos(){
        ProfessorService professorService = new ProfessorServiceImpl();


        novaModalidade.setNome(nomeField.getText());
        novaModalidade.setTipo(tipoField.getValue());
        novaModalidade.setVagas(Integer.parseInt(vagasField.getText()));

        Professor professor = professorService.buscarPorNome(professorField.getText().trim())
                .stream()
                .findFirst()
                .orElse(null);
        novaModalidade.setProfessor(professor);

        String valorTexto = valorField.getText()
                .replace("R$", "")
                .trim()
                .replace(".", "")
                .replace(",", ".");
        novaModalidade.setValor(Double.parseDouble(valorTexto));
    }
    private void limparCampos() {
        nomeField.clear();
        tipoField.setValue(null);
        professorField.clear();
        vagasField.clear();
        valorField.clear();

        novaModalidade = new Modalidade();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome()));
        colTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipo().getDescricao()));
        colVagas.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getVagas())));
        colValor.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().getValor())));

        colProfessor.setCellValueFactory(cellData -> {
            Professor professor = cellData.getValue().getProfessor();
            return new SimpleStringProperty(professor.getNome());
        });

        atualizarTabela();
    }
    private void atualizarTabela() {
        try {
            List<Modalidade> modalidades = service.buscarTodos();
            modalidadeTable.getItems().setAll(modalidades);
            modalidadeTable.setPlaceholder(new Label("Nenhuma modalidade cadastrada"));
        }
        catch (Exception e) {
            mostrarMensagem(erroTabela, "Não foi possível carregar as modalidades: " + e.getMessage(), Color.RED);
        }
    }

    private void configurarVagas(){
        vagasField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Aceita apenas dígitos
                vagasField.setText(oldValue);
            }
        });
    }
    private void configurarValor(){
        valorField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d*([\\,]\\d{0,2})?$")) { // Aceita números com até 2 casas decimais
                valorField.setText(oldValue);
            }
        });

        // Formata quando o campo perde o foco ou ao apertar enter
        valorField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                formatarValor();
            }
        });

        valorField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                formatarValor();
            }
        });
    }
    private void configurarTipo(){
        tipoField.getItems().setAll(TipoModalidade.values());

        tipoField.setCellFactory(param -> new ListCell<TipoModalidade>() {
            @Override
            protected void updateItem(TipoModalidade tipo, boolean empty) {
                super.updateItem(tipo, empty);
                setText(empty || tipo == null ? null : tipo.getDescricao());
            }
        });

        tipoField.setConverter(new StringConverter<TipoModalidade>() {
            @Override
            public String toString(TipoModalidade tipo) {
                return tipo != null ? tipo.getDescricao() : "";
            }

            @Override
            public TipoModalidade fromString(String string) {
                if (string == null || string.isEmpty()) return null;
                for (TipoModalidade tipo : TipoModalidade.values()) {
                    if (tipo.getDescricao().equalsIgnoreCase(string)) return tipo;
                }
                return null;
            }
        });
    }

    private void formatarValor(){
        try {
            String texto = valorField.getText().replace("R$", "").trim().replace(".", "").replace(",", ".");
            double valor = Double.parseDouble(texto);
            valorField.setText(String.format("R$ %,.2f", valor).replace(".", "X").replace(",", ".").replace("X", ","));
        } catch (NumberFormatException e) {
            valorField.setText("");
        }
    }

    private void configurarCampoPesquisa(){
        final PauseTransition pause = new PauseTransition(Duration.millis(500));

        consultaField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(e -> realizarPesquisa(newValue));
            pause.playFromStart();
        });

        consultaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                realizarPesquisa(consultaField.getText());
            }
        });
    }
    private void realizarPesquisa(String termo) {
        if (termo == null || termo.isEmpty())
            atualizarTabela();
        else {
            try {
                List<Modalidade> resultados = service.buscarPorTodosCampos(termo);
                modalidadeTable.getItems().setAll(resultados);

                if (resultados.isEmpty()) {
                    modalidadeTable.getItems().clear();
                    modalidadeTable.setPlaceholder(new Label("Nenhuma modalidade encontrada com: " + termo));
                } else {
                    modalidadeTable.getItems().setAll(resultados);
                    modalidadeTable.setPlaceholder(new Label(""));
                }
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro na pesquisa: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void initialize(){
        configurarTipo();
        configurarVagas();
        configurarValor();

        configurarTabela();
        configurarCampoPesquisa();
    }

    @FXML
    void cadastrarModalidade(ActionEvent event) {
        try {
            preencherCampos();

            if (!modoEdicao) {
                service.cadastrar(novaModalidade);
                mostrarMensagem(erroCadastro, "Modalidade cadastrada com sucesso!", Color.WHITE);
            } else {
                service.atualizar(novaModalidade);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;
                mostrarMensagem(erroCadastro, "Modalidade editada com sucesso!", Color.WHITE);
            }

            limparCampos();
            atualizarTabela();
        }
        catch (NumberFormatException e) {
            mostrarMensagem(erroCadastro, "Vagas e Valor devem ser números válidos", Color.YELLOW);
        }
        catch (IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.YELLOW);
        }
        catch (Exception e) {
            mostrarMensagem(erroCadastro, "Erro ao salvar modalidade: " + e.getMessage(), Color.YELLOW);
        }
    }

    @FXML
    void excluirModalidade(ActionEvent event) {
        Modalidade selecionada = modalidadeTable.getSelectionModel().getSelectedItem();

        if (selecionada == null)
            mostrarMensagem(erroTabela, "Selecione uma modalidade para excluir!", Color.RED);
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão da modalidade");
            alert.setContentText("Tem certeza que deseja excluir " + selecionada.getNome() + "?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.excluir(selecionada);
                        mostrarMensagem(erroTabela, "Modalidade removida com sucesso!", Color.GREEN);
                        atualizarTabela();
                    }
                    catch (Exception e) {
                        mostrarMensagem(erroTabela, "Erro ao excluir modalidade: " + e.getMessage(), Color.RED);
                    }
                }
            });
        }
    }

    @FXML
    void editarModalidade(ActionEvent event) {
        Modalidade selecionada = modalidadeTable.getSelectionModel().getSelectedItem();

        if (selecionada == null)
            mostrarMensagem(erroTabela, "Selecione uma modalidade para editar", Color.RED);
        else {
            try {
                modoEdicao = true;
                novaModalidade = new Modalidade();

                novaModalidade.setId(selecionada.getId());
                nomeField.setText(selecionada.getNome());
                tipoField.setValue(selecionada.getTipo());
                vagasField.setText(String.valueOf(selecionada.getVagas()));
                valorField.setText(String.format("%.2f", selecionada.getValor())
                        .replace(".", "X").replace(",", ".")
                        .replace("X", ","));
                professorField.setText(selecionada.getProfessor().getNome());

                botaoCadastro.setText("Salvar");
            }
            catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar modalidade: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    private void voltarDashboard(){
        View.dashboard();
    }
}
