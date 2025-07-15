package br.edu.ufersa.avance.controller;

import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.StatusProfessor;
import br.edu.ufersa.avance.model.services.ProfessorService;
import br.edu.ufersa.avance.model.services.ProfessorServiceImpl;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.List;

public class ProfessorController {
    @FXML private TableView<Professor> professorTable;
    @FXML private TableColumn<Professor, String> colNome;
    @FXML private TableColumn<Professor, String> colCpf;
    @FXML private TableColumn<Professor, String> colEmail;
    @FXML private TableColumn<Professor, String> colTelefone;
    @FXML private TableColumn<Professor, String> colEspecialidade;
    @FXML private TableColumn<Professor, String> colSalario;
    @FXML private TableColumn<Professor, String> colConta;
    @FXML private TableColumn<Professor, String> colStatus;

    @FXML private TextField consultaField;

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private TextField especialidadeField;
    @FXML private TextField salarioField;
    @FXML private TextField contaBancoField;
    @FXML private ComboBox<StatusProfessor> statusField;

    @FXML private Button botaoCadastro;

    @FXML private Label erroCadastro;
    @FXML private Label erroTabela;

    private final ProfessorService service = new ProfessorServiceImpl();
    private Professor novoProfessor = new Professor();
    private boolean modoEdicao = false;

    private void mostrarMensagem(Label label, String mensagem, Color cor){
        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }

    private void preencherCampos(){
        novoProfessor.setNome(nomeField.getText());
        novoProfessor.setCpf(cpfField.getText());
        novoProfessor.setEmail(emailField.getText());
        novoProfessor.setTelefone(telefoneField.getText());
        novoProfessor.setEspecialidade(especialidadeField.getText());
        novoProfessor.setContaBanco(contaBancoField.getText());

        novoProfessor.setStatus(StatusProfessor.ATIVO);

        String salarioTexto = salarioField.getText().
                replace("R$", "").
                trim().
                replace(".", "").
                replace(",", ".");
        novoProfessor.setSalario(Double.parseDouble(salarioTexto));
    }
    private void limparCampos(){
        nomeField.clear();
        cpfField.clear();
        emailField.clear();
        telefoneField.clear();
        especialidadeField.clear();
        salarioField.clear();
        contaBancoField.clear();
        statusField.setValue(StatusProfessor.ATIVO);
        statusField.setDisable(true);

        novoProfessor = new Professor();
    }

