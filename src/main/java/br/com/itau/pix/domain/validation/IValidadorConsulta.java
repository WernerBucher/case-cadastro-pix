package br.com.itau.pix.domain.validation;

import br.com.itau.pix.domain.dto.ConsultaDTO;

public interface IValidadorConsulta {

    Integer getPrioridade();
    void chain(ConsultaDTO dto);
}
