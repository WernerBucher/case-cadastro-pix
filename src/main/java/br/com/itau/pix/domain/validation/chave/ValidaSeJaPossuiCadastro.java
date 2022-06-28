package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import br.com.itau.pix.exception.ChaveJaExisteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidaSeJaPossuiCadastro implements IValidadorChaveInserir {

    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 10;
    }

    @Override
    public void chain(Chave chave) {
        Optional<Chave> chaveSalva = repository.findByValorChave(chave.getValorChave());
        if (chaveSalva.isPresent()) {
            throw new ChaveJaExisteException(chave.getValorChave());
        }
    }

}
