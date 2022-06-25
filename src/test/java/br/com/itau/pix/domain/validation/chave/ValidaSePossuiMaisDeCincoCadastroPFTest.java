package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.IRequisicaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.PossuiLimiteMaximoDeChavesException;
import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaSePossuiMaisDeCincoCadastroPFTest {
    public static final int PRIORIDADE = 20;
    public static final String OBJETO_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf.json";
    public static final String OBJETO1_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf1.json";
    public static final String OBJETO2_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf2.json";
    public static final String OBJETO3_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf3.json";
    public static final String OBJETO4_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf4.json";
    public static final String OBJETO_EXISTENTE_MOCK_VALIDO = "src/test/resources/update_TipoCpf.json";
    public static final String OBJETO_PJ_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCnpj.json";

    @Mock
    ChaveRepository repository;

    IValidadorNovaChave validador;

    IRequisicaoDTO dtoInsert;
    IRequisicaoDTO dtoUpdate;

    @BeforeEach
    void setUp() throws IOException {
        validador = new ValidaSePossuiMaisDeCincoCadastroPF(repository);
        dtoInsert = MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_NOVO_MOCK_VALIDO);
        dtoUpdate = MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_EXISTENTE_MOCK_VALIDO);
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void devePassarSeNaoForPF() {
        assertDoesNotThrow(() -> validador.chain(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_PJ_NOVO_MOCK_VALIDO)));
    }

    @Test
    void devePassarSeNovaChaveEhNaoEncontradaOutraChaveVinculadaAhConta() {
        List<Chave> chaveList = new ArrayList<>();
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(dtoInsert));
    }

    @Test
    void devePassarSeNovaChaveEhEncontradaQuatroChavesVinculadaAhConta() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO1_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO2_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO3_NOVO_MOCK_VALIDO)));

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(dtoInsert));
    }

    @Test
    void deveGerarExceptionSeNovaChaveEhEncontradaCincoChavesVinculadasAhConta() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO1_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO2_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO3_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO4_NOVO_MOCK_VALIDO)));

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertThrows(PossuiLimiteMaximoDeChavesException.class, () -> validador.chain(dtoInsert));
    }

    @Test
    void devePassarSeEdicaoEhValorChaveContemNaLista() {
        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(dtoUpdate));

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(dtoUpdate));
    }

    @Test
    void devePassarSeEdicaoEhValorChaveNaoContemNaListaMasNaoAtingiuLimite() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO1_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO2_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO3_NOVO_MOCK_VALIDO)));

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(dtoUpdate));
    }

    @Test
    void deveGerarExceptionSeEdicaoEhValorChaveNaoContemNaListaMasAtingiuLimite() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO1_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO2_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO3_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO4_NOVO_MOCK_VALIDO)));

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertThrows(PossuiLimiteMaximoDeChavesException.class, () -> validador.chain(dtoInsert));
    }

    @Test
    void devePassarSeEdicaoEhValorChaveContemNaListaMasAtingiuLimite() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO1_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO2_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO3_NOVO_MOCK_VALIDO)));
        chaveList.add(new Chave(MockUtils.objetoMockDeRequisicaoDeInsert(OBJETO_EXISTENTE_MOCK_VALIDO)));

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertThrows(PossuiLimiteMaximoDeChavesException.class, () -> validador.chain(dtoInsert));
    }
}