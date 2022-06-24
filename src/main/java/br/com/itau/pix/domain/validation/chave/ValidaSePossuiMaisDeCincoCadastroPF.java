package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.PossuiLimiteMaximoDeChavesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidaSePossuiMaisDeCincoCadastroPF implements IValidadorNovaChave {

    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 20;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        if (TipoPessoa.F.equals(TipoPessoa.valueOf(requisicao.getTipoPessoa()))) {
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(requisicao.getNumeroAgencia(), requisicao.getNumeroConta(), TipoPessoa.valueOf(requisicao.getTipoPessoa()));
            if (chavesDaConta.size() == 5) {
                throw new PossuiLimiteMaximoDeChavesException(requisicao.getNumeroAgencia(), requisicao.getNumeroConta());
            }
        }
    }
}
