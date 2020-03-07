package tokenring;

/**
 * Memoria
 *
 */
import it.unipr.sowide.actodes.registry.Reference;

public class Memoria {
	/**
	  * Integer che definisce l'ID del nodo
	 */
	Integer ID;
	/**
	  * Reference del nodo
	 */
	Reference indirizzo;
	
	/*
	 *   Costruttore
	*/
	public Memoria(){
		this.ID=0;
	}
	/**
	* Restituisce l'ID del nodo
	* @return l'ID
	*/
	public int getID() {
		return this.ID;
	}
	/**
	* Restituisce la Reference del nodo
	* @return la Reference
	*/	
	public Reference getindirizzo() {
		return this.indirizzo;
	}
	 /**
     * Metodo set della variabile ID
     * @param a Integer con il quale settare l'ID  
     */
	public void setID(Integer a) {
		this.ID=a;
	}
	 /**
     * Metodo set della variabile indirizzo
     * @param a Reference con la quale settare l'indirizzo
     */
	public void setindirizzo(Reference a) {
		this.indirizzo=a;
	}

}
