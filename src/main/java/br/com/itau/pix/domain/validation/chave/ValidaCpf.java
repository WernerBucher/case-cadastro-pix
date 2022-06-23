package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.CpfValidator;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

@Component
public class ValidaCpf implements IValidadorNovaChave {

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        if (TipoChave.CPF.equals(TipoChave.valueOf(requisicao.getTipoChave()))) {

            boolean cpfValido = CpfValidator.validaCpf(requisicao.getValorChave());

            if (!cpfValido) {
                throw new RegexException(requisicao.getTipoChave());
            }
        }
    }
}
