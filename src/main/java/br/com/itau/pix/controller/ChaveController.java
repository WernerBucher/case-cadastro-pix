package br.com.itau.pix.controller;

import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.service.ChaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chaves")
public class ChaveController {

    @Autowired
    ChaveService chaveService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Chave> listarTodasChaves() {
        return chaveService.listarTodasChaves();
    }

    @PostMapping
    public ResponseEntity<Chave> inserir (@RequestBody @Valid RequestDTO requisicao){
        Chave chave = chaveService.inserirNovaChave(requisicao);
        return ResponseEntity.ok(chave);
    }

}
