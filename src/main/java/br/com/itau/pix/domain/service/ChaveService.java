package br.com.itau.pix.domain.service;


import br.com.itau.pix.domain.dto.IRequisicaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChaveService {

    private final ChaveRepository repository;
    private final List<IValidadorNovaChave> validador;


    public List<Chave> listarTodasChaves() {
        return repository.findAll();
    }

    public Chave inserirChave(IRequisicaoDTO requisicao) {
        validador.stream().sorted(Comparator.comparing(IValidadorNovaChave::getPrioridade)).forEach(next -> next.chain(requisicao));
        Chave chave = new Chave(requisicao);
        return repository.save(chave);
    }

    public Chave editarChave(IRequisicaoDTO dto) {
        Chave chaveOriginal = validaSeChaveExiste(dto);
        BeanUtils.copyProperties(dto, chaveOriginal, "id", "tipoChave", "valorChave");
        BeanUtils.copyProperties(chaveOriginal, dto);
        validador.stream().sorted(Comparator.comparing(IValidadorNovaChave::getPrioridade)).forEach(next -> next.chain(dto));
        return repository.save(chaveOriginal);
    }

    public Chave deletarChave(String uuid) {
        //TODO: Implementar
        return null;
    }

    private Chave validaSeChaveExiste(IRequisicaoDTO dto) {
        Optional<Chave> chaveSalva = repository.findById(dto.getId());
        if(chaveSalva.isPresent()){
            return chaveSalva.get();
        }
        throw new EmptyResultDataAccessException(1);
    }
}