    private void configurarTabela(){
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome()));
        colCpf.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCpf()));
        colTelefone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefone()));
        colEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));
        colEspecialidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEspecialidade()));
        colSalario.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().getSalario())));
        colConta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getContaBanco()));
        colStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().getDescricao()));

        atualizarTabela();
    }
    private void atualizarTabela(){
        try {
            List<Professor> professores = service.buscarTodos();
            professorTable.getItems().setAll(professores);
            professorTable.setPlaceholder(new Label("Nenhum professor cadastrado"));
        }
        catch (Exception e) {
            mostrarMensagem(erroTabela, "Não foi possível carregar os professores: " + e.getMessage(), Color.RED);
        }
    }

    private void configurarSalario(){
        // Aceita números com até 2 casas decimais
        salarioField.textProperty().addListener((observable,
                                                 oldValue,
                                                 newValue) -> {
            if (!newValue.matches("^\\d*([\\,]\\d{0,2})?$")) salarioField.setText(oldValue);
        });

        // Formata quando o campo perde o foco ou ao apertar enter
        salarioField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                formatarSalario();
            }
        });

        salarioField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                formatarSalario();
            }
        });
    }
    private void configurarStatus(){
        statusField.getItems().setAll(StatusProfessor.values());

        statusField.setCellFactory(param -> new ListCell<StatusProfessor>() {
            @Override
            protected void updateItem(StatusProfessor status, boolean empty) {
                super.updateItem(status, empty);
                setText(empty || status == null ? null : status.getDescricao());
            }
        });

        statusField.setConverter(new StringConverter<StatusProfessor>() {
            @Override
            public String toString(StatusProfessor status) {
                return status != null ? status.getDescricao() : "";
            }

            @Override
            public StatusProfessor fromString(String string) {
                if (string == null || string.isEmpty()) return null;
                for (StatusProfessor status : StatusProfessor.values()) {
                    if (status.getDescricao().equalsIgnoreCase(string)) return status;
                }
                return null;
            }
        });
    }

    private void formatarSalario(){
        try {
            String texto = salarioField.getText().replace("R$", "").trim().replace(".", "").replace(",", ".");
            double valor = Double.parseDouble(texto);
            salarioField.setText(String.format("R$ %,.2f", valor).replace(".", "X").replace(",", ".").replace("X", ","));
        } catch (NumberFormatException e) {
            salarioField.setText("");
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
                List<Professor> resultados = service.buscarPorTodosCampos(termo);
                professorTable.getItems().setAll(resultados);

                if (resultados.isEmpty()) {
                    professorTable.getItems().clear();
                    professorTable.setPlaceholder(new Label("Nenhum professor encontrado com: " + termo));
                } else {
                    professorTable.getItems().setAll(resultados);
                    professorTable.setPlaceholder(new Label(""));
                }
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro na pesquisa: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void initialize(){
        configurarSalario();
        configurarStatus();

        configurarTabela();
        configurarCampoPesquisa();

        statusField.setDisable(true);
    }

    @FXML
    void cadastrarProfessor(ActionEvent event) {
        try{
            preencherCampos();

            if(!modoEdicao) {
                service.cadastrar(novoProfessor);
                mostrarMensagem(erroCadastro, "Professor cadastrado com sucesso!", Color.GREEN);
            }
            else {
                service.atualizar(novoProfessor);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;
                mostrarMensagem(erroCadastro, "Professor editado com sucesso!", Color.GREEN);
            }

            limparCampos();
            atualizarTabela();
        }
        catch (NumberFormatException e) {
            mostrarMensagem(erroCadastro, "Salário inválido! Use o formato 0,00", Color.YELLOW);
        }
        catch (IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.YELLOW);
        }
        catch (Exception e){
            mostrarMensagem(erroCadastro, "Erro ao salvar professor: " + e.getMessage(), Color.YELLOW);
        }
    }

    @FXML
    void excluirProfessor(ActionEvent event) {
        Professor selecionado = professorTable.getSelectionModel().getSelectedItem();

        if(selecionado == null)
            mostrarMensagem(erroTabela, "Escolha um professor para excluir!", Color.RED);
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão do professor");
            alert.setContentText("Tem certeza que deseja excluir " + selecionado.getNome() + "?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.excluir(selecionado);
                        mostrarMensagem(erroTabela, "Professor removido com sucesso!", Color.GREEN);
                        atualizarTabela();
                    }
                    catch (Exception e) {
                        mostrarMensagem(erroTabela, "Erro ao excluir professor: " + e.getMessage(), Color.RED);
                    }
                }
            });
        }
    }

    @FXML
    void editarProfessor(ActionEvent event) {
        Professor selecionado = professorTable.getSelectionModel().getSelectedItem();

        if (selecionado == null)
            mostrarMensagem(erroTabela, "Selecione um professor para editar", Color.RED);
        else {
            try {
                modoEdicao = true;
                novoProfessor = selecionado;

                nomeField.setText(selecionado.getNome());
                cpfField.setText(selecionado.getCpf());
                emailField.setText(selecionado.getEmail());
                telefoneField.setText(selecionado.getTelefone());
                especialidadeField.setText(selecionado.getEspecialidade());
                salarioField.setText(String.format("%.2f", selecionado.getSalario())
                        .replace(".", "X").replace(",", ".")
                        .replace("X", ","));
                contaBancoField.setText(selecionado.getContaBanco());
                statusField.setValue(selecionado.getStatus());
                statusField.setDisable(false);

                botaoCadastro.setText("Salvar");
            }
            catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar professor: " + e.getMessage(), Color.RED);
            }
        }
    }
}
