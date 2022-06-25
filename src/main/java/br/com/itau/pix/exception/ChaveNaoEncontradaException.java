package br.com.itau.pix.exception;

import java.util.UUID;

public class ChaveNaoEncontradaException extends RuntimeException {

    public ChaveNaoEncontradaException(UUID id) {
        super(String.format("Não encontrado chave com o ID < %s >!", id));
    }
}
