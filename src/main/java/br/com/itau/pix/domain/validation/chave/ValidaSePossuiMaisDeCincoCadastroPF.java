package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.ChaveDTO;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.PossuiLimiteMaximoDeChavesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidaSePossuiMaisDeCincoCadastroPF implements IValidadorNovaChave {

    public static final int MAX_CHAVES_PF = 5;
    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 20;
    }

    @Override
    public void chain(ChaveDTO dto) {
        if (TipoPessoa.F.equals(dto.getTipoPessoa())) {
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(dto.getNumeroAgencia(), dto.getNumeroConta(), dto.getTipoPessoa());
            List<Chave> chavesIguais = chavesDaConta.stream().filter(chave -> chave.getValorChave().equals(dto.getValorChave())).collect(Collectors.toList());
            if (ehNovaChave(dto) || chavesIguais.isEmpty()) {
                if (chavesDaConta.size() == MAX_CHAVES_PF) {
                    throw new PossuiLimiteMaximoDeChavesException(dto.getNumeroAgencia(), dto.getNumeroConta());
                }
            }
        }
    }

    private boolean ehNovaChave(ChaveDTO dto) {
        return dto.getId() == null;
    }
}
