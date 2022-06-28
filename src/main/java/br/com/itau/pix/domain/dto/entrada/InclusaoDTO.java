package br.com.itau.pix.domain.dto.entrada;

import br.com.itau.pix.domain.enums.TipoChave;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InclusaoDTO extends BaseDTO {

    @NotNull(message = "{tipoChave.notnull}")
    private TipoChave tipoChave;

    @NotBlank(message = "{valorChave.notblank}")
    private String valorChave;

}
