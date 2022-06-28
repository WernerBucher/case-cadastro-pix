package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.dto.resposta.RespostaBuilder;
import br.com.itau.pix.domain.dto.resposta.RespostaComDataDTO;
import br.com.itau.pix.domain.dto.resposta.RespostaComDataHoraDTO;
import br.com.itau.pix.domain.dto.resposta.RespostaIdDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.service.ChaveService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Inserir uma nova Chave")
    @PostMapping
    public ResponseEntity<RespostaIdDTO> inserir(@RequestBody @Valid InclusaoDTO dto) {
        Chave chave = chaveService.inserirChave(dto);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).somenteId());
    }

    @ApiOperation(value = "Editar uma nova Chave existente")
    @PutMapping
    public ResponseEntity<RespostaComDataHoraDTO> editar(@RequestBody @Valid AlteracaoDTO dto) {
        Chave chave = chaveService.editarChave(dto);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).completaComDataHora());
    }

    @ApiOperation(value = "Inativar uma nova Chave existente")
    @DeleteMapping(value = {"{uuid}"})
    public ResponseEntity<RespostaComDataHoraDTO> deletar(@PathVariable(value = "uuid") UUID uuid) {
        Chave chave = chaveService.deletarChave(uuid);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).completaComDataHora());
    }

    @ApiOperation(value = "Consultar uma nova Chave por Id")
    @GetMapping(value = "{uuid}")
    public ResponseEntity<RespostaComDataDTO> listaChavePoiId(@PathVariable(value = "uuid") UUID uuid) {
        Chave chave = chaveService.listarPorId(uuid);
        return ResponseEntity.ok(RespostaBuilder.builder(chave).completaComData());
    }

    @ApiOperation(value = "Consultar de chaves, com filtros")
    @GetMapping()
    public ResponseEntity<List<RespostaComDataDTO>> listaChavesComFiltro(@RequestParam(value = "id", required = false) UUID id, @RequestParam(value = "nomeCorrentista", required = false) String nome, @RequestParam(value = "tipoChave", required = false) String tipoChave){
        List<Chave> chaves = chaveService.listarComFiltro(id, nome, tipoChave);
        List<RespostaComDataDTO> resposta = chaves.stream().map(chave -> RespostaBuilder.builder(chave).completaComData()).collect(Collectors.toList());
        return resposta.isEmpty()?ResponseEntity.notFound().build():ResponseEntity.ok(resposta);
    }

}