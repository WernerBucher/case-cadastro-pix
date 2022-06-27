package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorAlteracaoChave;
import br.com.itau.pix.domain.validation.IValidadorDeletarChave;
import br.com.itau.pix.exception.ChaveInativaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidaSeChaveInativa implements IValidadorAlteracaoChave, IValidadorDeletarChave {

    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 10;
    }

    @Override
    public void chain(Chave chave) {
        Optional<Chave> chaveSalva = repository.findById(chave.getId());
        if (chaveSalva.isPresent() && chaveSalva.get().getDataHoraInativacao() != null) {
            throw new ChaveInativaException(chave.getId());
        }
    }
}
