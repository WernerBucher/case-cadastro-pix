package br.com.itau.pix.exception;

public class RegexException extends RuntimeException {

    public RegexException(String tipo) {
        super(String.format("Valor näo é valido para o tipo de chave < %s >  ", tipo));
    }

}
