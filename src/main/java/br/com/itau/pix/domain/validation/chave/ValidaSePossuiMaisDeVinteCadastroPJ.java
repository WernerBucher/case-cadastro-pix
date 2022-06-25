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

@Component
@RequiredArgsConstructor
public class ValidaSePossuiMaisDeVinteCadastroPJ implements IValidadorNovaChave {

    public static final int MAX_CHAVES_PJ = 20;
    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 30;
    }

    @Override
    public void chain(ChaveDTO dto) {
        if (TipoPessoa.J.equals(dto.getTipoPessoa())) {
            //TODO: SE Edição (possui id) e chave da req contem na lista. Return
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(dto.getNumeroAgencia(), dto.getNumeroConta(), TipoPessoa.J);
            if (chavesDaConta.size() == MAX_CHAVES_PJ) {
                throw new PossuiLimiteMaximoDeChavesException(dto.getNumeroAgencia(), dto.getNumeroConta());
            }
        }
    }
}
