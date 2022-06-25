package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public abstract class BaseDTO {

    @NotNull
    private TipoConta tipoConta;

    @NotNull
    @Min(1)
    @Max(9999)
    private Integer numeroAgencia;

    @NotNull
    @Min(1)
    @Max(99999999)
    private Integer numeroConta;

    @NotBlank
    @Size(max = 30)
    private String nomeCorrentista;

    @Size(max = 45)
    private String sobrenomeCorrentista;

    @NotNull
    private TipoPessoa tipoPessoa;

}
