package br.edu.ufersa.avance.projetoavance.model.services;

import br.edu.ufersa.avance.projetoavance.model.dao.ProfessorDAO;
import br.edu.ufersa.avance.projetoavance.model.dao.ProfessorDAOImpl;
import br.edu.ufersa.avance.projetoavance.model.entities.Professor;
import br.edu.ufersa.avance.projetoavance.model.enums.StatusProfessor;

import java.util.List;

public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorDAO professorDAO = new ProfessorDAOImpl();

    @Override
    public void cadastrar(Professor professor) {
        Professor professorEncontrado = professorDAO.buscarPorId(professor.getId());
        if(professorEncontrado != null)
            throw new IllegalArgumentException("Professor já cadastrado!");
        else professorDAO.cadastrar(professor);
    }

    @Override
    public void atualizar(Professor professor) {
        Professor professorEncontrado = professorDAO.buscarPorId(professor.getId());
        if(professorEncontrado == null)
            throw new IllegalArgumentException("Professor inexistente!");
        else professorDAO.atualizar(professor);
    }

    @Override
    public void excluir(Professor professor) {
        Professor professorEncontrado = professorDAO.buscarPorId(professor.getId());
        if(professorEncontrado == null)
            throw new IllegalArgumentException("Professor inexistente!");
        else professorDAO.excluir(professor);
    }


    @Override
    public Professor buscarPorId(long id) { return professorDAO.buscarPorId(id); }

    @Override
    public List<Professor> buscarTodos() { return professorDAO.buscarTodos(); }

    @Override
    public List<Professor> buscarPorNome(String nome) {
        if(nome != null && !nome.isEmpty()) return professorDAO.buscarPorNome(nome);
        else return null;
    }

    @Override
    public Professor buscarPorCpf(String cpf) {
        if(cpf != null && !cpf.isEmpty()) return professorDAO.buscarPorCpf(cpf);
        else return null;
    }

    @Override
    public Professor buscarPorEmail(String email) {
        if(email != null && !email.isEmpty()) return professorDAO.buscarPorEmail(email);
        else return null;
    }

    @Override
    public Professor buscarPorTelefone(String telefone) {
        if(telefone != null && !telefone.isEmpty()) return professorDAO.buscarPorTelefone(telefone);
        else return null;
    }
    
    @Override
    public List<Professor> buscarPorStatus(StatusProfessor status) {
        if(status != null) return professorDAO.buscarPorStatus(status);
        else return null;
    }

    @Override
    public List<Professor> buscarPorTodosCampos(String termo) {
        return List.of();
    }

    @Override
    public void alterarStatus(Professor professor, StatusProfessor status) {
        professor.setStatus(status);
        atualizar(professor);
    }
}
