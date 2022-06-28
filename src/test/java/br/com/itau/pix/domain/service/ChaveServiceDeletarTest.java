package br.com.itau.pix.domain.service;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.exception.ChaveInativaException;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorChaveDeletar;
import br.com.itau.pix.domain.validation.regras.ValidaSeChaveInativa;
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
class ChaveServiceDeletarTest {

    @Mock
    private ChaveRepository repository;

    @Mock
    private List<IValidadorChaveDeletar> validadorDeletarChave;

    @InjectMocks
    private ChaveService service;

    @BeforeEach
    void setUp() {
        reset(repository, validadorDeletarChave);
    }

    @Test
    void deveDeletarChaveComSucesso() {
        Chave chaveNoBanco = getMockChaveNoBanco();
        UUID uuid = UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9");
        when(repository.findById(any())).thenReturn(Optional.of(chaveNoBanco));
        given(repository.save(any())).willAnswer(invocation -> invocation.getArgument(0));
        Chave chaveSalva = service.deletarChave(uuid);
        Assertions.assertThat(chaveSalva).isNotNull();
        verify(repository).save(any(Chave.class));
    }


    @Test
    void deveDeletarChaveComSucessoPassandoPorVariasValidacoes() {
        Chave chaveNoBanco = getMockChaveNoBanco();
        UUID uuid = UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9");
        when(repository.findById(any())).thenReturn(Optional.of(chaveNoBanco));

        ValidaSeChaveInativa validaSeChaveInativa1 = new ValidaSeChaveInativa(repository);
        ValidaSeChaveInativa validaSeChaveInativa2 = new ValidaSeChaveInativa(repository);

        List<IValidadorChaveDeletar> validadorList = new ArrayList<>();
        validadorList.add(validaSeChaveInativa1);
        validadorList.add(validaSeChaveInativa2);

        when(validadorDeletarChave.stream()).thenReturn(validadorList.stream());
        given(repository.findById(any())).willAnswer(invocation -> Optional.of(new Chave()));

        assertDoesNotThrow(() -> service.deletarChave(uuid));
        verify(repository).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionaoDeletar_ChaveDesativada() {
        Chave chaveNoBanco = getMockChaveNoBanco();
        chaveNoBanco.setDataHoraInativacao(new Date());

        UUID uuid = UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9");

        ValidaSeChaveInativa validaSeChaveInativa1 = new ValidaSeChaveInativa(repository);
        ValidaSeChaveInativa validaSeChaveInativa2 = new ValidaSeChaveInativa(repository);

        List<IValidadorChaveDeletar> validadorList = new ArrayList<>();
        validadorList.add(validaSeChaveInativa1);
        validadorList.add(validaSeChaveInativa2);

        when(validadorDeletarChave.stream()).thenReturn(validadorList.stream());
        when(repository.findById(any())).thenReturn(Optional.of(chaveNoBanco));

        assertThrows(ChaveInativaException.class, () -> service.deletarChave(uuid));
        verify(repository, times(0)).save(any(Chave.class));
    }

    private Chave getMockChaveNoBanco() {
        Chave chaveNoBanco = new Chave();
        chaveNoBanco.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        chaveNoBanco.setTipoChave(TipoChave.ALEATORIO);
        chaveNoBanco.setValorChave("Chave1");
        chaveNoBanco.setTipoConta(TipoConta.CORRENTE);
        chaveNoBanco.setNumeroAgencia(1);
        chaveNoBanco.setNumeroConta(1);
        chaveNoBanco.setNomeCorrentista("Werner");
        chaveNoBanco.setTipoPessoa(TipoPessoa.F);
        return chaveNoBanco;
    }

}