package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.exception.RegexException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.validation.CnpjValidator;
import br.com.itau.pix.domain.validation.IValidadorChaveAlterar;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import org.springframework.stereotype.Component;

@Component
public class ValidaTipoCnpj implements IValidadorChaveInserir, IValidadorChaveAlterar {

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(Chave chave) {
        if (TipoChave.CNPJ.equals(chave.getTipoChave())) {
            if (!CnpjValidator.validaCnpj(chave.getValorChave())) {
                throw new RegexException(chave.getTipoChave().name());
            }
        }
    }
}
