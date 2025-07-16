package br.edu.ufersa.avance.projetoAvance.model.dao;

import br.edu.ufersa.avance.projetoAvance.model.enums.TipoModalidade;
import br.edu.ufersa.avance.projetoAvance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoAvance.model.entities.Professor;

import java.util.List;

public interface ModalidadeDAO extends GeralDAO<Modalidade> {
    public Modalidade buscarPorProfessor(Professor professor);
    public List<Modalidade> buscarPorNome(String nome);
    public List<Modalidade> buscarPorTipo(TipoModalidade tipo);
    public List<Modalidade> buscarAbertas();
}
