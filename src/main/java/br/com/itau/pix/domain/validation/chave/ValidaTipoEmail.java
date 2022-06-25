package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.IRequisicaoDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidaTipoEmail implements IValidadorNovaChave {

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
    public static final int TAMANHO_MAX = 77;

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(IRequisicaoDTO requisicao) {
        if (TipoChave.EMAIL.equals(requisicao.getTipoChave())) {
            String email = requisicao.getValorChave();
            if(!ehEmailValido(email) || tamanhoMaiorQueLimite(email)){
                throw new RegexException(requisicao.getTipoChave().name());
            }
        }
    }

    private boolean tamanhoMaiorQueLimite(String email) {
        return email.length() > TAMANHO_MAX;
    }

    private boolean ehEmailValido(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
