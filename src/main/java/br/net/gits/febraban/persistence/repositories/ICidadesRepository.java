package br.net.gits.febraban.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.gits.febraban.persistence.entities.Cidade;

public interface ICidadesRepository extends JpaRepository<Cidade, Integer> {

}
