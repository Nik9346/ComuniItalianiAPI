package it.esercizio.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.esercizio.helper.Risposta;
import it.esercizio.model.Comune;
import it.esercizio.service.ComuneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comuni")
public class ComuneController {
	@Autowired
	ComuneService comuneService;
	
	//Funzione utilizzata per la registrazione di un nuovo comune nel db
	//localhost:8080/comuni/create
	@PostMapping("/create")
	public ResponseEntity<Risposta> registrazioneComune(@Valid @RequestBody Comune comune){
		Risposta risposta = comuneService.registraComune(comune);
		return ResponseEntity.status(risposta.getCodice()).body(risposta);
	}
	
	//Funzione utilizzata per lettura del comune dal database passando come query il codice catastale
	//localhost:8080/comuni/get/{codice catastale}
	@GetMapping("/get/{codiceCatastale}")
	public ResponseEntity<Object> comuneByCodiceCatastale(@PathVariable("codiceCatastale") String codiceCatastale){
		Object risposta = comuneService.leggiComuneByCodiceCatastale(codiceCatastale);
		if(risposta instanceof Risposta)
			return ResponseEntity.status(((Risposta) risposta).getCodice()).body((Risposta)risposta);
		return ResponseEntity.status(HttpStatus.OK).body((Comune) risposta);
	}
	
	//funzione utilizzata per ottenere dal database l'elenco completo di tutti i comuni
	//localhost:8080/comuni/get
	@GetMapping("/get")
	public ResponseEntity<List<Comune>> elencoComuni(){
		return ResponseEntity.status(HttpStatus.OK).body(comuneService.elencoComuni());
	}
	
	//funzione utilizzata per modificare i dati relativi ad un comune identificato con il codice catastale
	//localhost:8080/comuni/update/{codice catastale}
	@PutMapping("/update/{codiceCatastale}")
	public ResponseEntity<Risposta> modificaDatiComune(@PathVariable("codiceCatastale") String codiceCatastale, @Valid @RequestBody Comune nuovoComune){
		Risposta risposta = comuneService.modificaComune(codiceCatastale, nuovoComune);
		return ResponseEntity.status(risposta.getCodice()).body(risposta);
	}
	//funzione utilizzata per cancellare un comune dall'elenco identificato con il codice catastale
	//localhost:8080/comuni/delete/{codice catastale}
	@DeleteMapping("/delete/{codiceCatastale}")
	public ResponseEntity<Risposta> cancellaComune(@PathVariable("codiceCatastale") String codiceCatastale){
		Risposta risposta = comuneService.cancellaComune(codiceCatastale);
		return ResponseEntity.status(risposta.getCodice()).body(risposta);
	}
	
	//metodo per intercettare eccezione di validazione dati
	@ExceptionHandler(BindException.class) //org.springframework.validation
	public ResponseEntity<Map<String, String>> gestioneEccezioneValidazione(BindException e){
		Map<String, String> errori = new HashMap<>();//si utilizza versione più efficiente in quanto non ci interessa ordinamento
		e.getBindingResult().getFieldErrors().forEach(error->errori.put(error.getField(),error.getDefaultMessage())); //per ogni errore prendiamo il campo errore e il messaggio
		return ResponseEntity.badRequest().body(errori); //settiamo la badrequest e come corpo impostiamo la mappa di errori
	}
	
	
}
