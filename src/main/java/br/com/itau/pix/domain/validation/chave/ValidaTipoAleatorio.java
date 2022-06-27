package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.validation.IValidadorAlteracaoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidaTipoAleatorio implements IValidadorNovaChave, IValidadorAlteracaoChave {

    public static final String REGEX_ALEATORIO = "^\\w+$";
    public static final int TAMANHO_MAX = 36;

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(Chave chave) {
        if (TipoChave.ALEATORIO.equals(chave.getTipoChave())) {
            Pattern pattern = Pattern.compile(REGEX_ALEATORIO);
            Matcher matcher = pattern.matcher(chave.getValorChave());
            if(!matcher.find() || chave.getValorChave().length() > TAMANHO_MAX){
                throw new RegexException(chave.getTipoChave().name());
            }
        }
    }
}
