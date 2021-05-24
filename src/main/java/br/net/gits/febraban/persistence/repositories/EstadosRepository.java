package br.net.gits.febraban.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.gits.febraban.persistence.entities.Estado;

@Repository
public interface EstadosRepository extends JpaRepository<Estado, Integer> {

	Optional<Estado> findByCodigo(String codigo);

}
