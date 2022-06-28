package br.com.itau.pix.domain.dto.entrada;

import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public abstract class BaseDTO {

    @NotNull(message = "{tipoConta.notnull}")
    private TipoConta tipoConta;

    @NotNull(message = "{numeroAgencia.notnull}")
    @Min(value = 1, message = "{numeroAgencia.min}" )
    @Max(value = 9999, message = "{numeroAgencia.max}" )
    private Integer numeroAgencia;

    @NotNull(message = "{numeroConta.notnull}")
    @Min(value = 1, message = "{numeroConta.min}" )
    @Max(value = 99999999, message = "{numeroConta.max}" )
    private Integer numeroConta;

    @NotBlank(message = "{nomeCorrentista.notblank}")
    @Size(min = 1, max = 30, message = "{nomeCorrentista.size}")
    private String nomeCorrentista;

    @Size(max = 45, message = "{sobrenomeCorrentista.size}")
    private String sobrenomeCorrentista;

    @NotNull(message = "{tipoPessoa.notnull}")
    private TipoPessoa tipoPessoa;

}
