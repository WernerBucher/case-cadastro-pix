package br.com.itau.pix.domain.service;


import br.com.itau.pix.domain.dto.entrada.AlteracaoDTO;
import br.com.itau.pix.domain.dto.entrada.InclusaoDTO;
import br.com.itau.pix.domain.model.Chave;
import br.com.itau.pix.domain.predicate.ChavePredicateBuilder;
import br.com.itau.pix.domain.repository.ChaveRepository;
import br.com.itau.pix.domain.validation.IValidadorAlteracaoChave;
import br.com.itau.pix.domain.validation.IValidadorDeletarChave;
import br.com.itau.pix.domain.validation.IValidadorNovaChave;
import br.com.itau.pix.exception.ChaveNaoEncontradaException;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(chaveById.isPresent()){
            return chaveById.get();
        }
        throw new ChaveNaoEncontradaException(uuid);
    }

    public Iterable<Chave> listarComFiltro(String filtro) {
        ChavePredicateBuilder builder = new ChavePredicateBuilder();

        if (filtro != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(\\w+?),");
            Matcher matcher = pattern.matcher(filtro + ",");
            while (matcher.find()) {
                String campo = matcher.group(1);
                String operacao = matcher.group(2);
                String valor = matcher.group(3);
                builder.with(campo, operacao, valor);
            }
        }
        BooleanExpression exp = builder.build();
        Iterable<Chave> all = repository.findAll(exp);
        return all;
    }

    private Chave buscarChaveNoBanco(UUID uuid) {
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
