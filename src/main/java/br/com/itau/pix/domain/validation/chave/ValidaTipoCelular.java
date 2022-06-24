package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidaTipoCelular implements IValidadorNovaChave {

    public static final String REGEX_CELULAR = "\\+([0-9]{1,2})+([0-9]{1,3})+([0-9]{9})";

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        if (TipoChave.CELULAR.equals(TipoChave.valueOf(requisicao.getTipoChave()))) {
            Pattern pattern = Pattern.compile(REGEX_CELULAR);
            Matcher matcher = pattern.matcher(requisicao.getValorChave());
            if(!matcher.find()){
                throw new RegexException(requisicao.getTipoChave());
            }
        }
    }
}
