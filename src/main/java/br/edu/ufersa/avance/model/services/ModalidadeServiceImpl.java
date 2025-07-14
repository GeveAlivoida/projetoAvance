package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.ModalidadeDAO;
import br.edu.ufersa.avance.model.dao.ModalidadeDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.TipoModalidade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModalidadeServiceImpl implements ModalidadeService {
    private final ModalidadeDAO modalidadeDAO = new ModalidadeDAOImpl();

    @Override
    public void cadastrar(Modalidade modalidade) {
        Modalidade modalidadeEncontrada = modalidadeDAO.buscarPorId(modalidade.getId());
        if(modalidadeEncontrada != null)
            throw new IllegalArgumentException("Modalidade já cadastrada!");
        else modalidadeDAO.cadastrar(modalidade);
    }

    @Override
    public void atualizar(Modalidade modalidade) {
        Modalidade modalidadeEncontrada = modalidadeDAO.buscarPorId(modalidade.getId());
        if(modalidadeEncontrada == null)
            throw new IllegalArgumentException("Modalidade inexistente!");
        else modalidadeDAO.cadastrar(modalidade);
    }

    @Override
    public void excluir(Modalidade modalidade) {
        Modalidade modalidadeEncontrada = modalidadeDAO.buscarPorId(modalidade.getId());
        if(modalidadeEncontrada == null)
            throw new IllegalArgumentException("Modalidade inexistente!");
        else modalidadeDAO.excluir(modalidade);
    }

    @Override
    public List<Modalidade> buscarTodos() { return modalidadeDAO.buscarTodos(); }

    @Override
    public Modalidade buscarPorId(long id) { return modalidadeDAO.buscarPorId(id); }

    @Override
    public Modalidade buscarPorProfessor(Professor professor) {
        if(professor != null) return modalidadeDAO.buscarPorProfessor(professor);
        else return null;
    }

    @Override
    public List<Modalidade> buscarPorNome(String nome) {
        if(nome != null && !nome.isEmpty()) return modalidadeDAO.buscarPorNome(nome);
        else return null;
    }

    @Override
    public List<Modalidade> buscarPorTipo(TipoModalidade tipo) {
        if(tipo != null) return modalidadeDAO.buscarPorTipo(tipo);
        else return null;
    }

    @Override
    public List<Modalidade> buscarAbertas() { return modalidadeDAO.buscarAbertas(); }

    @Override
    public List<Modalidade> buscarPorTodosCampos(String termo) {
        final ProfessorService professorService = new ProfessorServiceImpl();

        if (termo == null || termo.trim().isEmpty()) {
            return buscarTodos();
        }

        List<Modalidade> resultados = new ArrayList<>();

        resultados.addAll(buscarPorNome(termo));

        try {
            TipoModalidade tipo = TipoModalidade.valueOf(termo.toUpperCase());
            resultados.addAll(buscarPorTipo(tipo));
        } catch (IllegalArgumentException e) {
            // Não é um tipo válido, continua a busca
        }

        List<Professor> professores = professorService.buscarPorNome(termo);
        for (Professor professor : professores) {
            Modalidade modalidade = buscarPorProfessor(professor);
            if (modalidade != null) {
                resultados.add(modalidade);
            }
        }

        //Remove duplicados
        return resultados.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void matricularAluno(Modalidade modalidade, Aluno aluno) {
        if(modalidade.temVaga()){
            modalidade.adicionarAluno(aluno);
            modalidadeDAO.atualizar(modalidade);
        }
        else throw new IllegalStateException("Todas as vagas já estão cheias!");
    }
}
