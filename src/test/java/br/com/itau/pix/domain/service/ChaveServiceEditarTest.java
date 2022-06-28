package br.com.itau.pix.domain.service;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.exception.ChaveInativaException;
import br.com.itau.pix.domain.exception.ChaveNaoEncontradaException;
import br.com.itau.pix.domain.exception.PossuiLimiteMaximoDeChavesException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveAlterar;
import br.com.itau.pix.domain.validation.regras.ValidaSeChaveInativa;
import br.com.itau.pix.domain.validation.regras.ValidaSePossuiMaisDeCincoCadastroPF;
import br.com.itau.pix.domain.validation.regras.ValidaTipoEmail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChaveServiceEditarTest {

    @Mock
    private ChaveRepository repository;

    @Mock
    private List<IValidadorChaveAlterar> validadorAlteracaoChave;


    @InjectMocks
    private ChaveService service;

    @BeforeEach
    void setUp() {
        reset(repository, validadorAlteracaoChave);
    }

    @Test
    void deveEditarChaveComSucesso() {
        Chave chaveNoBanco = new Chave();
        chaveNoBanco.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        chaveNoBanco.setTipoChave(TipoChave.ALEATORIO);
        chaveNoBanco.setValorChave("Chave1");
        chaveNoBanco.setTipoConta(TipoConta.CORRENTE);
        chaveNoBanco.setNumeroAgencia(1);
        chaveNoBanco.setNumeroConta(1);
        chaveNoBanco.setNomeCorrentista("Werner");
        chaveNoBanco.setTipoPessoa(TipoPessoa.F);

        AlteracaoDTO dto = new AlteracaoDTO();
        dto.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        when(repository.findById(any())).thenReturn(Optional.of(chaveNoBanco));

        given(repository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Chave chaveSalva = service.editarChave(dto);
        Assertions.assertThat(chaveSalva).isNotNull();

        verify(repository).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoEditar_ChaveEncontradaNoBanco_MasNaoPassouNasRegrasDeValidacoes() {
        Chave chaveNoBanco = new Chave();
        chaveNoBanco.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        chaveNoBanco.setTipoChave(TipoChave.ALEATORIO);
        chaveNoBanco.setValorChave("Chave1");
        chaveNoBanco.setTipoConta(TipoConta.CORRENTE);
        chaveNoBanco.setNumeroAgencia(1);
        chaveNoBanco.setNumeroConta(1);
        chaveNoBanco.setNomeCorrentista("Werner");
        chaveNoBanco.setTipoPessoa(TipoPessoa.F);

        AlteracaoDTO dto = new AlteracaoDTO();
        dto.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        when(repository.findById(any())).thenReturn(Optional.of(chaveNoBanco));

        when(validadorAlteracaoChave.stream()).thenThrow(PossuiLimiteMaximoDeChavesException.class);
        assertThrows(PossuiLimiteMaximoDeChavesException.class, () -> service.editarChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoEditarChave_ChaveNaoEncontradaNoBanco() {
        AlteracaoDTO dto = new AlteracaoDTO();
        dto.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        assertThrows(ChaveNaoEncontradaException.class, () -> service.editarChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoEditarChave_DeAcordoComPrimeiraPrioridade() {
        AlteracaoDTO dto = new AlteracaoDTO();
        dto.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        ValidaSeChaveInativa validaSeChaveInativa = new ValidaSeChaveInativa(repository);
        ValidaTipoEmail validaTipoEmail = new ValidaTipoEmail();

        List<IValidadorChaveAlterar> validadorList = new ArrayList<>();
        validadorList.add(validaTipoEmail);
        validadorList.add(validaSeChaveInativa);

        when(validadorAlteracaoChave.stream()).thenReturn(validadorList.stream());
        Chave chave = new Chave();
        chave.setDataHoraInativacao(new Date());
        given(repository.findById(any())).willAnswer(invocation -> Optional.of(chave));

        assertThrows(ChaveInativaException.class, () -> service.editarChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    @Test
    void deveEditarChave_PassandoPorVariasValidacoes() {
        AlteracaoDTO dto = new AlteracaoDTO();
        dto.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        ValidaSeChaveInativa validaSeChaveInativa = new ValidaSeChaveInativa(repository);
        ValidaSePossuiMaisDeCincoCadastroPF validaSePossuiMaisDeCincoCadastroPF = new ValidaSePossuiMaisDeCincoCadastroPF(repository);

        List<IValidadorChaveAlterar> validadorList = new ArrayList<>();
        validadorList.add(validaSePossuiMaisDeCincoCadastroPF);
        validadorList.add(validaSeChaveInativa);

        when(validadorAlteracaoChave.stream()).thenReturn(validadorList.stream());
        given(repository.findById(any())).willAnswer(invocation -> Optional.of(new Chave()));

        assertDoesNotThrow(() -> service.editarChave(dto));
        verify(repository).save(any(Chave.class));
    }
}