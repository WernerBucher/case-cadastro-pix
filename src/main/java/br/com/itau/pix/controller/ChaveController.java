package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
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

    @GetMapping(value = "{uuid}")
    public ResponseEntity<RespostaDTO> listaChavePoiId(@PathVariable(value = "uuid") UUID uuid) {
        Chave chave = chaveService.listarPorId(uuid);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).comData());
    }

    @GetMapping()
    public ResponseEntity<List<RespostaDTO>> listaChavesComFiltro(@RequestParam(value = "nomeCorrentista", required = false) String nome, @RequestParam(value = "tipoChave", required = false) String tipoChave){
        List<Chave> chaves = chaveService.listarComFiltro(nome, tipoChave);
        List<RespostaDTO> resposta = chaves.stream().map(chave -> RespostaBuilder.builder(chave).comData()).collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

}