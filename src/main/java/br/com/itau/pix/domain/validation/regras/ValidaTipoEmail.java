package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.exception.RegexException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.validation.IValidadorChaveAlterar;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidaTipoEmail implements IValidadorChaveInserir, IValidadorChaveAlterar {

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
    public static final int TAMANHO_MAX = 77;

    @Override
    public Integer getPrioridade() {
        return 40;
    }

    @Override
    public void chain(Chave chave) {
        if (TipoChave.EMAIL.equals(chave.getTipoChave())) {
            String email = chave.getValorChave();
            if(!ehEmailValido(email) || tamanhoMaiorQueLimite(email)){
                throw new RegexException(chave.getTipoChave().name());
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
