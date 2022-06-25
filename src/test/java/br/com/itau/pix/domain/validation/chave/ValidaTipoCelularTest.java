package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.InclusaoDTO;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.RegexException;
import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidaTipoCelularTest {

    public static final int PRIORIDADE = 40;
    public static final String LISTA_MOCK_VALIDO = "src/test/resources/insert_lista_CelularValido.json";
    public static final String LISTA_MOCK_INVALIDO = "src/test/resources/insert_lista_CelularInvalido.json";
    IValidadorNovaChave validador;
    List<InclusaoDTO> listaValida;
    List<InclusaoDTO> listaInvalida;

    @BeforeEach
    void setUp() throws IOException {
        validador = new ValidaTipoCelular();
        listaValida = MockUtils.listaMockDeRequisicaoDeInsert(LISTA_MOCK_VALIDO);
        listaInvalida = MockUtils.listaMockDeRequisicaoDeInsert(LISTA_MOCK_INVALIDO);
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void testarUmalistaDeRequisicoesValida() {
        listaValida.forEach(dto -> {
            assertDoesNotThrow(() -> validador.chain(dto));
        });
        assertFalse(listaValida.isEmpty());
    }

    @Test
    void testarUmalistaDeRequisicoesInvalidas() {
        listaInvalida.forEach(dto -> {
            assertThrows(RegexException.class, () -> validador.chain(dto));
        });
        assertFalse(listaInvalida.isEmpty());
    }
}