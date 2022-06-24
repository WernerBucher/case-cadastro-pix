package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.dto.ResponseDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.service.ChaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chaves")
@RequiredArgsConstructor
public class ChaveController {

    private final ChaveService chaveService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ResponseDTO>> listarTodasChaves() {
        List<Chave> chaves = chaveService.listarTodasChaves();
        List<ResponseDTO> collect = chaves.stream().map(ResponseDTO::getResponseAll).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> inserir (@RequestBody @Valid RequestDTO requisicao){
        Chave chave = chaveService.inserirNovaChave(requisicao);
        return ResponseEntity.ok(ResponseDTO.getResponseId(chave));
    }
}