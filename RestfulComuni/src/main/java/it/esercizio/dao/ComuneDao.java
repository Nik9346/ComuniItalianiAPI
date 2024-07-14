package it.esercizio.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.esercizio.model.Comune;

public interface ComuneDao extends CrudRepository<Comune, Integer> {
	//Funzione utilizzata per verificare che il record del comune sia univoco in base al codice catastale, non possono esserci due codici catastali uguali
	Optional<Comune> findByCodiceCatastale(String codiceCatastale);

}
