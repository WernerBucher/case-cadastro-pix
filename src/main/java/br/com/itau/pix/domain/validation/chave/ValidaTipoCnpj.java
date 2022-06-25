package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.IRequisicaoDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.CnpjValidator;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

@Component
public class ValidaTipoCnpj implements IValidadorNovaChave {

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(IRequisicaoDTO requisicao) {
        if (TipoChave.CNPJ.equals(requisicao.getTipoChave())) {
            if (!CnpjValidator.validaCnpj(requisicao.getValorChave())) {
                throw new RegexException(requisicao.getTipoChave().name());
            }
        }
    }
}
