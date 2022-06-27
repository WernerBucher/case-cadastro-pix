package br.com.itau.pix.domain.predicate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String campo;
    private String operacao;
    private Object valor;
}
