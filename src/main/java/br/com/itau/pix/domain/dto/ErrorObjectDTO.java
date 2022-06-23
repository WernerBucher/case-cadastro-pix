package br.com.itau.pix.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorObjectDTO implements Serializable {

    private final String mensagem;
    private final String campo;
    private final Object valor;
}
