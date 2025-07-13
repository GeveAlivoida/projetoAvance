package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.ModalidadeDAO;
import br.edu.ufersa.avance.model.dao.ModalidadeDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.TipoModalidade;

import java.util.List;

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
    public List<Modalidade> buscarPorTipo(TipoModalidade tipo) {
        if(tipo != null) return modalidadeDAO.buscarPorTipo(tipo);
        else return null;
    }

    @Override
    public List<Modalidade> buscarAbertas() { return modalidadeDAO.buscarAbertas(); }

    @Override
    public void matricularAluno(Modalidade modalidade, Aluno aluno) {
        if(modalidade.temVaga()){
            modalidade.adicionarAluno(aluno);
            modalidadeDAO.atualizar(modalidade);
        }
        else throw new IllegalStateException("Todas as vagas já estão cheias!");
    }
}
