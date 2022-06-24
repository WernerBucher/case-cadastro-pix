package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidaTipoAleatorio implements IValidadorNovaChave {

    public static final String REGEX_ALEATORIO = "^\\w+$";
    public static final int TAMANHO_MAX = 36;

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        if (TipoChave.ALEATORIO.equals(TipoChave.valueOf(requisicao.getTipoChave()))) {
            Pattern pattern = Pattern.compile(REGEX_ALEATORIO);
            Matcher matcher = pattern.matcher(requisicao.getValorChave());
            if(!matcher.find() || requisicao.getValorChave().length() > TAMANHO_MAX){
                throw new RegexException(requisicao.getTipoChave());
            }
        }
    }
}
