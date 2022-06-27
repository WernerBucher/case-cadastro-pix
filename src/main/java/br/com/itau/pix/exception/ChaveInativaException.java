package br.com.itau.pix.exception;

import java.util.UUID;

public class ChaveInativaException extends RuntimeException {

    public ChaveInativaException(UUID uuid) {
        super(String.format("A chave com ID < %s > já foi Desativada!", uuid));
    }
}
