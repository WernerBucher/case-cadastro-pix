package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.ConsultaDTO;
import br.com.itau.pix.domain.validation.IValidadorConsulta;
import br.com.itau.pix.exception.ChaveCombinadaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaConsultaSeChaveCombinadaComIdTest {

    public static final int PRIORIDADE = 10;

    IValidadorConsulta validador;

    ConsultaDTO dtoValido;

    ConsultaDTO dtoInvalido;
    @BeforeEach
    void setUp() throws IOException {
        dtoInvalido = new ConsultaDTO(UUID.fromString("5f3ae2de-4884-41d2-8c7c-387bf5e0f18d"), "nome", "cpf");
        dtoValido = new ConsultaDTO(UUID.fromString("5f3ae2de-4884-41d2-8c7c-387bf5e0f18d"), null, null);
        validador = new ValidaConsultaSeChaveCombinadaComId();
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void devePassar_FiltroSomenteId() {
        assertDoesNotThrow(() -> validador.chain(dtoValido));
    }

    @Test
    void deveGerarException_FiltroCombinadoComId() {
        assertThrows(ChaveCombinadaException.class, () -> validador.chain(dtoInvalido));
    }
}