package br.com.itau.pix.domain.validation;

import br.com.itau.pix.domain.model.Chave;

public interface IValidadorChave {

    Integer getPrioridade();
    void chain(Chave requisicao);
}
