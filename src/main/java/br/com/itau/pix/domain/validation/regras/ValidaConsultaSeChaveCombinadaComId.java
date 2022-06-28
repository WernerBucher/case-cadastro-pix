package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.dto.ConsultaDTO;
import br.com.itau.pix.domain.exception.ChaveCombinadaException;
import br.com.itau.pix.domain.validation.IValidadorConsulta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaConsultaSeChaveCombinadaComId implements IValidadorConsulta {

    @Override
    public Integer getPrioridade() {
        return 10;
    }

    @Override
    public void chain(ConsultaDTO dto) {
        if (dto.getId() != null && (dto.getNomeCorrentista() !=null || dto.getTipoChave() != null)){
            throw new ChaveCombinadaException();
        }
    }
}
