package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.service.ChaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ChaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChaveService service;

    @Mock
    private ChaveRepository repository;

    @Test
    void inserir_requisicaoValida_deveRetornar200() throws Exception {
        TipoChave tipoChave =TipoChave.CELULAR;
        String valorChave = "+5544999631530";

        Chave mockChaveNoBanco = getMockChaveNoBanco(tipoChave, valorChave, null);
        InclusaoDTO dto = getInclusaoDTO(tipoChave, valorChave);

        doReturn(mockChaveNoBanco).when(service).inserirChave(any());

        mockMvc.perform(post("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void inserir_requisicaoComCamposNulos_deveRetornar422() throws Exception {
        InclusaoDTO dto = new InclusaoDTO();
        mockMvc.perform(post("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void inserir_requisicaoInvalida_CampoValorChaveObrigatorio_deveRetornar422() throws Exception {
        InclusaoDTO dto = getInclusaoDTO(TipoChave.CELULAR, null);
        mockMvc.perform(post("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void inserir_requisicaoInvalida_DtoForaDoEsperado_deveRetornar422() throws Exception {
        String s = "{\"nome\":\"aaa\", \"numeroConta\":\"aaa\"}";
        mockMvc.perform(post("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void editar_requisicaoValida_deveRetornar200() throws Exception {
        TipoChave tipoChave =TipoChave.CELULAR;
        String valorChave = "+5544999631530";

        Chave mockChaveNoBanco = getMockChaveNoBanco(tipoChave, valorChave, null);
        AlteracaoDTO dto = getAlteracaoDTO();

        doReturn(mockChaveNoBanco).when(service).editarChave(any());

        mockMvc.perform(put("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void editar_requisicaoInvalida_NaoAtendeAsRegras_deveRetornar422() throws Exception {
        AlteracaoDTO dto = new AlteracaoDTO();

        mockMvc.perform(put("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deletar_requisicaoValida_deveRetornar200() throws Exception {
        String uuid = "19608f73-d0b2-4af1-a2f2-c19365a29ce9";

        Chave mockChaveNoBanco = getMockChaveNoBanco(TipoChave.ALEATORIO, "valorChave", null);

        doReturn(mockChaveNoBanco).when(service).deletarChave(any());

        mockMvc.perform(delete("/chaves/{id}", uuid)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_porId_deveRetornar200() throws Exception {
        String uuid = "19608f73-d0b2-4af1-a2f2-c19365a29ce9";

        Chave mockChaveNoBanco = getMockChaveNoBanco(TipoChave.ALEATORIO, "valorChave", null);

        doReturn(mockChaveNoBanco).when(service).listarPorId(any());

        mockMvc.perform(get("/chaves/{id}", uuid)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_porFiltros_nome_deveRetornar200() throws Exception {
        String nome = "Werner";

        Chave mockChaveNoBanco = getMockChaveNoBanco(TipoChave.ALEATORIO, "valorChave", null);


        doReturn(Collections.singletonList(mockChaveNoBanco)).when(service).listarComFiltro(any(), any(), any());

        mockMvc.perform(get("/chaves")
                        .param("nomeCorrentista", nome)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_porFiltros_nome_tipoChave_deveRetornar200() throws Exception {
        String nome = "Werner";

        Chave mockChaveNoBanco = getMockChaveNoBanco(TipoChave.ALEATORIO, "valorChave", null);


        doReturn(Collections.singletonList(mockChaveNoBanco)).when(service).listarComFiltro(any(), any(), any());

        mockMvc.perform(get("/chaves")
                        .param("nomeCorrentista", nome)
                        .param("tipoChave", "aleatorio")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_porFiltros_tipoChave_deveRetornar200() throws Exception {
        String nome = "Werner";

        Chave mockChaveNoBanco = getMockChaveNoBanco(TipoChave.ALEATORIO, "valorChave", null);


        doReturn(Collections.singletonList(mockChaveNoBanco)).when(service).listarComFiltro(any(), any(), any());

        mockMvc.perform(get("/chaves")
                        .param("tipoChave", "aleatorio")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    private AlteracaoDTO getAlteracaoDTO() {
        AlteracaoDTO dto = new AlteracaoDTO();
        dto.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(2);
        dto.setNumeroConta(2);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);
        return dto;
    }

    private InclusaoDTO getInclusaoDTO(TipoChave tipoChave, String valorChave) {
        InclusaoDTO dto = new InclusaoDTO();
        dto.setTipoChave(tipoChave);
        dto.setValorChave(valorChave);
        dto.setTipoConta(TipoConta.CORRENTE);
        dto.setNumeroAgencia(1);
        dto.setNumeroConta(1);
        dto.setNomeCorrentista("Werner");
        dto.setTipoPessoa(TipoPessoa.F);
        return dto;
    }

    private Chave getMockChaveNoBanco(TipoChave tipoChave, String valorChave, Date dataInativacao) {
        Chave chaveNoBanco = new Chave();
        chaveNoBanco.setId(UUID.fromString("19608f73-d0b2-4af1-a2f2-c19365a29ce9"));
        chaveNoBanco.setTipoChave(tipoChave);
        chaveNoBanco.setValorChave(valorChave);
        chaveNoBanco.setTipoConta(TipoConta.CORRENTE);
        chaveNoBanco.setNumeroAgencia(1);
        chaveNoBanco.setNumeroConta(1);
        chaveNoBanco.setNomeCorrentista("Werner");
        chaveNoBanco.setTipoPessoa(TipoPessoa.F);
        chaveNoBanco.setDataHoraInativacao(dataInativacao);
        return chaveNoBanco;
    }
}