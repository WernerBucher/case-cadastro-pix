package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.exception.RegexException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidaTipoCpfTest{

    public static final int PRIORIDADE = 40;
    public static final String LISTA_MOCK_VALIDO = "src/test/resources/insert_lista_CpfValido.json";
    public static final String LISTA_MOCK_INVALIDO = "src/test/resources/insert_lista_CpfInvalido.json";
    IValidadorChaveInserir validador;
    List<InclusaoDTO> listaValida;
    List<InclusaoDTO> listaInvalida;

    @BeforeEach
    void setUp() throws IOException {
        validador = new ValidaTipoCpf();
        listaValida = MockUtils.carregarListaMockDeInclusao(LISTA_MOCK_VALIDO);
        listaInvalida = MockUtils.carregarListaMockDeInclusao(LISTA_MOCK_INVALIDO);
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void testarUmalistaDeRequisicoesValida() {
        listaValida.forEach(dto -> {
            Chave chave = new Chave(dto);
            assertDoesNotThrow(() -> validador.chain(chave));
        });
        assertFalse(listaValida.isEmpty());
    }

    @Test
    void testarUmalistaDeRequisicoesInvalidas() {
        listaInvalida.forEach(dto -> {
            Chave chave = new Chave(dto);
            assertThrows(RegexException.class, () -> validador.chain(chave));
        });
        assertFalse(listaInvalida.isEmpty());
    }
}