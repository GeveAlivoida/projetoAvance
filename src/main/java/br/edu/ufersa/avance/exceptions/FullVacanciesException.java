package br.edu.ufersa.avance.exceptions;

public class FullVacanciesException extends RuntimeException {
    public FullVacanciesException(){ super("As vagas já estão cheias!"); }
}
