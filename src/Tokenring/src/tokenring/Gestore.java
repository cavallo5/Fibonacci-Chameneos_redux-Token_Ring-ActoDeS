package tokenring;

/**
 * Gestore 
 * @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
 */

import tokenring.Token;
import tokenring.Memoria;
import java.util.Hashtable;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
import it.unipr.sowide.actodes.registry.Reference;


public final class Gestore extends Behavior
{
  private static final long serialVersionUID = 1L;
  /**
   * Hashtable in cui salvo le reference dei nodi
   */
  Hashtable<Integer, Reference> h = new Hashtable<Integer, Reference>();
  /**
   * Memoria del gestore
   */ 
  Memoria memoria_gestore=new Memoria();
  /**
   * Variabile contatore
   */
  int i=0; //contatore client
  /**
   * Variabile che indica il numero di nodi della token ring
   */
  private int num_nodi=500; //numero di nodi
  /**
   * Numero di token 
   */
  private int num_token=2; 
  /**
   * Numero di token assegnati
   */
  private int token_assegnati=0; 
  /**
   * Token
   */
  public Token token;
  public int w;
  
  /**
   * Restituisce il numero di nodi
   * @return num_nodi
   */
  public int getnum_nodi() {
  	return num_nodi;
  }
  /**
   * Restituisce il numero di token assegnati
   * @return token_assegnati
   */
  public int gettoken_assegnati() {
	  	return token_assegnati;
  }
  /**
   * Restituisce il numero di token 
   * @return num_token
   */  
  public int getnum_token() {
	  	return num_token;
  }
  /**
   * Funzione che genera il token e lo assegna ad un nodo della token ring seguendo l'equazione distribuita
   */  
  void GenerazioneToken() {
	  token=new Token();
	  //Scelta nodo a cui mandare il token
	  this.token_assegnati=this.token_assegnati+1;
	 
	  this.w=this.gettoken_assegnati()* this.getnum_nodi() / this.getnum_token(); //Equazione distribuita per l'assegnamento del token
	  System.out.println("GESTORE: Il token sarà assegnato al nodo "+this.w);
	  System.out.println("GESTORE: INVIO al nodo :"+h.get(this.w)+" il token");
	  send(h.get(this.w), token);
  }

  
  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  MessageHandler k = (m) -> {
		  System.out.println("GESTORE: Il gestore si dichiara ai nodi");
		  send(APP, "GESTORE");		  
		  return null;
	  };

	 
    c.define(START, k);
	  
    MessageHandler g = (m) -> {
		  
    	
		  if (m.getContent() instanceof Memoria) { //Popolo la Hashtable
			  	    
			  System.out.println("GESTORE: ricevo il messaggio dal client contenente "+ m.getContent());
			  memoria_gestore=(Memoria)m.getContent();
			  System.out.println("GESTORE: ho ricevuto " + memoria_gestore.getID()+" e "+memoria_gestore.getindirizzo());
			  h.put(memoria_gestore.getID(), memoria_gestore.getindirizzo());
			  i++;
			  
			  
			  if(i==getnum_nodi()) { //Se ho riempito l'hashtable con gli indirizzi dei 500 nodi
				  //Creazione RING
				  for(int j=1;j<=getnum_nodi();j++) { 
					  if(j!=getnum_nodi()) {
						  System.out.println("GESTORE: sto mandando a "+h.get(j)+" la reference del nodo successivo " +h.get(j+1));
						  send(h.get(j), h.get(j+1)); //Invio al nodo i-esimo, la reference del nodo i+1-esimo
					  }
					  else { //All'ultimo nodo va mandata la reference del primo nodo
						  System.out.println("GESTORE: sto mandando all'ultimo nodo "+h.get(j)+" la reference del primo nodo " +h.get(1));
						  send(h.get(j), h.get(1)); //All'ultimo nodo, mando la reference del primo nodo
					  }
				  }
				  //Dopo che la rete è stata creata, genero i token e li assegno
				  
				  for(int j=0;j<num_token;j++) {
					  GenerazioneToken();
				  }
				  return Shutdown.SHUTDOWN;
			  }
			  
		  }
		  return null;
	  }; 
		c.define(ACCEPTALL, g);   
  }
}
