package br.edu.ufersa.avance.projetoAvance.model.services;

import br.edu.ufersa.avance.projetoAvance.model.dao.ResponsavelDAO;
import br.edu.ufersa.avance.projetoAvance.model.dao.ResponsavelDAOImpl;
import br.edu.ufersa.avance.projetoAvance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoAvance.model.entities.Responsavel;

import java.util.List;

public class ResponsavelServiceImpl implements ResponsavelService {
    private final ResponsavelDAO responsavelDAO = new ResponsavelDAOImpl();

    @Override
    public void cadastrar(Responsavel responsavel) {
        Responsavel responsavelEncontrado = responsavelDAO.buscarPorId(responsavel.getId());
        if(responsavelEncontrado != null)
            throw new IllegalArgumentException("Responsavel já cadastrado!");
        else responsavelDAO.cadastrar(responsavel);
    }

    @Override
    public void atualizar(Responsavel responsavel) {
        Responsavel responsavelEncontrado = responsavelDAO.buscarPorId(responsavel.getId());
        if(responsavelEncontrado == null)
            throw new IllegalArgumentException("Responsavel inexistente!");
        else responsavelDAO.atualizar(responsavel);
    }

    @Override
    public void excluir(Responsavel responsavel) {
        Responsavel responsavelEncontrado = responsavelDAO.buscarPorId(responsavel.getId());
        if(responsavelEncontrado == null)
            throw new IllegalArgumentException("Responsavel inexistente!");
        else responsavelDAO.excluir(responsavel);
    }

    @Override
    public List<Responsavel> buscarPorTodosCampos(String termo) {
        return List.of();
    }


    @Override
    public Responsavel buscarPorId(long id) { return responsavelDAO.buscarPorId(id); }

    @Override
    public List<Responsavel> buscarTodos() { return responsavelDAO.buscarTodos(); }

    @Override
    public List<Responsavel> buscarPorNome(String nome) {
        if(nome != null && !nome.isEmpty()) return responsavelDAO.buscarPorNome(nome);
        else return null;
    }

    @Override
    public Responsavel buscarPorCpf(String cpf) {
        if(cpf != null && !cpf.isEmpty()) return responsavelDAO.buscarPorCpf(cpf);
        else return null;
    }

    @Override
    public Responsavel buscarPorEmail(String email) {
        if(email != null && !email.isEmpty()) return responsavelDAO.buscarPorEmail(email);
        else return null;
    }

    @Override
    public Responsavel buscarPorTelefone(String telefone) {
        if(telefone != null && !telefone.isEmpty()) return responsavelDAO.buscarPorTelefone(telefone);
        else return null;
    }
    
    @Override
    public Responsavel buscarPorAluno(Aluno aluno) {
        if(aluno != null) return responsavelDAO.buscarPorAluno(aluno);
        else return null;
    }

    @Override
    public void cadastrarAluno(Responsavel responsavel, Aluno aluno) {
        if(responsavel != null && aluno != null) {
            responsavel.adicionarDependente(aluno);
            responsavelDAO.atualizar(responsavel);
        }
        else throw new IllegalArgumentException("O responsavel ou aluno não podem estar vazios!");
    }
}
