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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<RespostaDTO>> listaChavesComFiltro(@RequestParam(value = "filtro") String filtro){
        Iterable<Chave> chaves = chaveService.listarComFiltro(filtro);
        List<RespostaDTO> respostaList = new ArrayList<>();
        chaves.forEach(chave -> respostaList.add(RespostaBuilder.builder(chave).comData()));
        return ResponseEntity.ok(respostaList);
    }

}