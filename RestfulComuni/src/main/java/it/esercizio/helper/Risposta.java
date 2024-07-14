package it.esercizio.helper;

//classe utilizzata per generare una risposta con relativo codice e messaggio per la verifica dell'esecuzione delle funzioni
public class Risposta {
	
	private int codice;
	private String messaggio;
	
	public Risposta(int codice, String messaggio) {
		this.codice = codice;
		this.messaggio = messaggio;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
}
