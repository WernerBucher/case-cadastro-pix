package br.com.itau.pix.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ErrorResponseDTO implements Serializable {

    private final List<ErrorObjectDTO> errors;
}
