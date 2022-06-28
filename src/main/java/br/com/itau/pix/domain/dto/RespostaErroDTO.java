package br.com.itau.pix.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class RespostaErroDTO implements Serializable {

    private final List<ErroDTO> errors;
}
