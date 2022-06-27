package br.com.itau.pix.domain.predicate;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ChaveQueryDslBinderCustomize<T extends EntityPath<?>> extends QuerydslBinderCustomizer<T>{

    @Override
    default void customize(QuerydslBindings bindings, T entidade) {
        bindings.bind(String.class)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));

      /*  bindings.bind(TipoChave.class)
                .first((EnumPath<TipoChave> path, TipoChave value) -> path.eq(value));*/
    }
}
