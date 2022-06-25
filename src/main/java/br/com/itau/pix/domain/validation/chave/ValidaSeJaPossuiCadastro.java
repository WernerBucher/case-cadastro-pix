package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.IRequisicaoDTO;
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
    public void chain(IRequisicaoDTO dto) {
        if(ehNovaChave(dto)){
            Optional<Chave> chaveSalva = repository.findByValorChave(dto.getValorChave());
            if (chaveSalva.isPresent()) {
                throw new ChaveJaExisteException(dto.getValorChave());
            }
        }
    }

    private boolean ehNovaChave(IRequisicaoDTO requisicao) {
        return requisicao.getId() == null;
    }
}
