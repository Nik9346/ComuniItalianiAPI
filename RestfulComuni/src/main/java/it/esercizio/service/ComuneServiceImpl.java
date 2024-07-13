package it.esercizio.service;

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

	@Autowired
	private ComuneDao comuneDao;

	@Override
	public Risposta registraComune(Comune comune) {
		if (comuneDao.findByCodiceCatastale(comune.getCodiceCatastale()).isEmpty()) {
			try {
				comuneDao.save(comune);
				return new Risposta(201, "Comune registrato correttamente");
			} catch (DataIntegrityViolationException e) {
				return new Risposta(400, e.getMessage());
			}
		}
		return new Risposta(409, "Comune già presente");
	}
	
	@Override
	public Object leggiComuneByCodiceCatastale(String codiceCatastale) {
		Optional<Comune> comuneOptional = comuneDao.findByCodiceCatastale(codiceCatastale); // da controllare
		if (!comuneOptional.isPresent())
			return new Risposta(404, "Comune non trovato");
		return comuneOptional.get();
	}
	
	@Override
	public Risposta modificaComune(String codiceCatastale, Comune nuovoComune) {
		Optional<Comune> comuneOptional = comuneDao.findByCodiceCatastale(codiceCatastale);

		if (!comuneOptional.isPresent())
			return new Risposta(404, "Comune non trovato");
		Comune comune = comuneOptional.get();

		BeanUtils.copyProperties(nuovoComune, comune, "id", "nome", "codiceCatastale", "coordinate");

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
		if (!comueOptional.isPresent())
			return new Risposta(404, "Comune non trovato");
		comuneDao.delete(comueOptional.get());
		return new Risposta(202, "Comune Eliminato con successo");
	}
	
	@Override
	public List<Comune> elencoComuni() {
		return (List<Comune>) comuneDao.findAll();
	}
}
