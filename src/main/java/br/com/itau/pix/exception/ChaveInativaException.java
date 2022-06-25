package br.com.itau.pix.exception;

public class ChaveInativaException extends RuntimeException {

    public ChaveInativaException(String valorChave) {
        super(String.format("A chave < %s > não pose ser alterada, pois a mesma está inativa!", valorChave));
    }
}
