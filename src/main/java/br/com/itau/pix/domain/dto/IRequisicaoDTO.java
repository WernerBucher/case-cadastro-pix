package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;

import java.util.UUID;

public interface IRequisicaoDTO {

    UUID getId();
    TipoChave getTipoChave();
    String getValorChave();
    TipoConta getTipoConta();
    Integer getNumeroAgencia();
    Integer getNumeroConta();
    TipoPessoa getTipoPessoa();
    String getNomeCorrentista();
    String getSobrenomeCorrentista();

}
