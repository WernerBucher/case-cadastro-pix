package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private String nomeCorrentista;

    private String sobrenomeCorrentista;

    @NotNull
    private TipoPessoa tipoPessoa;

}
