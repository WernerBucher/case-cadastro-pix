package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.ChaveDTO;
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
    public void chain(ChaveDTO dto) {
        if (TipoChave.EMAIL.equals(dto.getTipoChave())) {
            String email = dto.getValorChave();
            if(!ehEmailValido(email) || tamanhoMaiorQueLimite(email)){
                throw new RegexException(dto.getTipoChave().name());
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
