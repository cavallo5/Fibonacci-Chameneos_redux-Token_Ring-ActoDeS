package chameneos_redux;

/**
 * Colore
 *
 */

public class Colore
{
	/**
	  * int che definisce il colore dello chameneos
	 */
	public int colore_charmeneox;
	/**
	  * variabile int che è associata al colore blu
	 */
	private static final int intblu = 0 ;
	/**
	  * Colore blu
	 */
	public static final Colore BLU = new Colore(intblu); 
	/**
	  * variabile int che è associata al colore rosso
	 */
	private static final int introsso = 1 ;
	/**
	  * Colore rosso
	 */
	public static final Colore ROSSO = new Colore(introsso);
	/**
	  * variabile int che è associata al colore giallo
	 */
	private static final int intgiallo = 2;
	/**
	  * Colore giallo
	 */
	public static final Colore GIALLO = new Colore(intgiallo); 
	

	/*
	 *   Costruttore
	*/
	private Colore(int valore) 
	{
		colore_charmeneox = valore %3 ;
	}
	
	/**
	  * Funzione che restituisce il colore complementare a quello passato
	  * @param C colore dello chameneos incontrato
	  * @return il colore complementare al mio e al colore in ingresso
	  */
	public Colore Complementare(Colore C) {
		if ( colore_charmeneox == C. colore_charmeneox ) //se ho incontrato uno chameneos del mio stesso colore
		{ 
			return new Colore(colore_charmeneox ); //ritorna il colore stesso
		}
		else return new Colore( 3 - colore_charmeneox - C.colore_charmeneox ); //altrimenti ritorna il complementare
	}
	
	/**
	  * Funzione che restituisce una stringa con il colore dello chameneos che invoca il metodo
	  * @return il colore 
	  */
	public String toString (){
		if ( colore_charmeneox == intblu )
		{
			return "BLU";
		}
		else if ( colore_charmeneox == introsso ) 
		{
			return "ROSSO"; 
		}
		else //intgiallo
		{
			return "GIALLO"; 
		}
	}
}
