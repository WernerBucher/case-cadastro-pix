package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveDeletar;
import br.com.itau.pix.exception.ChaveInativaException;
import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaSeChaveInativaTest {
    public static final int PRIORIDADE = 10;
    public static final String OBJETO_EXISTENTE_MOCK_VALIDO = "src/test/resources/update_um_tipoCpf.json";

    @Mock
    ChaveRepository repository;

    IValidadorChaveDeletar validador;

    AlteracaoDTO dtoUpdate;

    @BeforeEach
    void setUp() throws IOException {
        validador = new ValidaSeChaveInativa(repository);
        dtoUpdate = MockUtils.carregarObjetoMockDeAlteracao(OBJETO_EXISTENTE_MOCK_VALIDO);
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void devePassarSe_ChaveNaoEncontrada() {
        Chave chave = new Chave(dtoUpdate);
        assertDoesNotThrow(() -> validador.chain(chave));
    }

    @Test
    void devePassarSeChaveEncontrada_e_Ativa() {
        Chave chave = new Chave(dtoUpdate);
        when(repository.findById(dtoUpdate.getId())).thenReturn(Optional.of(chave));
        assertDoesNotThrow(() -> validador.chain(chave));
    }

    @Test
    void deveGerarExceptionSeChaveEncontrada_e_Inativa() {
        Chave chave = new Chave(dtoUpdate);
        chave.setDataHoraInativacao(new Date());
        when(repository.findById(dtoUpdate.getId())).thenReturn(Optional.of(chave));
        assertThrows(ChaveInativaException.class, () -> validador.chain(chave));
    }
}