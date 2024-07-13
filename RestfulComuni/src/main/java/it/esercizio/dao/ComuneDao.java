package it.esercizio.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.esercizio.model.Comune;

public interface ComuneDao extends CrudRepository<Comune, Integer> {
	
	Optional<Comune> findByCodiceCatastale(String codiceCatastale);

}
