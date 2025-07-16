package br.edu.ufersa.avance.projetoAvance.controller;

import br.edu.ufersa.avance.projetoAvance.model.entities.*;
import br.edu.ufersa.avance.projetoAvance.model.enums.StatusPagamento;
import br.edu.ufersa.avance.projetoAvance.model.services.*;
import br.edu.ufersa.avance.projetoAvance.view.View;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PagamentoController {
    @FXML private TableView<Pagamento> pagamentoTable;
    @FXML private TableColumn<Pagamento, String> colAluno;
    @FXML private TableColumn<Pagamento, String> colMesRef;
    @FXML private TableColumn<Pagamento, String> colDataPagamento;
    @FXML private TableColumn<Pagamento, String> colDataValidade;
    @FXML private TableColumn<Pagamento, String> colStatus;

    @FXML private TextField consultaField;

    @FXML private TextField alunoNomeField;
    @FXML private TextField mesRefField;
    @FXML private ComboBox<StatusPagamento> statusField;
    @FXML private DatePicker dataValidadeField;

    @FXML private Button botaoCadastro;

    @FXML private Label erroCadastro;
    @FXML private Label erroTabela;

    private final PagamentoService service = new PagamentoServiceImpl();
    private Pagamento novoPagamento = new Pagamento();
    private boolean modoEdicao = false;

    private void mostrarMensagem(Label label, String mensagem, Color cor){
        erroCadastro.setVisible(false);
        erroTabela.setVisible(false);

        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }

    private void preencherCampos(){
        AlunoService alunoService = new AlunoServiceImpl();

        List<Aluno> alunos = alunoService.buscarPorNome(alunoNomeField.getText());
        if (alunos == null || alunos.isEmpty())
            throw new IllegalArgumentException("Aluno não encontrado!");
        else
            novoPagamento.setAluno(alunos.getFirst());

        if(modoEdicao)
            novoPagamento.setStatus(statusField.getValue());
        else
            novoPagamento.setStatus(StatusPagamento.PENDENTE);
        novoPagamento.setDataValidade(dataValidadeField.getValue());

        // Parse do mês de referência (formato MM/yyyy)
        String[] partesMesRef = mesRefField.getText().split("/");
        YearMonth mesRef = YearMonth.of(
                Integer.parseInt(partesMesRef[1]),
                Integer.parseInt(partesMesRef[0])
        );
        novoPagamento.setMesRef(mesRef);
    }
    private void limparCampos(){
        alunoNomeField.clear();
        mesRefField.clear();
        statusField.setValue(null);
        dataValidadeField.setValue(null);

        novoPagamento = new Pagamento();
    }

    private void configurarTabela(){
        colAluno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAluno().getNome()));
        colMesRef.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMesRef().toString()));
        colDataPagamento.setCellValueFactory(cellData -> {
            LocalDate data = cellData.getValue().getDataPagamento();
            return new SimpleStringProperty(data != null ? data.toString() : "N/A");
        });
        colDataValidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataValidade().toString()));
        colAluno.setCellValueFactory(cellData -> {
            Aluno aluno = cellData.getValue().getAluno();
            return new SimpleStringProperty(aluno.getNome());
        });
        colStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().getDescricao()));
        
        atualizarTabela();
    }
    private void atualizarTabela(){
        try {
            List<Pagamento> pagamentos = service.buscarTodos();
            pagamentoTable.getItems().setAll(pagamentos);
            pagamentoTable.setPlaceholder(new Label("Nenhum pagamento cadastrado"));
        }
        catch (Exception e) {
            mostrarMensagem(erroTabela, "Não foi possível carregar os pagamentos: " + e.getMessage(), Color.RED);
        }
    }

    private void configurarStatus(){
        statusField.getItems().setAll(StatusPagamento.values());

        statusField.setCellFactory(param -> new ListCell<StatusPagamento>() {
            @Override
            protected void updateItem(StatusPagamento status, boolean empty) {
                super.updateItem(status, empty);
                setText(empty || status == null ? null : status.getDescricao());
            }
        });

        statusField.setConverter(new StringConverter<StatusPagamento>() {
            @Override
            public String toString(StatusPagamento status) {
                return status != null ? status.getDescricao() : "";
            }

            @Override
            public StatusPagamento fromString(String string) {
                if (string == null || string.isEmpty()) return null;
                for (StatusPagamento status : StatusPagamento.values()) {
                    if (status.getDescricao().equalsIgnoreCase(string)) return status;
                }
                return null;
            }
        });

        //Atualiza a data de pagamento quando o status mudar
        statusField.valueProperty().addListener((observable,
                                                 oldValue,
                                                 newValue) -> {
            if (modoEdicao && newValue != null) atualizarDataPagamento(newValue);
        });
    }
    private void atualizarDataPagamento(StatusPagamento status) {
        if (status == StatusPagamento.PAGO || status == StatusPagamento.ATRASADO)
            novoPagamento.setDataPagamento(LocalDate.now());
        else
            novoPagamento.setDataPagamento(null);
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
                List<Pagamento> resultados = service.buscarPorTodosCampos(termo);
                pagamentoTable.getItems().setAll(resultados);

                if (resultados.isEmpty()) {
                    pagamentoTable.getItems().clear();
                    pagamentoTable.setPlaceholder(new Label("Nenhum pagamento encontrado com: " + termo));
                } else {
                    pagamentoTable.getItems().setAll(resultados);
                    pagamentoTable.setPlaceholder(new Label(""));
                }
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro na pesquisa: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void initialize(){
        configurarStatus();

        configurarTabela();
        configurarCampoPesquisa();

        statusField.setDisable(true);
        dataValidadeField.setValue(LocalDate.now().plusMonths(1)); //valor padrão
    }

    @FXML
    void cadastrarPagamento(ActionEvent event) {
        try {
            preencherCampos();

            if (!modoEdicao) {
                novoPagamento.setStatus(StatusPagamento.PENDENTE);
                novoPagamento.setDataPagamento(null);
                service.cadastrar(novoPagamento);
                mostrarMensagem(erroCadastro, "Pagamento cadastrado com sucesso!", Color.WHITE);
            } else {
                //obs: Data de pagamento já foi atualizada pelo listener do statusField
                novoPagamento.setStatus(statusField.getValue());
                service.atualizar(novoPagamento);
                mostrarMensagem(erroCadastro, "Pagamento editado com sucesso!", Color.WHITE);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;

                statusField.setDisable(true);
            }

            limparCampos();
            atualizarTabela();
        }
        catch (IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.YELLOW);
        }
        catch (Exception e){
            mostrarMensagem(erroCadastro, "Erro ao salvar pagamento: " + e.getMessage(), Color.YELLOW);
        }
    }

    @FXML
    void excluirPagamento(ActionEvent event) {
        Pagamento selecionado = pagamentoTable.getSelectionModel().getSelectedItem();

        if(selecionado == null)
            mostrarMensagem(erroTabela, "Escolha um pagamento para excluir!", Color.RED);
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirmar exclusão do pagamento");
            alert.setContentText("Tem certeza que deseja excluir o pagamento selecionado?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.excluir(selecionado);
                        mostrarMensagem(erroTabela, "Pagamento removido com sucesso!", Color.GREEN);
                        atualizarTabela();
                    }
                    catch (Exception e) {
                        mostrarMensagem(erroTabela, "Erro ao excluir pagamento: " + e.getMessage(), Color.RED);
                    }
                }
            });
        }
    }

    @FXML
    void editarPagamento(ActionEvent event) {
        Pagamento selecionado = pagamentoTable.getSelectionModel().getSelectedItem();

        if (selecionado == null)
            mostrarMensagem(erroTabela, "Selecione um pagamento para editar", Color.RED);
        else {
            try {
                modoEdicao = true;
                novoPagamento = selecionado;

                alunoNomeField.setText(selecionado.getAluno().getNome());
                mesRefField.setText(selecionado.getMesRef().getMonthValue() + "/" +
                        selecionado.getMesRef().getYear());
                statusField.setValue(selecionado.getStatus());
                dataValidadeField.setValue(selecionado.getDataValidade());

                statusField.setDisable(false);

                botaoCadastro.setText("Salvar");
            }
            catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar pagamento: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    private void imprimirPDF(){

    }

    @FXML
    private void voltarDashboard(){
        View.dashboard();
    }
}
