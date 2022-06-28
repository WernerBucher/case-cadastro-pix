package br.com.itau.pix.domain.dto.resposta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RespostaComDataHoraDTO extends RespostaDTO{

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "25/06/2022 15:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataHoraCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "25/06/2022 15:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataHoraInativacao;

}
