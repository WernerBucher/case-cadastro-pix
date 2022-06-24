package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private UUID id;
    private TipoChave tipoChave;
    private String valorChave;
    private String tipoConta;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private TipoPessoa tipoPessoa;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date dataHoraCadastro;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date dataHoraInativacao;

    public static ResponseDTO getResponseId(Chave chave) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.id = chave.getId();
        return responseDTO;
    }

    public static ResponseDTO getResponseAll(Chave chave) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.id = chave.getId();
        responseDTO.tipoChave = chave.getTipoChave();
        responseDTO.valorChave = chave.getValorChave();
        responseDTO.tipoConta = chave.getTipoConta();
        responseDTO.numeroAgencia = chave.getNumeroAgencia();
        responseDTO.numeroConta = chave.getNumeroConta();
        responseDTO.nomeCorrentista = chave.getNomeCorrentista();
        responseDTO.sobrenomeCorrentista = chave.getSobrenomeCorrentista();
        responseDTO.tipoPessoa = chave.getTipoPessoa();
        responseDTO.dataHoraCadastro = chave.getDataHoraCadastro();
        responseDTO.dataHoraInativacao = chave.getDataHoraInativacao();
        return responseDTO;
    }

}
