package br.com.itau.pix.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaDTO{

    private final UUID id;
    private final String nomeCorrentista;
    private final String tipoChave;

}
