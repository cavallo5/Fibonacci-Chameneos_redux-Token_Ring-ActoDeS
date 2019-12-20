package tokenring;

import tokenring.Token;
import tokenring.Memoria;
import java.util.Hashtable;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;


public final class Gestore extends Behavior
{
  private static final long serialVersionUID = 1L;
  Hashtable<Integer, Reference> h = new Hashtable<Integer, Reference>();
  Memoria memoria_gestore=new Memoria();
  int i=0; //contatore client
  private int num_nodi=500; //numero di nodi
  private int num_token=10; //numero di token
  private int token_assegnati=0; //token assegnati
  public Token token;
  public int w;
  
 
  int getnum_nodi() {
  	return num_nodi;
  }
  
  int gettoken_assegnati() {
	  	return token_assegnati;
  }
  
  int getnum_token() {
	  	return num_token;
  }
  
  void GenerazioneToken() {
	  token=new Token();
	  //SCELTA NODO A CUI MANDARE IL TOKEN
	  this.token_assegnati=this.token_assegnati+1;
	 
	  this.w=this.gettoken_assegnati()* this.getnum_nodi() / this.getnum_token();
	  System.out.println("GESTORE: Il token sarà assegnato al nodo "+this.w);
	  System.out.println("GESTORE: INVIO al nodo :"+h.get(this.w)+" il token");
	  send(h.get(this.w), token);

	  
	  
	  
  }

  
  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  
	  
	  //Invio al server di x
	  MessageHandler k = (m) -> {
		  System.out.println("GESTORE: Il gestore si dichiara ai nodi");
		  send(APP, "GESTORE");		  
		  return null;
	  };

	 
    c.define(START, k);
	  
	  
	  //System.out.println("Server avviato");
	
    MessageHandler g = (m) -> {
		  
    	
		  if (m.getContent() instanceof Memoria) { //POPOLO LA Hashtable
			  	    
			  //System.out.println(m.getSender());
			  System.out.println("GESTORE: ricevo il messaggio dal client contenente "+ m.getContent());
			  memoria_gestore=(Memoria)m.getContent();
			  System.out.println("GESTORE: ho ricevuto " + memoria_gestore.getID()+" e "+memoria_gestore.getindirizzo());
			  h.put(memoria_gestore.getID(), memoria_gestore.getindirizzo());
			  i++;
			  
			  
			  if(i==getnum_nodi()) {
				  System.out.println("HASHTABLE RIEMPITA");
				  System.out.println("Valori nella hash " + h);	  
				  //INVIO INDIRIZZO NODO SUCCESSIVO --- CICLICO
				  for(int j=1;j<=getnum_nodi();j++) { //j:1-2-3-...30
					  if(j!=getnum_nodi()) {
						  System.out.println("GESTORE: sto mandando a "+h.get(j)+" la reference del nodo successivo " +h.get(j+1));
						  send(h.get(j), h.get(j+1)); //Invio al nodo i-esimo, la reference del nodo i+1-esimo
					  }
					  else {
						  System.out.println("GESTORE: sto mandando all'ultimo nodo "+h.get(j)+" la reference del primo nodo " +h.get(1));
						  send(h.get(j), h.get(1)); //All'ultimo nodo, mando la reference del primo nodo
					  }
				  }
				  //Dopo che la rete è stata creata, genero i token
				  
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
