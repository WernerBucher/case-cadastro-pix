package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.validation.CpfValidator;
import br.com.itau.pix.domain.validation.IValidadorAlteracaoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

@Component
public class ValidaTipoCpf implements IValidadorNovaChave, IValidadorAlteracaoChave {

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
