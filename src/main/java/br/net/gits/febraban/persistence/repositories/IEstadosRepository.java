package br.net.gits.febraban.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.gits.febraban.persistence.entities.Estado;

@Repository
public interface IEstadosRepository extends JpaRepository<Estado, Integer> {

	Estado findFirstByCodigoIgnoreCase(String codigo);

	List<Estado> findByNomeContainingIgnoreCase(String nome);

}
