package br.com.itau.pix.domain.predicate;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.model.Chave;
import com.querydsl.core.types.dsl.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChavePredicate {
    private SearchCriteria criteria;

    public BooleanExpression getPredicate() {
        PathBuilder<Chave> entityPath = new PathBuilder<>(Chave.class, "chave");

        if ("numeroAgencia".equalsIgnoreCase(criteria.getCampo()) || "numeroConta".equalsIgnoreCase(criteria.getCampo())) {
            NumberPath<Integer> numberPath = entityPath.getNumber(criteria.getCampo(), Integer.class);
            return numberPath.eq(Integer.parseInt((String) criteria.getValor()));
        }

        if ("tipoChave".equalsIgnoreCase(criteria.getCampo())) {
            EnumPath<TipoChave> path2 = entityPath.getEnum(criteria.getCampo(), TipoChave.class);
            return path2.eq(TipoChave.valueOf(String.valueOf(criteria.getValor()).toUpperCase()));
        }

        if ("nomeCorrentista".equalsIgnoreCase(criteria.getCampo())) {
            StringPath path = entityPath.getString(criteria.getCampo());
            return path.containsIgnoreCase(criteria.getValor().toString());
        }

        //TODO: Gerar exception de filtro invalido
        return null;
    }
}