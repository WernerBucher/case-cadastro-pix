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
public class ValidaTipoCelular implements IValidadorNovaChave, IValidadorAlteracaoChave {

    public static final String REGEX_CELULAR = "\\+([0-9]{1,2})+([0-9]{1,3})+([0-9]{9})";

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(Chave chave) {
        if (TipoChave.CELULAR.equals(chave.getTipoChave())) {
            Pattern pattern = Pattern.compile(REGEX_CELULAR);
            Matcher matcher = pattern.matcher(chave.getValorChave());
            if(!matcher.find()){
                throw new RegexException(chave.getTipoChave().name());
            }
        }
    }
}
