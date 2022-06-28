package br.com.itau.pix.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroDTO implements Serializable {

    private final String mensagem;
    private final String campo;
    private final Object valor;
}
