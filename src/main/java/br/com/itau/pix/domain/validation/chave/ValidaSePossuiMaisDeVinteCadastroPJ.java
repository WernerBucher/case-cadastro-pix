package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.IRequisicaoDTO;
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
    public void chain(IRequisicaoDTO requisicao) {
        if (TipoPessoa.J.equals(requisicao.getTipoPessoa())) {
            //TODO: SE Edição (possui id) e chave da req contem na lista. Return
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(requisicao.getNumeroAgencia(), requisicao.getNumeroConta(), TipoPessoa.J);
            if (chavesDaConta.size() == MAX_CHAVES_PJ) {
                throw new PossuiLimiteMaximoDeChavesException(requisicao.getNumeroAgencia(), requisicao.getNumeroConta());
            }
        }
    }
}
