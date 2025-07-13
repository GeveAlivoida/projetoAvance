package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.dao.*;
import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.entities.Aula;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;
import br.edu.ufersa.avance.model.enums.StatusAula;
import br.edu.ufersa.avance.model.enums.StatusProfessor;

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
        if(dataInicio != null && dataFim != null) {
            if(!dataInicio.isAfter(dataFim)) return aulaDAO.buscarPorPeriodo(dataInicio, dataFim);
            else throw new IllegalArgumentException("A data de início deve ser antes da data de fim!");
        }
        else return null;
    }

    @Override
    public void agendarAula(Aula aula) {
        aula.setStatus(StatusAula.AGENDADA);
        cadastrar(aula);
    }

    @Override
    public void cancelarAula(Aula aula) {
        aula.setStatus(StatusAula.CANCELADA);
        atualizar(aula);
    }

    @Override
    public void concluirAula(Aula aula) {
        aula.setStatus(StatusAula.CONCLUIDA);
        atualizar(aula);
    }

    @Override
    public void adiarAula(Aula aula, LocalDate novaData) {
        aula.setStatus(StatusAula.ADIADA);
        atualizar(aula);

        aula.setData(novaData);
        agendarAula(aula);
    }
}
