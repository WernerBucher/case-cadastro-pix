package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.ChaveDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.ChaveJaExisteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidaSeAlteracaoDeChaveInativa implements IValidadorNovaChave {

    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 11;
    }

    @Override
    public void chain(ChaveDTO dto) {
        if(!ehNovaChave(dto)){
            Optional<Chave> chaveSalva = repository.findById(dto.getId());
            if (chaveSalva.isPresent() && chaveSalva.get().getDataHoraInativacao() != null) {
                throw new ChaveJaExisteException(dto.getValorChave());
            }
        }
    }

    private boolean ehNovaChave(ChaveDTO requisicao) {
        return requisicao.getId() == null;
    }
}
