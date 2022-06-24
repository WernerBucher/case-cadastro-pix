package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.ChaveJaExisteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidaSeJaPossuiCadastro implements IValidadorNovaChave {

    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 10;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        Optional<Chave> chaveSalva = repository.findByValorChave(requisicao.getValorChave());
        if (chaveSalva.isPresent()) {
            throw new ChaveJaExisteException(requisicao.getValorChave());
        }
    }
}
