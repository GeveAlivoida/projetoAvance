package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.AlunoDAO;
import br.edu.ufersa.avance.model.dao.AlunoDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Responsavel;

import java.util.List;

public class AlunoServiceImpl implements AlunoService {
    private final AlunoDAO alunoDAO = new AlunoDAOImpl();

    @Override
    public void cadastrar(Aluno aluno) {
        Aluno alunoEncontrado = alunoDAO.buscarPorId(aluno.getId());
        if(alunoEncontrado != null)
            throw new IllegalArgumentException("Aluno já cadastrado!");
        else alunoDAO.cadastrar(aluno);
    }

    @Override
    public void atualizar(Aluno aluno) {
        Aluno alunoEncontrado = alunoDAO.buscarPorId(aluno.getId());
        if(alunoEncontrado == null)
            throw new IllegalArgumentException("Aluno inexistente!");
        else alunoDAO.atualizar(aluno);
    }

    @Override
    public void excluir(Aluno aluno) {
        Aluno alunoEncontrado = alunoDAO.buscarPorId(aluno.getId());
        if(alunoEncontrado == null)
            throw new IllegalArgumentException("Aluno inexistente!");
        else alunoDAO.excluir(aluno);
    }


    @Override
    public Aluno buscarPorId(long id) { return alunoDAO.buscarPorId(id); }

    @Override
    public List<Aluno> buscarTodos() { return alunoDAO.buscarTodos(); }

    @Override
    public List<Aluno> buscarPorNome(String nome) {
        if(nome != null && !nome.isEmpty()) return alunoDAO.buscarPorNome(nome);
        else return null;
    }

    @Override
    public Aluno buscarPorCpf(String cpf) {
        if(cpf != null && !cpf.isEmpty()) return alunoDAO.buscarPorCpf(cpf);
        else return null;
    }

    @Override
    public Aluno buscarPorEmail(String email) {
        if(email != null && !email.isEmpty()) return alunoDAO.buscarPorEmail(email);
        else return null;
    }

    @Override
    public Aluno buscarPorTelefone(String telefone) {
        if(telefone != null && !telefone.isEmpty()) return alunoDAO.buscarPorTelefone(telefone);
        else return null;
    }

    @Override
    public List<Aluno> buscarPorAula(Aula aula) {
        if(aula != null) return alunoDAO.buscarPorAula(aula);
        else return null;
    }

    @Override
    public List<Aluno> buscarPorResponsavel(Responsavel responsavel) {
        if(responsavel != null) return alunoDAO.buscarPorResponsavel(responsavel);
        else return null;
    }
}
