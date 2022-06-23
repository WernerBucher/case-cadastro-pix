package br.com.itau.pix.exception;

public class ChaveJaExisteException extends RuntimeException {

    public ChaveJaExisteException(String valorChave) {
        super(String.format("A chave < %s > já foi cadastrada!", valorChave));
    }
}
