package br.com.itau.pix.domain.service;

import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.ChaveJaExisteException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChaveServiceTest {

    @Mock
    private ChaveRepository repository;

    @Mock
    private List<IValidadorNovaChave> validadorNovaChave;

    @InjectMocks
    private ChaveService service;

    @BeforeEach
    void setUp() {


    }

    @Test
    void deveInserirChaveComSucesso() {
        InclusaoDTO dto = new InclusaoDTO();
        dto.setTipoChave(TipoChave.ALEATORIO);
        dto.setValorChave("Chave1");
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        given(repository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Chave chaveSalva = service.inserirChave(dto);
        Assertions.assertThat(chaveSalva).isNotNull();

        verify(repository).save(any(Chave.class));
    }

    @Test
    void deveGerarExceptionAoInserirChave_NaoPasouNasRegrasDeNegocio() {
        InclusaoDTO dto = new InclusaoDTO();
        dto.setTipoChave(TipoChave.ALEATORIO);
        dto.setValorChave("Chave1");
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);

        given(validadorNovaChave.stream()).willThrow(ChaveJaExisteException.class);
        assertThrows(ChaveJaExisteException.class, () -> service.inserirChave(dto));
        verify(repository, times(0)).save(any(Chave.class));
    }

    @Test
    void editarChave() {
    }

    @Test
    void deletarChave() {
    }

    @Test
    void listarPorId() {
    }

    @Test
    void listarComFiltro() {
    }
}