package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.AulaDAO;
import br.edu.ufersa.avance.model.dao.AulaDAOImpl;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Modalidade;

import java.time.LocalDate;
import java.util.List;

public class AulaServiceImpl implements AulaService{
    private final AulaDAO aulaDAO = new AulaDAOImpl();

    @Override
    public void cadastrar(Aula aula) {
        Aula aulaEncontrada = aulaDAO.buscarPorId(aula.getId());
        if(aulaEncontrada != null)
            throw new IllegalArgumentException("Aula já cadastrada!");
        else aulaDAO.cadastrar(aula);
    }

    @Override
    public void atualizar(Aula aula) {
        Aula aulaEncontrada = aulaDAO.buscarPorId(aula.getId());
        if(aulaEncontrada == null)
            throw new IllegalArgumentException("Aula inexistente!");
        else aulaDAO.cadastrar(aula);
    }

    @Override
    public void excluir(Aula aula) {
        Aula aulaEncontrada = aulaDAO.buscarPorId(aula.getId());
        if(aulaEncontrada == null)
            throw new IllegalArgumentException("Aula inexistente!");
        else aulaDAO.excluir(aula);
    }

    @Override
    public List<Aula> buscarTodos() { return aulaDAO.buscarTodos(); }

    @Override
    public Aula buscarPorId(Long id) {
        if(id != null) return aulaDAO.buscarPorId(id);
        else return null;
    }

    @Override
    public List<Aula> buscarPorAluno(Aluno aluno) {
        if(aluno != null) return aulaDAO.buscarPorAluno(aluno);
        else return null;
    }

    @Override
    public List<Aula> buscarPorModalidade(Modalidade modalidade) {
        if(modalidade != null) return aulaDAO.buscarPorModalidade(modalidade);
        else return null;
    }

    @Override
    public List<Aula> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        if(dataInicio != null && dataFim != null && !dataInicio.isAfter(dataFim))
            return aulaDAO.buscarPorPeriodo(dataInicio, dataFim);
        else return null;
    }
}
