package br.com.itau.pix.domain.validation;

import br.com.itau.pix.domain.dto.RequestDTO;

public interface IValidadorNovaChave {

    Integer getPrioridade();
    void chain(RequestDTO requisicao);
}
