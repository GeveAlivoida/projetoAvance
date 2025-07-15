package br.edu.ufersa.avance.model.services;

import br.edu.ufersa.avance.model.entities.Aluno;
import br.edu.ufersa.avance.model.enums.TipoModalidade;
import br.edu.ufersa.avance.model.entities.Modalidade;
import br.edu.ufersa.avance.model.entities.Professor;

import java.util.List;

public interface ModalidadeService extends GeralService<Modalidade> {
    public Modalidade buscarPorProfessor(Professor professor);
    public List<Modalidade> buscarPorNome(String nome);
    public List<Modalidade> buscarPorTipo(TipoModalidade tipo);
    public List<Modalidade> buscarAbertas();
    public void matricularAluno(Modalidade modalidade, Aluno aluno);
}
