package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.ChaveDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.CpfValidator;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

@Component
public class ValidaTipoCpf implements IValidadorNovaChave {

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(ChaveDTO dto) {
        if (TipoChave.CPF.equals(dto.getTipoChave())) {
            if (!CpfValidator.validaCpf(dto.getValorChave())) {
                throw new RegexException(dto.getTipoChave().name());
            }
        }
    }
}
