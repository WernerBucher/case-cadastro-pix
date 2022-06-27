package br.com.itau.pix.domain.dto.resposta;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class RespostaDTO {

    private UUID id;
    private TipoChave tipoChave;
    private String valorChave;
    private TipoConta tipoConta;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private TipoPessoa tipoPessoa;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "25/06/2022 15:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataHoraCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "25/06/2022 15:00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataHoraInativacao;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dataInativacao;


}
