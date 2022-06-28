package br.com.itau.pix.domain.specification;


import br.com.itau.pix.domain.model.Chave;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class SpecificationChaveId {

    private SpecificationChaveId() {
    }

    public static Specification<Chave> id(UUID id) {
        if (id == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

}
