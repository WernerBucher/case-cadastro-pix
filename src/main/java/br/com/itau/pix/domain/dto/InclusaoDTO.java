package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InclusaoDTO extends BaseDTO {

    @NotNull
    private TipoChave tipoChave;

    @NotBlank
    private String valorChave;

}
