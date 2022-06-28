package br.com.itau.pix.domain.validation.chave;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
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
class ValidaSePossuiMaisDeVinteCadastroPJTest {
    public static final int PRIORIDADE = 30;
    public static final String OBJETO_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoAleatorioPJ.json";
    public static final String OBJETO_EXISTENTE_MOCK_VALIDO = "src/test/resources/update_um_tipoCnpj.json";
    public static final String OBJETO_PF_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf.json";

    @Mock
    ChaveRepository repository;

    IValidadorChaveInserir validador;

    InclusaoDTO dtoInsert;
    AlteracaoDTO dtoUpdate;

    @BeforeEach
    void setUp() throws IOException {
        validador = new ValidaSePossuiMaisDeVinteCadastroPJ(repository);
        dtoInsert = MockUtils.carregaObjetoMockDeInclucao(OBJETO_NOVO_MOCK_VALIDO);
        dtoUpdate = MockUtils.carregarObjetoMockDeAlteracao(OBJETO_EXISTENTE_MOCK_VALIDO);
    }

    @Test
    void testarPrioridadeDoValidador() {
        assertEquals(PRIORIDADE, validador.getPrioridade());
    }

    @Test
    void devePassarSeNaoForPJ() throws IOException {
        InclusaoDTO inclusaoDTO = MockUtils.carregaObjetoMockDeInclucao(OBJETO_PF_NOVO_MOCK_VALIDO);
        Chave novaChave = new Chave(inclusaoDTO);
        assertDoesNotThrow(() -> validador.chain(novaChave));
    }

    @Test
    void devePassarSeNovaChave_e_NaoEncontradaOutraChaveVinculadaAhConta() {
        List<Chave> chaveList = new ArrayList<>();
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);

        Chave novaChave = new Chave(dtoInsert);
        assertDoesNotThrow(() -> validador.chain(novaChave));
    }

    @Test
    void devePassarSeNovaChave_e_EncontradaDezenoveVinculadaAhConta() throws IOException {
        List < Chave > chaveList = new ArrayList<>();
        gerarListaMockDeChaves(chaveList, 19);

        Chave novaChave = new Chave(dtoInsert);
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(novaChave));
    }

    @Test
    void deveGerarExceptionSeNovaChave_e_EncontradaVinteChavesVinculadasAhConta() throws IOException {
        List < Chave > chaveList = new ArrayList<>();
        gerarListaMockDeChaves(chaveList, 20);

        Chave novaChave = new Chave(dtoInsert);
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertThrows(PossuiLimiteMaximoDeChavesException.class, () -> validador.chain(novaChave));
    }

    @Test
    void devePassarSeEdicao_e_ValorChaveContemNaLista() {
        Chave chaveExistenteNoBanco = new Chave(dtoUpdate);
        chaveExistenteNoBanco.setValorChave("111");

        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(chaveExistenteNoBanco);

        Chave chaveEdicao = new Chave(dtoUpdate);
        chaveEdicao.setValorChave("111");
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(chaveEdicao));
    }

    @Test
    void devePassarSeEdicao_e_ValorChaveNaoContemNaLista_e_NaoAtingiuLimite() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        gerarListaMockDeChaves(chaveList, 19);

        Chave chaveEdicao = new Chave(dtoUpdate);
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(chaveEdicao));
    }

    @Test
    void deveGerarExceptionSeEdicao_e_ValorChaveNaoContemNaLista_e_AtingiuLimite() throws IOException {
        List<Chave> chaveList = new ArrayList<>();
        gerarListaMockDeChaves(chaveList, 20);

        Chave chaveEdicao = new Chave(dtoUpdate);
        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertThrows(PossuiLimiteMaximoDeChavesException.class, () -> validador.chain(chaveEdicao));
    }

    @Test
    void devePassarSeEdicao_e_ValorChaveContemNaLista_e_AtingiuLimite() throws IOException {
        Chave chaveExistenteNoBanco = new Chave(dtoUpdate);
        chaveExistenteNoBanco.setValorChave("111");

        List<Chave> chaveList = new ArrayList<>();
        chaveList.add(new Chave(dtoInsert));
        gerarListaMockDeChaves(chaveList, 18);
        chaveList.add(chaveExistenteNoBanco);

        Chave chaveEdicao = new Chave(dtoUpdate);
        chaveEdicao.setValorChave("111");

        when(repository.findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(anyInt(), anyInt(), any())).thenReturn(chaveList);
        assertDoesNotThrow(() -> validador.chain(chaveEdicao));
    }

    private void gerarListaMockDeChaves(List<Chave> chaveList, int qtde) throws IOException {
        for (int x = 1; x<=qtde ; x++){
            Chave chave = new Chave(MockUtils.carregaObjetoMockDeInclucao(OBJETO_NOVO_MOCK_VALIDO));
            chave.setValorChave(chave.getValorChave()+x);
            chaveList.add(chave);
        }
    }
}