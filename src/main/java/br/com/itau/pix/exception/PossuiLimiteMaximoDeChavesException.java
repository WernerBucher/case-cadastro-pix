package br.com.itau.pix.exception;

public class PossuiLimiteMaximoDeChavesException extends RuntimeException {

    public PossuiLimiteMaximoDeChavesException(Integer agencia, Integer conta) {
        super(String.format("Atingiu o limite maximo de chaves para a Agencia < %d > e Conta < %d >!", agencia, conta));
    }
}
