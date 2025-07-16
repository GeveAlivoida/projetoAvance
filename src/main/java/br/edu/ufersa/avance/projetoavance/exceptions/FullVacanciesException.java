package br.edu.ufersa.avance.projetoAvance.exceptions;

public class FullVacanciesException extends RuntimeException {
    public FullVacanciesException(){ super("As vagas já estão cheias!"); }
}
