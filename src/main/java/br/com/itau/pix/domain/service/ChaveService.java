package br.com.itau.pix.domain.service;


import br.com.itau.pix.domain.dto.AlteracaoDTO;
import br.com.itau.pix.domain.dto.ChaveDTO;
import br.com.itau.pix.domain.dto.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.ChaveNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChaveService {

    private final ChaveRepository repository;
    private final List<IValidadorNovaChave> validador;


    public List<Chave> listarTodasChaves() {
        return repository.findAll();
    }

    public Chave inserirChave(InclusaoDTO requisicao) {
        ChaveDTO novaChave = new ChaveDTO(requisicao);
        validador.stream().sorted(Comparator.comparing(IValidadorNovaChave::getPrioridade)).forEach(next -> next.chain(novaChave));
        Chave chave = new Chave(novaChave);
        return repository.save(chave);
    }

    public Chave editarChave(AlteracaoDTO requisicao) {
        Chave chaveOriginal = validaSeChaveExiste(requisicao.getId());
        atualizaValoresChaveOriginal(requisicao, chaveOriginal);
        validador.stream().sorted(Comparator.comparing(IValidadorNovaChave::getPrioridade)).forEach(next -> next.chain(chaveOriginal.getDTO()));
        return repository.save(chaveOriginal);
    }

    public Chave deletarChave(UUID uuid) {
        Chave chaveOriginal = validaSeChaveExiste(uuid);
        //TODO: Dever retornar 422 caso a mesma já foi desativada
        chaveOriginal.setDataHoraInativacao(new Date());
        return repository.save(chaveOriginal);
    }

    private Chave validaSeChaveExiste(UUID uuid) {
        Optional<Chave> chaveSalva = repository.findById(uuid);
        if(chaveSalva.isPresent()){
            return chaveSalva.get();
        }
        throw new ChaveNaoEncontradaException(uuid);
    }

    private void atualizaValoresChaveOriginal(AlteracaoDTO requisicao, Chave chaveOriginal) {
        BeanUtils.copyProperties(requisicao, chaveOriginal, "id", "tipoChave", "valorChave");
    }

}
