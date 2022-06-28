package br.com.itau.pix.domain.validation.regras;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.exception.ChaveJaExisteException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaSeJaPossuiCadastroTest {

    public static final int PRIORIDADE = 10;
    public static final String OBJETO_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf.json";
    public static final String OBJETO_EXISTENTE_MOCK_VALIDO = "src/test/resources/update_um_tipoCpf.json";

    @Mock
    ChaveRepository repository;

    IValidadorChaveInserir validador;

    InclusaoDTO dtoInsert;
    AlteracaoDTO dtoUpdate;

    @BeforeEach
    void setUp() throws IOException {
        validador = new ValidaSeJaPossuiCadastro(repository);
        dtoInsert = MockUtils.carregaObjetoMockDeInclucao(OBJETO_NOVO_MOCK_VALIDO);
        dtoUpdate = MockUtils.carregarObjetoMockDeAlteracao(OBJETO_EXISTENTE_MOCK_VALIDO);
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void devePassarSeNovaChaveENaoEncontradaNoBanco() {
        Chave chave = new Chave(dtoInsert);
        when(repository.findByValorChave(dtoInsert.getValorChave())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> validador.chain(chave));
    }

    @Test
    void deveGerarExceptionSeChaveEncontradaNoBanco() {
        Chave chave = new Chave(dtoInsert);
        when(repository.findByValorChave(dtoInsert.getValorChave())).thenReturn(Optional.of(chave));

        assertThrows(ChaveJaExisteException.class, () -> validador.chain(chave));
    }

}