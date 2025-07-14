package br.edu.ufersa.avance.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() { super("Login ou senha inválidos!"); }
    public AuthenticationException(String message) {super(message);}

}
