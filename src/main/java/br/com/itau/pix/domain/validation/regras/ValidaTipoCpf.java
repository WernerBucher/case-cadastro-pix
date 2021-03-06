package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.exception.RegexException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.validation.CpfValidator;
import br.com.itau.pix.domain.validation.IValidadorChaveAlterar;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import org.springframework.stereotype.Component;

@Component
public class ValidaTipoCpf implements IValidadorChaveInserir, IValidadorChaveAlterar {

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(Chave chave) {
        if (TipoChave.CPF.equals(chave.getTipoChave())) {
            if (!CpfValidator.validaCpf(chave.getValorChave())) {
                throw new RegexException(chave.getTipoChave().name());
            }
        }
    }
}
