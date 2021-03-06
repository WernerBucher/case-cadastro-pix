package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.exception.ChaveInativaException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveAlterar;
import br.com.itau.pix.domain.validation.IValidadorChaveDeletar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidaSeChaveInativa implements IValidadorChaveAlterar, IValidadorChaveDeletar {

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
