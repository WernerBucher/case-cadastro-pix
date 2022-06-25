package br.com.itau.pix.domain.validation;

import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CnpjValidatorTest {

    private static final String ARQUIVO_MOCK_VALIDO = "src/test/resources/cnpj_lista_Validos.txt";
    private static final String ARQUIVO_MOCK_INVALIDO = "src/test/resources/cnpj_lista_Invalidos.txt";

    private List<String> listaValida;
    private List<String> listaInvalida;

    @BeforeEach
    void setUp() throws IOException {
        listaValida = MockUtils.listaMockStrings(ARQUIVO_MOCK_VALIDO);
        listaInvalida = MockUtils.listaMockStrings(ARQUIVO_MOCK_INVALIDO);
    }

    @Test
    void validaCpfValidos() {
        listaValida.forEach(cnpj -> assertTrue(CnpjValidator.validaCnpj(cnpj)));
        assertFalse(listaValida.isEmpty());
    }

    @Test
    void validaCpfInvalidos() {
        listaInvalida.forEach(cnpj -> assertFalse(CnpjValidator.validaCnpj(cnpj)));
        assertFalse(listaValida.isEmpty());
    }

}