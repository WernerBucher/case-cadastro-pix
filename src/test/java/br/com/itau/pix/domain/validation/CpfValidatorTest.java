package br.com.itau.pix.domain.validation;

import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpfValidatorTest {

    private static final String ARQUIVO_MOCK_VALIDO = "src/test/resources/cpf_lista_Validos.txt";
    private static final String ARQUIVO_MOCK_INVALIDO = "src/test/resources/cpf_lista_Invalidos.txt";
    private List<String> listaValida;
    private List<String> listaInvalida;

    @BeforeEach
    void setUp() throws IOException {
        listaValida = MockUtils.listaMockStrings(ARQUIVO_MOCK_VALIDO);
        listaInvalida = MockUtils.listaMockStrings(ARQUIVO_MOCK_INVALIDO);
    }

    @Test
    void validaCpfValidos() {
        listaValida.forEach(cpf -> assertTrue(CpfValidator.validaCpf(cpf)));
        assertFalse(listaValida.isEmpty());
    }

    @Test
    void validaCpfInvalidos() {
        listaInvalida.forEach(cpf -> assertFalse(CpfValidator.validaCpf(cpf)));
        assertFalse(listaValida.isEmpty());
    }
}