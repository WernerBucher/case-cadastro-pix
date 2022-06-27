package br.com.itau.pix.domain.service;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.exception.ChaveNaoEncontradaException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChaveServiceListarTest {

    @Mock
    private ChaveRepository repository;

    @InjectMocks
    private ChaveService service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void deveRetornarAoListar_ChaveEncontrada() {
        Chave chaveNoBanco = getMockChaveNoBanco();
        UUID uuid = UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9");
        when(repository.findById(uuid)).thenReturn(Optional.of(chaveNoBanco));
        Chave chave = service.listarPorId(uuid);
        Assertions.assertThat(chave).isNotNull();
        assertEquals(TipoConta.CORRENTE, chave.getTipoConta());
    }

    @Test
    void deveGerarExceptionAoListar_ChaveNaoEncontrada() {
        UUID uuid = UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9");
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ChaveNaoEncontradaException.class, () -> service.listarPorId(uuid));
    }

    @Test
    void deveRetornarListaVaziaAoListarComFiltro_ChaveNaoEncontrada() {
        List<Chave> chaveList = service.listarComFiltro("nome", "CPF");
        Assertions.assertThat(chaveList).isNotNull();
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