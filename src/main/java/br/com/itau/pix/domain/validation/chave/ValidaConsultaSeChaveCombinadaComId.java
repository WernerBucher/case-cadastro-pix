package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.ConsultaDTO;
import br.com.itau.pix.domain.validation.IValidadorConsulta;
import br.com.itau.pix.exception.ChaveCombinadaException;
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
