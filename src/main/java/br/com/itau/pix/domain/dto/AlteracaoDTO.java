package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AlteracaoDTO extends BaseDTO implements IRequisicaoDTO {

    @NotNull
    private UUID id;

    @ApiModelProperty(hidden = true)
    private TipoChave tipoChave;

    @ApiModelProperty(hidden = true)
    private String valorChave;

}
