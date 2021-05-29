package br.net.gits.febraban.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.entities.Estado;

public interface ICidadesRepository extends JpaRepository<Cidade, Integer> {
	
	List<Cidade> findByEstado(Estado estado);

	List<Cidade> findByNomeContainingIgnoreCase(String nome);
}
