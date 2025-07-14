package br.edu.ufersa.avance.controller;

import br.edu.ufersa.avance.exceptions.FullVacanciesException;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.services.*;
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
        label.setText(mensagem);
        label.setTextFill(cor);
        label.setVisible(true);
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

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
        } catch (Exception e) {
            mostrarMensagem(erroTabela, "Não foi possível carregar os alunos: " + e.getMessage(), Color.RED);
        }
    }

    private void configurarModalidades(){
        final ModalidadeService modalidadeService = new ModalidadeServiceImpl();

        //Configurando a forma como aparecem as modalidades no modalidadeField
        modalidadeField.getItems().addAll(modalidadeService.buscarTodos());

        modalidadeField.setCellFactory(param -> new ListCell<Modalidade>() {
            @Override
            protected void updateItem(Modalidade modalidade, boolean empty) {
                super.updateItem(modalidade, empty);
                if (empty || modalidade == null) {
                    setText(null);
                } else {
                    setText(modalidade.getNome() + " (" + modalidade.getTipo() + ")");
                }
            }
        });

        modalidadeField.setConverter(new StringConverter<Modalidade>() {
            @Override
            public String toString(Modalidade modalidade) {
                if (modalidade == null) {
                    return null;
                }
                return modalidade.getNome() + " (" + modalidade.getTipo() + ")";
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
        final ResponsavelService responsavelService = new ResponsavelServiceImpl();

        try {
            novoAluno.setNome(nomeField.getText());
            novoAluno.setCpf(cpfAlunoField.getText());
            if (!cpfRespField.getText().isEmpty())
                novoAluno.setResponsavel(responsavelService.buscarPorCpf(cpfRespField.getText()));
            else
                novoAluno.setResponsavel(null);
            novoAluno.setNascimento(nascimentoField.getValue());
            novoAluno.setTelefone(telefoneField.getText());
            novoAluno.setEmail(emailField.getText());

            Modalidade selecionada = modalidadeField.getValue();
            if(selecionada != null) {
                try {
                    novoAluno.adicionarModalidade(selecionada);
                } catch (FullVacanciesException e) {
                    mostrarMensagem(erroCadastro, e.getMessage(), Color.WHITE);
                }
            }

            if(!modoEdicao) {
                service.cadastrar(novoAluno);
                mostrarMensagem(erroCadastro, "Aluno cadastrado com sucesso!", Color.GREEN);
            }
            else {
                service.atualizar(novoAluno);
                botaoCadastro.setText("Cadastrar");
                modoEdicao = false;
            }

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException e) {
            mostrarMensagem(erroCadastro, e.getMessage(), Color.WHITE);
        } catch (Exception e){
            mostrarMensagem(erroCadastro, "Erro ao salvar aluno: " + e.getMessage(), Color.WHITE);
        }
    }

    @FXML
    void excluirAluno(ActionEvent event) {
        Aluno selecionado = alunoTable.getSelectionModel().getSelectedItem();

        if(selecionado == null) {
            mostrarMensagem(erroTabela, "Escolha um aluno para remover!", Color.RED);
        }
        else {
            try {
                service.excluir(selecionado);
                mostrarMensagem(erroTabela, "Aluno removido com sucesso!", Color.GREEN);
                atualizarTabela();
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao excluir professor:" + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    void editarAluno(ActionEvent event) {
        Aluno selecionado = alunoTable.getSelectionModel().getSelectedItem();
        if(selecionado == null) {
            mostrarMensagem(erroTabela, "Escolha um aluno para editar!", Color.RED);
        }
        else {
            modoEdicao = true;

            try {
                nomeField.setText(selecionado.getNome());
                cpfAlunoField.setText(selecionado.getCpf());
                if (selecionado.getResponsavel() != null) {
                    cpfRespField.setText(selecionado.getResponsavel().getCpf());
                }
                nascimentoField.setValue(selecionado.getNascimento());
                telefoneField.setText(selecionado.getTelefone());
                emailField.setText(selecionado.getEmail());
                if (!selecionado.getModalidades().isEmpty()) {
                    modalidadeField.setValue(selecionado.getModalidades().getLast());
                } else {
                    modalidadeField.setValue(null);
                }

                novoAluno = selecionado;
                botaoCadastro.setText("Salvar");
            } catch (Exception e) {
                mostrarMensagem(erroTabela, "Erro ao carregar aluno: " + e.getMessage(), Color.RED);
            }
        }
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
}
