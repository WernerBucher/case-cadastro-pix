package br.com.itau.pix.domain.repository;

import br.com.itau.pix.domain.enums.TipoPessoa;
import br.com.itau.pix.domain.model.Chave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChaveRepository extends JpaRepository<Chave, UUID> {

    Optional<Chave> findByValorChave(String chave);
    List<Chave> findAllByNumeroAgenciaAndNumeroContaAndTipoPessoa(Integer numeroAgencia, Integer numeroConta, TipoPessoa tipoPessoa);
}
