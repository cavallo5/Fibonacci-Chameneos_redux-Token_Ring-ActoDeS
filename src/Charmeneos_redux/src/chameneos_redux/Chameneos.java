package chameneos_redux;

/**
 * Comportamento dello chameneos 
 * @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
 */

//Librerie utilizzate
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
import it.unipr.sowide.actodes.registry.Reference;
import chameneos_redux.Colore;
import chameneos_redux.IdChameneos;


public final class Chameneos extends Behavior{
	private static final long serialVersionUID = 1L;
	/**
	 * IdChameneos per identificare gli chameneos
	 */
	private IdChameneos id;
	/**
	  * Variabili d'appoggio
	 */
	private Colore coloreA, coloreB;
	/**
	 * Intero che identifica il numero di viaggi che deve effettuare ogni chameneos
	 */
	private int numeroviaggi;
	/**
	 * Contatore dei viaggi dello chameneos 
	 */
	public int contatoreviaggi;
	/**
	 * Reference dello chameneos
	 */
	Reference indirizzochameneos;
	/**
	 * Reference dello chameneos incontrato al centro commerciale
	 */
	Reference indirizzochameneosincontrato;
	/**
	 * Reference del centro commerciale
	 */
	Reference indirizzocentrocommerciale;
	/**
	 * Variabili per il calcolo dell'elapsed time
	*/
	long start_time, end_time, time_used=0;


	/*
	 *   Costruttore
	 *   @param id identificativo dello chameneos
	 *   @param b colore dello chamaneos
	 *   @param c numero dei viaggi che effettuerà lo chameneos prima di dormire
	*/
	public Chameneos(IdChameneos id , Colore b, int c) 
	{
		this.id=id; 
		this.coloreA=b;
		this.numeroviaggi=c;
	}
	
	 /**
	  * Restituisce la reference dello chameneos incontrato
	  * @return la reference dello chameneos incontrato
	  */
	public Reference getindirizzocharmeneosincontrato() {
		return this.indirizzochameneosincontrato;
	}
	 /**
	  * Restituisce la reference del centro commerciale
	  * @return la reference del centro commerciale
	  */
	public Reference getindirizzocentrocommerciale() {
		return this.indirizzocentrocommerciale;
	}
	 /**
	  * Restituisce la propria reference
	  * @return la reference dello chameneos che ha invocato il metodo
	  */
	public Reference getindirizzocharmeneos() {
		return this.indirizzochameneos;
	}
	/**
	  * Metodo set della Reference indirizzochameneos 
	  * @param a stringa contenente la Reference
	 */
	public void setindirizzochameneos(Reference a) {
		this.indirizzochameneos=a;
	}
	
	/**
	  * Funzione che mi realizza un output leggibile
	  * @param Mess contenuto del messaggio
	 */
	private void Messaggio(String Mess) 
	{
		System.out. println ( "(" + id . toString () + ") Sono " +
		coloreA.toString () + " e " + Mess);
	}
	/**
	  * Funzione che astrae il comportamento di uno chameneos rimasto a casa
	 */
	private void Riposo()
	{
		Messaggio("sono a casa");
	}
	/**
	  * Funzione che astrae il comportamento di uno chameneos che va al centro commerciale
	 */
	private void Vadoalcentrocommerciale() 
	{
		Messaggio("vado nel centro commerciale");
	}
	/**
	  * Funzione che astrae il comportamento di uno chameneos che effettua la mutazione dopo aver incontrato uno chameneos
	 */
	private void Mutazione()
	{
		Messaggio("sto avendo una mutazione dopo aver incontrato un'altra creatura di colore "+coloreB);
		this.coloreA = coloreA.Complementare(coloreB);
		Messaggio("torno a casa dopo aver fatto la mutazione");
	}
	
	
	/** {@inheritDoc} **/
	 @Override
	 public void cases(final CaseFactory c)
	 {
		  MessageHandler k = (m) -> {
			  setindirizzochameneos(this.getReference()); //settaggio del proprio indirizzo
			  this.contatoreviaggi=0; //azzera la variabile contatore
			  Riposo(); //Riposo a casa
			  return null;
		  };

        start_time=System.nanoTime(); //tempo in ns
	    c.define(START, k);

	    MessageHandler g = (m) -> {
	    	if (m.getContent()=="CENTROCOMMERCIALE_APERTO") { //messaggio di inizializzazione dal centrocommerciale
	    		this.indirizzocentrocommerciale=m.getSender(); //salvo la reference del centro commerciale
	    		Vadoalcentrocommerciale(); 
				send(this.indirizzocentrocommerciale, coloreA);	//lo chameneos si reca al centro commerciale
	    	}
	    	
	    	if (m.getContent() instanceof Reference && m.getSender()==indirizzocentrocommerciale){ //incontro tra 2 chameneos
	    		indirizzochameneosincontrato=(Reference) m.getContent(); //salvo la reference dello chameneos incontrato
	    		send(indirizzochameneosincontrato,coloreA); //invio allo chameneos incontrato il mio colore
	    	}
	    		
	    	if (m.getContent() instanceof Colore && m.getSender()==indirizzochameneosincontrato){ //se mi arriva il colore dallo chamoneos incontrato nel centro commerciale
	    		this.coloreB=(Colore)m.getContent(); //salvo il suo colore in coloreB
	    		Mutazione(); //Effettuo la mutazione
	    		this.contatoreviaggi++; //incremento la variabile contatore dei viaggi
	    		Riposo();
	    		if(contatoreviaggi<numeroviaggi) { //Se devo effettuare altri viaggi
		    		Vadoalcentrocommerciale();
					send(this.indirizzocentrocommerciale, coloreA);	//lo chameneos si reca al centro commerciale
	    		}else { //lo chameneos ha terminato i viaggi e rimane a casa
	    			Messaggio("ho terminato i miei viaggi"); 
	                end_time= System.nanoTime(); //tempo finale in ns 
	                time_used= (long) ((end_time - start_time)/1000F); //tempo impiegato in 􏱇µs 
	                Messaggio("ho impiegato: "+time_used+" ns");
	    			return Shutdown.SHUTDOWN;
	    		}
	    	}
	    	return null;
	    };
	    
		c.define(ACCEPTALL, g); 
	  }
}
