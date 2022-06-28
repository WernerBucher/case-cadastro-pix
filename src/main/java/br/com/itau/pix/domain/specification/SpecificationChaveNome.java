package br.com.itau.pix.domain.specification;


import br.com.itau.pix.domain.model.Chave;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationChaveNome {

    private SpecificationChaveNome() {
    }

    public static Specification<Chave> nomeCorrentista(String nome) {
        if (nome == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nomeCorrentista")), "%" + nome.toLowerCase() + "%"));
    }

}
