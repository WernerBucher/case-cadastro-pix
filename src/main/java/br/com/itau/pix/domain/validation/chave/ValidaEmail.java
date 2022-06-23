package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidaEmail implements IValidadorNovaChave {

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(RequestDTO requisicao) {
        if (TipoChave.EMAIL.equals(TipoChave.valueOf(requisicao.getTipoChave()))) {
            String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(requisicao.getValorChave());
            if(!matcher.find() || requisicao.getValorChave().length() > 77){
                throw new RegexException(requisicao.getTipoChave());
            }
        }
    }
}
