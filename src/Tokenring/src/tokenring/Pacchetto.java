package tokenring;

/**
 * Pacchetto
 *
 */
public class Pacchetto {
	/**
	  * ID del mittente del pacchetto
	 */
	private Integer ID_mittente;
	/**
	  * ID del destinatario del pacchetto
	 */
	private Integer ID_destinatario;
	/**
	  * Messaggio inviato
	 */
	private String messaggio;
	/**
	  * Token
	 */
	private Token token;
	/**
	  * Tempo iniziale
	 */
	long start_time;

	/*
	 *   Costruttore
	 *   @param a ID mittente
	 *   @param b ID destinatario
	 *   @param c Token
	 *   @param d Start_time
	*/	
	public Pacchetto(Integer a, Integer b,Token c, long d){
		this.ID_mittente=a;
		this.ID_destinatario=b;
		this.messaggio="Hello";
		this.token=c;
		this.start_time=d;
	}
	/**
	* Restituisce l'ID del mittente del pacchetto
	* @return l'ID_mittente
	*/
	public Integer getID_mittente() {
		return this.ID_mittente;
	}
	/**
	* Restituisce l'ID del destinatario del pacchetto
	* @return l'ID_destinarario
	*/
	public Integer getID_destinatario() {
		return this.ID_destinatario;
	}
	/**
	* Restituisce il messaggio incapsulato nel pacchetto
	* @return il messaggio
	*/
	public String getmessaggio() {
		return this.messaggio;
	}
	/**
	* Restituisce il token incapsulato nel pacchetto
	* @return Token
	*/
	public Token gettoken() {
		return this.token;
	}
	/**
	* Restituisce il tempo iniziale
	* @return start_time
	*/
	public long getstart_time() {
		return this.start_time;
	}
}
