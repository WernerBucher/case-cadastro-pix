package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class InclusaoDTO extends BaseDTO implements IRequisicaoDTO{

    @ApiModelProperty(hidden = true)
    private UUID id;

    @NotNull
    private TipoChave tipoChave;

    @NotBlank
    private String valorChave;
}
