package br.com.itau.pix.domain.dto.resposta;

import br.com.itau.pix.domain.model.Chave;

import java.util.Date;

public class RespostaBuilder {

    private final Chave chave;
    private final Date dataHoraCadastro;
    private final Date dataHoraInativacao;


    public RespostaBuilder(Chave chave) {
        this.chave = chave;
        this.dataHoraCadastro = chave.getDataHoraCadastro();
        this.dataHoraInativacao = chave.getDataHoraInativacao();
    }

    public static RespostaBuilder builder(Chave chave) {
        return new RespostaBuilder(chave);
    }

    public RespostaComDataHoraDTO completaComDataHora() {
        RespostaComDataHoraDTO comDataHoraDTO = new RespostaComDataHoraDTO();
        comDataHoraDTO.setId(chave.getId());
        comDataHoraDTO.setTipoChave(chave.getTipoChave());
        comDataHoraDTO.setValorChave(chave.getValorChave());
        comDataHoraDTO.setTipoConta(chave.getTipoConta());
        comDataHoraDTO.setNumeroAgencia(chave.getNumeroAgencia());
        comDataHoraDTO.setNumeroConta(chave.getNumeroConta());
        comDataHoraDTO.setNomeCorrentista(chave.getNomeCorrentista());
        comDataHoraDTO.setSobrenomeCorrentista(chave.getSobrenomeCorrentista());
        comDataHoraDTO.setTipoPessoa(chave.getTipoPessoa());
        comDataHoraDTO.setDataHoraCadastro(dataHoraCadastro);
        comDataHoraDTO.setDataHoraInativacao(dataHoraInativacao);
        return comDataHoraDTO;
    }

    public RespostaComDataDTO completaComData() {
        RespostaComDataDTO comDataDTO = new RespostaComDataDTO();
        comDataDTO.setId(chave.getId());
        comDataDTO.setTipoChave(chave.getTipoChave());
        comDataDTO.setValorChave(chave.getValorChave());
        comDataDTO.setTipoConta(chave.getTipoConta());
        comDataDTO.setNumeroAgencia(chave.getNumeroAgencia());
        comDataDTO.setNumeroConta(chave.getNumeroConta());
        comDataDTO.setNomeCorrentista(chave.getNomeCorrentista());
        comDataDTO.setSobrenomeCorrentista(chave.getSobrenomeCorrentista());
        comDataDTO.setTipoPessoa(chave.getTipoPessoa());
        comDataDTO.setDataCadastro(dataHoraCadastro);
        comDataDTO.setDataInativacao(dataHoraInativacao);
        return comDataDTO;
    }

    public RespostaIdDTO somenteId() {
        return new RespostaIdDTO(chave.getId());
    }

}
