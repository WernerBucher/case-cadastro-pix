package br.com.itau.pix.domain.service;

import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.exception.ChaveJaExisteException;
import br.com.itau.pix.domain.exception.RegexException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import br.com.itau.pix.domain.validation.regras.ValidaSeJaPossuiCadastro;
import br.com.itau.pix.domain.validation.regras.ValidaTipoAleatorio;
import br.com.itau.pix.domain.validation.regras.ValidaTipoEmail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChaveServiceInserirTest {

    @Mock
    private ChaveRepository repository;

    @Mock
    private List<IValidadorChaveInserir> validadorNovaChave;

    @InjectMocks
    private ChaveService service;

    @BeforeEach
    void setUp() {
        reset(repository, validadorNovaChave);
    }

    @Test
    void deveInserirChaveComSucesso() {
        InclusaoDTO dto = getInclusaoDTO("Chave1");

        given(repository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Chave chaveSalva = service.inserirChave(dto);
        Assertions.assertThat(chaveSalva).isNotNull();

        verify(repository).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoInserirChave_NaoPasouNasRegrasDeNegocio() {
        InclusaoDTO dto = getInclusaoDTO("Chave1");

        when(validadorNovaChave.stream()).thenThrow(ChaveJaExisteException.class);
        assertThrows(ChaveJaExisteException.class, () -> service.inserirChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoInserirChave_DeAcordoComPrioridade() {
        InclusaoDTO dto = getInclusaoDTO("Chave1");

        ValidaSeJaPossuiCadastro validaSeJaPossuiCadastro = new ValidaSeJaPossuiCadastro(repository);
        ValidaTipoEmail validaTipoEmail = new ValidaTipoEmail();

        List<IValidadorChaveInserir> validadorNovaChaves = new ArrayList<>();
        validadorNovaChaves.add(validaTipoEmail);
        validadorNovaChaves.add(validaSeJaPossuiCadastro);

        when(validadorNovaChave.stream()).thenReturn(validadorNovaChaves.stream());
        given(repository.findByValorChave(any())).willAnswer(invocation -> Optional.of(new Chave()));

        assertThrows(ChaveJaExisteException.class, () -> service.inserirChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoInserirChave_ValorChaveImcompativel() {
        InclusaoDTO dto = getInclusaoDTO("Chave.1");

        ValidaSeJaPossuiCadastro validaSeJaPossuiCadastro = new ValidaSeJaPossuiCadastro(repository);
        ValidaTipoAleatorio validaTipoAleatorio = new ValidaTipoAleatorio();

        List<IValidadorChaveInserir> validadorNovaChaves = new ArrayList<>();
        validadorNovaChaves.add(validaTipoAleatorio);
        validadorNovaChaves.add(validaSeJaPossuiCadastro);

        when(validadorNovaChave.stream()).thenReturn(validadorNovaChaves.stream());

        assertThrows(RegexException.class, () -> service.inserirChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    private InclusaoDTO getInclusaoDTO(String valorChave) {
        InclusaoDTO dto = new InclusaoDTO();
        dto.setTipoChave(TipoChave.ALEATORIO);
        dto.setValorChave(valorChave);
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);
        return dto;
    }

}