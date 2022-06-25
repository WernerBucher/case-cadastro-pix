package br.com.itau.pix.domain.dto.resposta;

import br.com.itau.pix.domain.model.Chave;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaBuilder {

    private final RespostaDTO resposta;
    private final Date dataHoraCadastro;
    private final Date dataHoraInativacao;

    public RespostaBuilder(Chave chave) {
        this.resposta = new RespostaDTO();
        this.resposta.setId(chave.getId());
        this.resposta.setTipoChave(chave.getTipoChave());
        this.resposta.setValorChave(chave.getValorChave());
        this.resposta.setTipoConta(chave.getTipoConta());
        this.resposta.setNumeroAgencia(chave.getNumeroAgencia());
        this.resposta.setNumeroConta(chave.getNumeroConta());
        this.resposta.setNomeCorrentista(chave.getNomeCorrentista());
        this.resposta.setSobrenomeCorrentista(chave.getSobrenomeCorrentista());
        this.resposta.setTipoPessoa(chave.getTipoPessoa());
        this.dataHoraCadastro = chave.getDataHoraCadastro();
        this.dataHoraInativacao = chave.getDataHoraInativacao();
    }

    public static RespostaBuilder builder(Chave chave) {
        return new RespostaBuilder(chave);
    }

    public RespostaDTO comDataHora() {
        this.resposta.setDataHoraCadastro(dataHoraCadastro);
        this.resposta.setDataHoraInativacao(dataHoraInativacao);
        return resposta;
    }

    public RespostaDTO comData() {
        this.resposta.setDataCadastro(dataHoraCadastro);
        this.resposta.setDataInativacao(dataHoraInativacao);
        return resposta;
    }

}
