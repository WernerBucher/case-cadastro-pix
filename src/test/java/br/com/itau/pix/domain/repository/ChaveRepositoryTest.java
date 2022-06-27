package br.com.itau.pix.domain.repository;

import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.predicate.ChavePredicateBuilder;
import br.com.itau.pix.util.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChaveRepositoryTest {

    public static final String OBJETO_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf.json";

    @Autowired
    ChaveRepository repository;
    Chave chaveSalva;

    @BeforeEach
    void setUp() throws IOException {
        InclusaoDTO inclusaoDTO = MockUtils.carregaObjetoMockDeInclucao(OBJETO_NOVO_MOCK_VALIDO);
        Chave chave = new Chave(inclusaoDTO);
        chaveSalva = repository.save(chave);
    }

    @Test
    @Order(1)
    void deveInserirRegistroNoBanco() throws IOException {
        InclusaoDTO inclusaoDTO = MockUtils.carregaObjetoMockDeInclucao(OBJETO_NOVO_MOCK_VALIDO);
        Chave chave = new Chave(inclusaoDTO);
        Chave save = repository.save(chave);
        assertEquals(inclusaoDTO.getValorChave(), save.getValorChave());
    }

    @Test
    void deveContemRegistroNoBanco(){
        List<Chave> all = repository.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void deveConsultarRegistroNoBancoPorId(){
        Optional<Chave> byId = repository.findById(chaveSalva.getId());
        assertTrue(byId.isPresent());
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloNomeCorrentista() {
        ChavePredicateBuilder builder = new ChavePredicateBuilder().with("nomeCorrentista", ":", "Correntista 1");
        Iterable<Chave> results = repository.findAll(builder.build());
        assertThat(results, containsInAnyOrder(chaveSalva));
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloNomeCorrentista_e_TipoChave() {
        ChavePredicateBuilder builder = new ChavePredicateBuilder()
                .with("nomeCorrentista", ":", "Correntista 1")
                .with("tipoChave", ":", "CPF");
        Iterable<Chave> results = repository.findAll(builder.build());
        assertThat(results, containsInAnyOrder(chaveSalva));
    }

    @Test
    public void deveEncontrarChaveFiltrandoPelo_TipoChave() {
        ChavePredicateBuilder builder = new ChavePredicateBuilder()
                .with("tipoChave", ":", "CPF");
        Iterable<Chave> results = repository.findAll(builder.build());
        assertThat(results, containsInAnyOrder(chaveSalva));
    }



}