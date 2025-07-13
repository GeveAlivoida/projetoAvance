package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.ResponsavelDAO;
import br.edu.ufersa.avance.model.dao.ResponsavelDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Responsavel;

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
    public Responsavel buscarPorId(Long id) {
        if(id != null) return responsavelDAO.buscarPorId(id);
        else return null;
    }

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
}
