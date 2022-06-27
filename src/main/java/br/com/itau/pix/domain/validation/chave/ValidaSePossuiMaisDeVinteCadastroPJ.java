package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorAlteracaoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.PossuiLimiteMaximoDeChavesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidaSePossuiMaisDeVinteCadastroPJ implements IValidadorNovaChave, IValidadorAlteracaoChave {

    public static final int MAX_CHAVES_PJ = 20;
    private final ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 30;
    }

    @Override
    public void chain(Chave chave) {
        if (TipoPessoa.J.equals(chave.getTipoPessoa())) {
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(chave.getNumeroAgencia(), chave.getNumeroConta(), TipoPessoa.J);
            List<Chave> chavesIguais = chavesDaConta.stream().filter(a -> a.getValorChave().equals(chave.getValorChave())).collect(Collectors.toList());
            if (ehNovaChave(chave) || chavesIguais.isEmpty()) {
                if (chavesDaConta.size() == MAX_CHAVES_PJ) {
                    throw new PossuiLimiteMaximoDeChavesException(chave.getNumeroAgencia(), chave.getNumeroConta());
                }
            }
        }
    }
    private boolean ehNovaChave (Chave chave){
        return chave.getId() == null;
    }
}
