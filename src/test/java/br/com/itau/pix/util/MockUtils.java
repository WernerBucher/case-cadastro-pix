package br.com.itau.pix.util;

import br.com.itau.pix.domain.dto.InclusaoDTO;
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

    public static List<InclusaoDTO> listaMockDeRequisicaoDeInsert(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        List<InclusaoDTO> lista = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<List<InclusaoDTO>>() {
                });
        return lista;
    }

    public static List<String> listaMockStrings(String pathname) throws IOException {
        try (Stream<String> linhas = Files.lines(Paths.get(pathname))) {
            return linhas.collect(Collectors.toList());
        }
    }

    public static InclusaoDTO objetoMockDeRequisicaoDeInsert(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        InclusaoDTO objeto = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<InclusaoDTO>() {
                });
        return objeto;
    }
}
