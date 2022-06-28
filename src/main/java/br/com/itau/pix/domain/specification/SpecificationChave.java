package br.com.itau.pix.domain.specification;


import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.model.Chave;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class SpecificationChave {

    private SpecificationChave() {
    }

    public static Specification<Chave> id(UUID id) {
        if (id == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

    public static Specification<Chave> tipoChave(String tipo) {
        if (tipo == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tipoChave"), TipoChave.valueOf(tipo.toUpperCase())));
    }

    public static Specification<Chave> nomeCorrentista(String nome) {
        if (nome == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nomeCorrentista")), "%" + nome.toLowerCase() + "%"));
    }

}
