package br.com.itau.pix.domain.dto;

import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ChaveDTO extends BaseDTO{

    private UUID id;
    private TipoChave tipoChave;
    private String valorChave;

    public ChaveDTO(InclusaoDTO dto) {
        setValores(dto.getTipoChave(), dto.getValorChave(), dto.getTipoConta(), dto.getNumeroAgencia(), dto.getNumeroConta(), dto.getNomeCorrentista(), dto.getSobrenomeCorrentista(), dto.getTipoPessoa());
    }

    public ChaveDTO(Chave chave) {
        this.id = chave.getId();
        setValores(chave.getTipoChave(), chave.getValorChave(), chave.getTipoConta(), chave.getNumeroAgencia(), chave.getNumeroConta(), chave.getNomeCorrentista(), chave.getSobrenomeCorrentista(), chave.getTipoPessoa());
    }

    private void setValores(TipoChave tipoChave, String valorChave, TipoConta tipoConta, Integer numeroAgencia, Integer numeroConta, String numeCorrentista, String sobrenomeCorrentista, TipoPessoa tipoPessoa) {
        this.tipoChave = tipoChave;
        this.valorChave = valorChave;
        this.setTipoConta(tipoConta);
        this.setNumeroAgencia(numeroAgencia);
        this.setNumeroConta(numeroConta);
        this.setNomeCorrentista(numeCorrentista);
        this.setSobrenomeCorrentista(sobrenomeCorrentista);
        this.setTipoPessoa(tipoPessoa);
    }
}
