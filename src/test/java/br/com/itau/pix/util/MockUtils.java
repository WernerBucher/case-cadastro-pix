package br.com.itau.pix.util;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockUtils {

    public static List<InclusaoDTO> carregarListaMockDeInclusao(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        List<InclusaoDTO> lista = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<List<InclusaoDTO>>() {
                });
        return lista;
    }

    public static List<AlteracaoDTO> carregarListaMockDeAlteracao(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        List<AlteracaoDTO> lista = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<List<AlteracaoDTO>>() {
                });
        return lista;
    }

    public static InclusaoDTO carregaObjetoMockDeInclucao(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        InclusaoDTO objeto = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<InclusaoDTO>() {
                });
        return objeto;
    }

    public static AlteracaoDTO carregarObjetoMockDeAlteracao(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        AlteracaoDTO objeto = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<AlteracaoDTO>() {
                });
        return objeto;
    }

    public static List<String> listaMockStrings(String pathname) throws IOException {
        try (Stream<String> linhas = Files.lines(Paths.get(pathname))) {
            return linhas.collect(Collectors.toList());
        }
    }
}
