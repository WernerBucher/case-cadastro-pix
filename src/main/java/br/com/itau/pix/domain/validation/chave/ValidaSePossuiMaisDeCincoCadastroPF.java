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
    public void chain(IRequisicaoDTO requisicao) {
        if (TipoPessoa.F.equals(requisicao.getTipoPessoa())) {
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(requisicao.getNumeroAgencia(), requisicao.getNumeroConta(), requisicao.getTipoPessoa());
            List<Chave> chavesIguais = chavesDaConta.stream().filter(chave -> chave.getValorChave().equals(requisicao.getValorChave())).collect(Collectors.toList());
            if (ehNovaChave(requisicao) || chavesIguais.isEmpty()) {
                if (chavesDaConta.size() == MAX_CHAVES_PF) {
                    throw new PossuiLimiteMaximoDeChavesException(requisicao.getNumeroAgencia(), requisicao.getNumeroConta());
                }
            }
        }
    }

    private boolean ehNovaChave(IRequisicaoDTO requisicao) {
        return requisicao.getId() == null;
    }
}
