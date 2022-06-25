package br.com.itau.pix.domain.model;

import br.com.itau.pix.domain.dto.ChaveDTO;
import br.com.itau.pix.domain.enums.TipoChave;
import br.com.itau.pix.domain.enums.TipoConta;
import br.com.itau.pix.domain.enums.TipoPessoa;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "chave")
@Data
@NoArgsConstructor
public class Chave{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoChave tipoChave;

    @Column(length = 77, nullable = false, unique = true)
    private String valorChave;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private TipoConta tipoConta;

    @Column(precision = 4, nullable = false)
    private Integer numeroAgencia;

    @Column(precision = 8, nullable = false)
    private Integer numeroConta;

    @Column(length = 30, nullable = false)
    private String nomeCorrentista;

    @Column(length = 45)
    private String sobrenomeCorrentista;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private TipoPessoa tipoPessoa;

    @Column(name = "dataHoraCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCadastro = new Date();

    @Column(name = "dataHoraInativacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraInativacao;

    public Chave(ChaveDTO dto) {
        this.tipoChave = dto.getTipoChave();
        this.valorChave = dto.getValorChave();
        this.tipoConta = dto.getTipoConta();
        this.numeroAgencia = dto.getNumeroAgencia();
        this.numeroConta = dto.getNumeroConta();
        this.nomeCorrentista = dto.getNomeCorrentista();
        this.sobrenomeCorrentista = dto.getSobrenomeCorrentista();
        this.tipoPessoa = dto.getTipoPessoa();
    }

    public ChaveDTO getDTO() {
        ChaveDTO dto = new ChaveDTO(this);
        return dto;
    }
}
