package br.com.itau.pix.domain.service;


import br.com.itau.pix.domain.dto.ConsultaDTO;
import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.specification.SpecificationChave;
import br.com.itau.pix.domain.validation.IValidadorChaveAlterar;
import br.com.itau.pix.domain.validation.IValidadorChaveDeletar;
import br.com.itau.pix.domain.validation.IValidadorChaveInserir;
import br.com.itau.pix.domain.validation.IValidadorConsulta;
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
    private final List<IValidadorChaveInserir> validadorNovaChave;
    private final List<IValidadorChaveAlterar> validadorAlteracaoChave;
    private final List<IValidadorChaveDeletar> validadorDeletarChave;

    private final List<IValidadorConsulta> validadorConsultarChave;

    public Chave inserirChave(InclusaoDTO requisicao) {
        Chave novaChave = new Chave(requisicao);
        validadorNovaChave.stream().sorted(Comparator.comparing(IValidadorChaveInserir::getPrioridade)).forEach(next -> next.chain(novaChave));
        return repository.save(novaChave);
    }

    public Chave editarChave(AlteracaoDTO requisicao) {
        Chave chaveOriginal = buscarChaveNoBanco(requisicao.getId());
        atualizaValoresChaveOriginal(requisicao, chaveOriginal);
        validadorAlteracaoChave.stream().sorted(Comparator.comparing(IValidadorChaveAlterar::getPrioridade)).forEach(next -> next.chain(chaveOriginal));
        return repository.save(chaveOriginal);
    }

    public Chave deletarChave(UUID uuid) {
        Chave chaveOriginal = buscarChaveNoBanco(uuid);
        validadorDeletarChave.stream().sorted(Comparator.comparing(IValidadorChaveDeletar::getPrioridade)).forEach(next -> next.chain(chaveOriginal));
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

    public List<Chave> listarComFiltro(UUID id, String nome, String tipoChave) {
        ConsultaDTO dto = new ConsultaDTO(id, nome, tipoChave);
        validadorConsultarChave.stream().sorted(Comparator.comparing(IValidadorConsulta::getPrioridade)).forEach(next -> next.chain(dto));
        return repository.findAll(Specification
                .where(SpecificationChave.id(id)).or(SpecificationChave.nomeCorrentista(nome)).and(SpecificationChave.tipoChave(tipoChave)));
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
