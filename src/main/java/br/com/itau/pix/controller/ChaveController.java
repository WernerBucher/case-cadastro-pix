package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.AlteracaoDTO;
import br.com.itau.pix.domain.dto.InclusaoDTO;
import br.com.itau.pix.domain.dto.resposta.RespostaBuilder;
import br.com.itau.pix.domain.dto.resposta.RespostaDTO;
import br.com.itau.pix.domain.dto.resposta.RespostaIdDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.service.ChaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chaves")
@RequiredArgsConstructor
public class ChaveController {

    private final ChaveService chaveService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RespostaDTO>> listarTodasChaves() {
        List<Chave> chaves = chaveService.listarTodasChaves();
        List<RespostaDTO> collect = chaves.stream().map(chave -> RespostaBuilder.builder(chave).comData()).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @PostMapping
    public ResponseEntity<RespostaIdDTO> inserir(@RequestBody @Valid InclusaoDTO dto) {
        Chave chave = chaveService.inserirChave(dto);
        return ResponseEntity.ok(RespostaIdDTO.getResponseId(chave));
    }

    @PutMapping
    public ResponseEntity<RespostaDTO> editar(@RequestBody @Valid AlteracaoDTO dto) {
        Chave chave = chaveService.editarChave(dto);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).comDataHora());
    }

    @DeleteMapping(value = {"{uuid}"})
    public ResponseEntity<RespostaDTO> deletar(@PathVariable(value = "uuid") UUID uuid) {
        Chave chave = chaveService.deletarChave(uuid);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).comDataHora());
    }
}