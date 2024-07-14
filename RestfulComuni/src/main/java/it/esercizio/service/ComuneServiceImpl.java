package it.esercizio.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import it.esercizio.dao.ComuneDao;
import it.esercizio.helper.Risposta;
import it.esercizio.model.Comune;

@Service
public class ComuneServiceImpl implements ComuneService {

	//Colleghiamo il Dao che ci permette di fare le query necessarie al db
	@Autowired
	private ComuneDao comuneDao;

	@Override
	public Risposta registraComune(Comune comune) {
		if (comuneDao.findByCodiceCatastale(comune.getCodiceCatastale()).isEmpty()) {  //se non viene trovato nessun comune con lo stesso codice catastale si prosegue la registrazione
			try {
				comuneDao.save(comune);
				return new Risposta(201, "Comune registrato correttamente");
			} catch (DataIntegrityViolationException e) {
				return new Risposta(400, e.getMessage());
			}
		}
		//altrimenti viene lanciato un messaggio di risposta
		return new Risposta(409, "Comune già presente");
	}
	
	@Override
	public Object leggiComuneByCodiceCatastale(String codiceCatastale) {
		Optional<Comune> comuneOptional = comuneDao.findByCodiceCatastale(codiceCatastale);
		//se il comune non è presente nel database viene lanciato questo messaggio
		if (!comuneOptional.isPresent())
			return new Risposta(404, "Comune non trovato");
		//altrimenti ritorniamo il comune trovato
		return comuneOptional.get();
	}
	
	@Override
	public Risposta modificaComune(String codiceCatastale, Comune nuovoComune) {
		Optional<Comune> comuneOptional = comuneDao.findByCodiceCatastale(codiceCatastale);
		//Se il comune non è presente nel database viene lanciato questo messaggio
		if (!comuneOptional.isPresent())
			return new Risposta(404, "Comune non trovato");
		//altrimenti viene preso il comune trovato
		Comune comune = comuneOptional.get();
		//vengono copiate le proprietà tranne quelle passate tra le parentesi
		BeanUtils.copyProperties(nuovoComune, comune, "id", "nome", "codiceCatastale", "coordinate");
		//viene salvato il nuovo oggetto comune con le proprietà modificate
		try {
			comuneDao.save(comune);
			return new Risposta(202, "Dati Comune aggiornati");

		} catch (DataIntegrityViolationException e) {
			return new Risposta(400, e.getMessage());
		}
	}
	
	@Override
	public Risposta cancellaComune(String codiceCatastale) {
		Optional<Comune> comueOptional = comuneDao.findByCodiceCatastale(codiceCatastale);
		//se il comune non è presente nel database viene lanciato il messaggio di errore
		if (!comueOptional.isPresent())
			return new Risposta(404, "Comune non trovato");
		//altrimenti se viene trovato, viene lanciata la funzione che cancella il comune dal database
		comuneDao.delete(comueOptional.get());
		return new Risposta(202, "Comune Eliminato con successo");
	}
	
	@Override
	//Funzione che ci ritorna la lista completa dei comuni in elenco
	public List<Comune> elencoComuni() {
		List<Comune> comuni = (List<Comune>) comuneDao.findAll();
		//con il comparator confrontiamo i nomi dei comuni e li ordiniamo
		Comparator<Comune> comuniByNome = Comparator.comparing(Comune::getNome); //se passiamo .reversed invertiamo l'ordine A-Z Z-A
		comuni = comuni.stream().sorted(comuniByNome).toList();
		return comuni;
	}
}
