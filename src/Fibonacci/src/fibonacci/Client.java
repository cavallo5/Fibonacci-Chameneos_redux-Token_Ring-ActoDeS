package fibonacci;

/**
* Client che richiede il calcolo del numero di Fibonacci
* @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
*
*/

//Librerie importate
import java.math.BigInteger;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
import it.unipr.sowide.actodes.registry.Reference;
import fibonacci.Memoria;

public final class Client extends Behavior
{
  private static final long serialVersionUID = 1L;
  /**
   *  Variabile in cui verrà salvato il numero scelto dal client
   */
  private int x; //variabile in cui salvare il numero dal quale calcolare il numero di Fibonacci
  /**
   *  Variabile in cui verrà salvato il risultato proveniente dal server
   */
  private BigInteger y; //variabile in cui salvare il numero di Fibonacci
  /**
   *  Estremo superiore dell'intervallo dei numeri scelto
   */
  private int M; //estremo superiore dell'intervallo dei numeri scelto
  /**
   *  Reference del server
   */
  Reference indirizzo_server; //reference del server
  /**
   *  Reference del manager
   */
  Reference indirizzo_manager; //reference del manager
  /**
   *  Memoria del client
   */
  private Memoria memoria; //memoria nel quale ogni client incapsula i dati 
  /**
   *  Variabili per il calcolo dell'elapsed time
   */
  long start_time, end_time, time_used; //variabili per il calcolo dell'elapsed time
  
  /**
   *  Costruttore
   */
  public Client() {
	  memoria=new Memoria(); 
	  start_time=0; 
	  end_time=0; 
	  time_used=0;
	  M=100;
	  x=0;
  }
  
  //Metodi get
  /**
   * Restituisce x
   * @return il numero scelto per il calcolo
   */
  public int getx(){
	  return this.x;
  }
  /**
   * Restituisce y
   * @return il numero di Fibonacci ottenuto dal server
   */
  public BigInteger gety(){
	  return this.y;
  }
  /**
   * Restituisce lo start_time
   * @return il tempo iniziale
   */
  public long getstart_time(){
	  return this.start_time;
  }
  /**
   * Restituisce l'end_time
   * @return il tempo finale
   */
  public long getend_time(){
	  return this.end_time;
  }
  /**
   * Restituisce il time_used
   * @return il tempo usato
   */
  public long gettime_used(){
	  return this.time_used;
  }
  /**
   * Restituisce l'indirizzo del server
   * @return la reference dell'indirizzo del server
   */
  public Reference getindirizzo_server() {
	  return this.indirizzo_server;
  }
  /**
   * Restituisce l'indirizzo del manager
   * @return la reference dell'indirizzo del manager
   */
  public Reference getindirizzo_manager() {
	  return this.indirizzo_manager;
  }
//Metodi set
  /**
   * Metodo set della variabile x
   * @param a int con il quale settare la variabile x
   */
  private void setx(int a){
	  this.x=a;
  }
  /**
   * Metodo set della variabile y
   * @param a BigInteger con il quale settare la variabile a
   */
  private void sety(BigInteger a){
	  this.y=a;
  }
  /**
   * Metodo set della variabile start_time
   * @param a long con il quale settare la variabile start_time
   */
  private void setstart_time(long a){
	  this.start_time=a;
  }
  /**
   * Metodo set della variabile end_time
   * @param a long con il quale settare la variabile end_time
   */
  private void setend_time(long a){
	  this.end_time=a;
  }
  /**
   * Metodo set della variabile time_used
   * @param a long con il quale settare la variabile time_used
   */
  private void settime_used(long a){
	  this.time_used=a;
  }
  /**
   * Metodo set della Reference indirizzo_server
   * @param a Reference dell'indirizzo con la quale settare l'indirizzo del server
   */
  private void setindirizzo_server(Reference a) {
	  this.indirizzo_server=a;
  }
  /**
   * Metodo set della Reference indirizzo_manager
   * @param Reference dell'indirizzo con la quale settare l'indirizzo del manager
   */
  private void setindirizzo_manager(Reference a) {
	  this.indirizzo_manager=a;
  }
  
  /**
   * Comportamento del Client
   */
  @Override
  public void cases(final CaseFactory c)
  {
	  MessageHandler g = (m) -> {
    	if (m.getContent()=="SERVER") { //se arriva un messaggio con contenuto la stringa "SERVER"
    		this.setindirizzo_server(m.getSender()); //salvo la reference del server
    		//Scelta numero casuale da inviare al server
    		this.setx((int)(Math.random()*M -1)); //x compreso da 0 a 100
    	    System.out.println("CLIENT: numero scelto da inviare al server "+this.getx()+" ");
    	    this.memoria.setnumero(String.valueOf(x)); //salvo nella memoria del client il numero x scelto
    	    this.setstart_time(System.nanoTime()); //Avvio timer in ns prima dell'invio al server
            send(indirizzo_server, x); //Invio numero da calcolare
    	return null;	
    	}
    	
    	if (m.getContent()=="MANAGER") { //se arriva un messaggio con contenuto la stringa "MANAGER" 
    		this.setindirizzo_manager(m.getSender()); //client salva l'indirizzo del manager
    		return null;	
    	}
  
    	if (m.getContent() instanceof BigInteger) { //il risultato viene restituito dal server in una variabile BigInteger
            System.out.println("CLIENT: ricevo il risultato "+m.getContent());
            this.setend_time(System.nanoTime()); //tempo in ns dopo aver ricevuto la risposta del server
            
            //Calcolo elapsed time
            this.settime_used((long)((end_time - start_time)/1000F)); //tempo impiegato in 􏱇µs tra invio e risposta del server
            
            this.sety((BigInteger) m.getContent()); //variabile contenente il risultato
    	    memoria.setrisultato(y.toString()); //salvo nella memoria del client il risultato
    	    
            memoria.setelapsedtime(String.valueOf(time_used)); //salvo nella memoria del client il tempo impiegato
            

            //Invio dati al manager
    	    System.out.println("CLIENT: invio dati al manager");
            send(indirizzo_manager, memoria); //invio al manager i dati salvati in memoria
            //System.out.println("CLIENT: SHUTDOWN");
            return Shutdown.SHUTDOWN; //shutdown del client dopo aver inviato i risultati al manager
    	}
    	
        return null;
	  };
	  
	c.define(ACCEPTALL, g);
  }
}
