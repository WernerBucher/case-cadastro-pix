package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.validation.ValueOfEnum;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class RequestDTO {

    @NotBlank
    @ValueOfEnum(enumClass = TipoChave.class)
    private String tipoChave;

    @NotBlank
    private String valorChave;

    @NotBlank
    @ValueOfEnum(enumClass = TipoConta.class)
    private String tipoConta;

    @NotNull
    @Min(1) @Max(9999)
    private Integer numeroAgencia;

    @NotNull
    @Min(1) @Max(99999999)
    private Integer numeroConta;

    @NotBlank
    private String nomeCorrentista;

    private String sobrenomeCorrentista;

    @NotBlank
    @ValueOfEnum(enumClass = TipoPessoa.class)
    private String tipoPessoa;

}
