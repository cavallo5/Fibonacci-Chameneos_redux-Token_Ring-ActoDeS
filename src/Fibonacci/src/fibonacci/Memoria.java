package fibonacci;

/**
 * Memoria per incapsulare i dati
 *
 */

public class Memoria {
	 /**
	  * Stringa in cui salvo il numero scelto dal client
	 */
	String numero;
	 /**
	  * Stringa in cui salvo il risultato
	 */
	String risultato;
	 /**
	  * Stringa in cui salvo l'elapsed time
	 */
	String elapsedtime;
	
	/*
	 *   Costruttore
	*/
	public Memoria(){ 
		this.numero="";
		this.risultato="";
		this.elapsedtime= "";
	}
	
	//Metodi get
	 /**
	  * Restituisce numero
	  * @return la stringa del numero
	  */
	public String getnumero(){
		return this.numero;
	}
	 /**
	  * Restituisce risultato
	  * @return la stringa del risultato
	  */
	public String getrisultato() {
		return this.risultato;
	}
	 /**
	  * Restituisce elapsedtime
	  * @return la stringa dell'elapsed time
	  */
	public String getelapsedtime() {
		return this.elapsedtime;
	}
	
	//Metodi set
	 /**
	  * Metodo set della variabile numero
	  * @param a stringa contenente il numero
	  */
	public void setnumero(String a) {
		this.numero=a;
	}
	 /**
	  * Metodo set della variabile risultato
	  * @param a stringa contenente il risultato
	  */
	public void setrisultato(String a) {
		this.risultato=a;
	}
	 /**
	  * Metodo set della variabile elapsed time
	  * @param a stringa contenente l'elapsed time
	  */
	public void setelapsedtime(String a) {
		this.elapsedtime=a;
	}

}