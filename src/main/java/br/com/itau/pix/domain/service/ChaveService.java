package br.com.itau.pix.domain.service;


import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.specification.SpecificationChave;
import br.com.itau.pix.domain.validation.IValidadorAlteracaoChave;
import br.com.itau.pix.domain.validation.IValidadorDeletarChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.ChaveNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChaveService {

    private final ChaveRepository repository;
    private final List<IValidadorNovaChave> validadorNovaChave;
    private final List<IValidadorAlteracaoChave> validadorAlteracaoChave;
    private final List<IValidadorDeletarChave> validadorDeletarChave;

    public Chave inserirChave(InclusaoDTO requisicao) {
        Chave novaChave = new Chave(requisicao);
        validadorNovaChave.stream().sorted(Comparator.comparing(IValidadorNovaChave::getPrioridade)).forEach(next -> next.chain(novaChave));
        return repository.save(novaChave);
    }

    public Chave editarChave(AlteracaoDTO requisicao) {
        Chave chaveOriginal = buscarChaveNoBanco(requisicao.getId());
        atualizaValoresChaveOriginal(requisicao, chaveOriginal);
        validadorAlteracaoChave.stream().sorted(Comparator.comparing(IValidadorAlteracaoChave::getPrioridade)).forEach(next -> next.chain(chaveOriginal));
        return repository.save(chaveOriginal);
    }

    public Chave deletarChave(UUID uuid) {
        Chave chaveOriginal = buscarChaveNoBanco(uuid);
        validadorDeletarChave.stream().sorted(Comparator.comparing(IValidadorDeletarChave::getPrioridade)).forEach(next -> next.chain(chaveOriginal));
        chaveOriginal.setDataHoraInativacao(new Date());
        return repository.save(chaveOriginal);
    }

    public Chave listarPorId(UUID uuid) {
        Optional<Chave> chaveById = repository.findById(uuid);
        if (chaveById.isPresent()) {
            return chaveById.get();
        }
        throw new ChaveNaoEncontradaException(uuid);
    }

    public List<Chave> listarComFiltro(String nome, String tipoChave) {
        List<Chave> resultado = repository.findAll(Specification
                .where(SpecificationChave.nomeCorrentista(nome)).and(SpecificationChave.tipoChave(tipoChave)
                ));
        return resultado;
    }

    private Chave buscarChaveNoBanco(UUID uuid) {
        Optional<Chave> chaveSalva = repository.findById(uuid);
        if (chaveSalva.isPresent()) {
            return chaveSalva.get();
        }
        throw new ChaveNaoEncontradaException(uuid);
    }

    private void atualizaValoresChaveOriginal(AlteracaoDTO requisicao, Chave chaveOriginal) {
        BeanUtils.copyProperties(requisicao, chaveOriginal, "id", "tipoChave", "valorChave");
    }
}
