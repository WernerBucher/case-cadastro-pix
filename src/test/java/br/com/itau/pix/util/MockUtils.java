package br.com.itau.pix.util;

import br.com.itau.pix.domain.dto.ChaveDTO;
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

    public static List<ChaveDTO> listaMockDeRequisicao(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        List<ChaveDTO> lista = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<List<ChaveDTO>>() {
                });
        return lista;
    }

    public static List<String> listaMockStrings(String pathname) throws IOException {
        try (Stream<String> linhas = Files.lines(Paths.get(pathname))) {
            return linhas.collect(Collectors.toList());
        }
    }

    public static ChaveDTO objetoMockDeRequisicao(String pathname) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        ChaveDTO objeto = objectMapper.readValue(
                new File(pathname)
                , new TypeReference<ChaveDTO>() {
                });
        return objeto;
    }
}
