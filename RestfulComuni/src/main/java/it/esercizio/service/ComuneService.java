package it.esercizio.service;

import java.util.List;

import it.esercizio.helper.Risposta;
import it.esercizio.model.Comune;

public interface ComuneService {
	Risposta registraComune(Comune comune);
	Object leggiComuneByCodiceCatastale(String codiceCatastale);
	Risposta modificaComune (String codiceCatastale, Comune nuovoComune);
	Risposta cancellaComune(String codiceCatastale);
	List<Comune> elencoComuni();
}
