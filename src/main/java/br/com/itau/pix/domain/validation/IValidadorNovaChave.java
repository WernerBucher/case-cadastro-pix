package br.com.itau.pix.domain.validation;

import br.com.itau.pix.domain.dto.ChaveDTO;

public interface IValidadorNovaChave {

    Integer getPrioridade();
    void chain(ChaveDTO requisicao);
}
