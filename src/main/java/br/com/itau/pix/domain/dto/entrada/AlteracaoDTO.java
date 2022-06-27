package br.com.itau.pix.domain.dto.entrada;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AlteracaoDTO extends BaseDTO {

    @NotNull
    private UUID id;
}
