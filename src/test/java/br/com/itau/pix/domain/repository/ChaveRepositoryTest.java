package br.com.itau.pix.domain.repository;

import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.specification.SpecificationChaveId;
import br.com.itau.pix.domain.specification.SpecificationChaveNome;
import br.com.itau.pix.domain.specification.SpecificationChaveTipoChave;
import br.com.itau.pix.util.MockUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChaveRepositoryTest {

    public static final String OBJETO_NOVO_MOCK_VALIDO = "src/test/resources/insert_um_tipoCpf.json";

    @Autowired
    ChaveRepository repository;
    Chave chaveSalvaMock;

    @BeforeEach
    void setUp() throws IOException {
        InclusaoDTO inclusaoDTO = MockUtils.carregaObjetoMockDeInclucao(OBJETO_NOVO_MOCK_VALIDO);
        Chave chave = new Chave(inclusaoDTO);
        chaveSalvaMock = repository.save(chave);
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
    void deveContemRegistroNoBanco() {
        List<Chave> all = repository.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void deveConsultarRegistroNoBancoPorId() {
        Optional<Chave> byId = repository.findById(chaveSalvaMock.getId());
        assertTrue(byId.isPresent());
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloNomeCorrentista() {
        Iterable<Chave> results = repository.findAll(Specification.where(SpecificationChaveNome.nomeCorrentista("Correntista 1")));
        assertThat(results, containsInAnyOrder(chaveSalvaMock));
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloNomeCorrentista_e_TipoChave() {
        Iterable<Chave> results = repository.findAll(Specification
                .where(SpecificationChaveNome.nomeCorrentista("Correntista 1"))
                .and(SpecificationChaveTipoChave.tipoChave("CPF")
                ));
        assertThat(results, containsInAnyOrder(chaveSalvaMock));
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloTipoChave() {
        Iterable<Chave> results = repository.findAll(Specification
                .where(SpecificationChaveTipoChave.tipoChave("CPF")));
        Assertions.assertThat(results).isNotNull();
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloTipoChave_e_DesconsiderarNomeNulo() {
        Iterable<Chave> results = repository.findAll(Specification
                .where(SpecificationChaveNome.nomeCorrentista(null))
                .and(SpecificationChaveTipoChave.tipoChave("CPF")
                ));
        Assertions.assertThat(results).isNotNull();
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloNome_e_DesconsiderarTipoChaveNulo() {
        Iterable<Chave> results = repository.findAll(Specification
                .where(SpecificationChaveNome.nomeCorrentista("Correntista 1"))
                .and(SpecificationChaveTipoChave.tipoChave(null)
                ));
        assertThat(results, containsInAnyOrder(chaveSalvaMock));
    }

    @Test
    public void deveEncontrarChaveFiltrandoPeloId() {
        Iterable<Chave> results = repository.findAll(Specification
                .where(SpecificationChaveId.id(UUID.fromString("5f3ae2de-4884-41d2-8c7c-387bf5e0f18d"))
                ));
        Assertions.assertThat(results).isNotNull();
    }

}