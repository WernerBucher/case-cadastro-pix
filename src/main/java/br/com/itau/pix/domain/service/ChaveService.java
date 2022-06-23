package br.com.itau.pix.domain.service;


import br.com.itau.pix.domain.dto.RequestDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ChaveService {

    @Autowired
    ChaveRepository repository;

    @Autowired
    private List<IValidadorNovaChave> validador;


    public List<Chave> listarTodasChaves() {
        return repository.findAll();
    }

    public Chave inserirNovaChave(RequestDTO requisicao) {
        validador.stream().sorted(Comparator.comparing(IValidadorNovaChave::getPrioridade)).forEach(next -> next.chain(requisicao));
        Chave chave = new Chave(requisicao);
        return repository.save(chave);
    }
}
