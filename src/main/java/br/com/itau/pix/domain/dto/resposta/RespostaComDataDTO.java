package br.com.itau.pix.domain.dto.resposta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RespostaComDataDTO extends RespostaDTO{

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "25/06/2022")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "25/06/2022")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataInativacao;

}
