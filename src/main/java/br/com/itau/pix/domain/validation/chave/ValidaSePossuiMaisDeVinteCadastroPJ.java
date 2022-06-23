package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.PossuiLimiteMaximoDeChavesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidaSePossuiMaisDeVinteCadastroPJ implements IValidadorNovaChave {

    @Autowired
    ChaveRepository repository;

    @Override
    public Integer getPrioridade() {
        return 30;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        if (TipoPessoa.J.equals(TipoPessoa.valueOf(requisicao.getTipoPessoa()))) {
            List<Chave> chavesDaConta = repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(requisicao.getNumeroAgencia(), requisicao.getNumeroConta(), TipoPessoa.J);
            if (chavesDaConta.size() == 20) {
                throw new PossuiLimiteMaximoDeChavesException(requisicao.getNumeroAgencia(), requisicao.getNumeroConta());
            }
        }
    }
}
