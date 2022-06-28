package br.com.itau.pix.domain.specification;


import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.model.Chave;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationChaveTipoChave {

    private SpecificationChaveTipoChave() {
    }

    public static Specification<Chave> tipoChave(String tipo) {
        if (tipo == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tipoChave"), TipoChave.valueOf(tipo.toUpperCase())));
    }

}
