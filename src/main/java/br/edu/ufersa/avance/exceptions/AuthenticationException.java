package br.edu.ufersa.avance.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) { super(message); }
    public AuthenticationException() { super("Login ou senha inválidos!"); }
}
