package br.com.itau.pix.domain.dto.resposta;

import br.com.itau.pix.domain.model.Chave;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaIdDTO {

    private UUID id;

    public static RespostaIdDTO getResponseId(Chave chave) {
        RespostaIdDTO dto = new RespostaIdDTO();
        dto.id = chave.getId();
        return dto;
    }
}
