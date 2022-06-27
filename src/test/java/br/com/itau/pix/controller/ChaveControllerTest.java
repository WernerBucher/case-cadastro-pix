package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.service.ChaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ChaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChaveService service;

    @Test
    void inserir_requisicaoComCamposNulos_deveRetornar422() throws Exception {
        InclusaoDTO dto = new InclusaoDTO();
        mockMvc.perform(post("/chaves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void inserir() {
    }

    @Test
    void editar() {
    }

    @Test
    void deletar() {
    }

    @Test
    void listaChavePoiId() {
    }

    @Test
    void listaChavesComFiltro() {
    }
}