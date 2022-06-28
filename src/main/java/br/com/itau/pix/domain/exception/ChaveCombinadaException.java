package br.com.itau.pix.domain.exception;

public class ChaveCombinadaException extends RuntimeException {
    public ChaveCombinadaException() {
        super("Não é permitido combinar o ID com outros filtros");
    }
}
