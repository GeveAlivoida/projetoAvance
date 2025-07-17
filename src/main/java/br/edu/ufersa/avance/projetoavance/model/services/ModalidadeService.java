package br.edu.ufersa.avance.projetoavance.model.services;

import br.edu.ufersa.avance.projetoavance.model.entities.Aluno;
import br.edu.ufersa.avance.projetoavance.model.enums.TipoModalidade;
import br.edu.ufersa.avance.projetoavance.model.entities.Modalidade;
import br.edu.ufersa.avance.projetoavance.model.entities.Professor;

import java.util.List;

public interface ModalidadeService extends GeralService<Modalidade> {
    public Modalidade buscarPorProfessor(Professor professor);
    public List<Modalidade> buscarPorNome(String nome);
    public List<Modalidade> buscarPorTipo(TipoModalidade tipo);
    public List<Modalidade> buscarAbertas();
    public void matricularAluno(Modalidade modalidade, Aluno aluno);
}
