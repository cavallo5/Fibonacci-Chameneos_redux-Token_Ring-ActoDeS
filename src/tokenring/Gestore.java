package tokenring;

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
  private int nclient=3;
  
 
  int getnclient() {
  	return nclient;
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
			  System.out.println("GESTORE: ho ricevuto" + memoria_gestore.getID()+" e "+memoria_gestore.getindirizzo());
			  h.put(memoria_gestore.getID(), memoria_gestore.getindirizzo());
			  i++;
			  
			  
			  if(i==getnclient()) {
				  System.out.println("HASHTABLE RIEMPITA");
				  System.out.println("Valori nella hash " + h);	  
				  //INVIO INDIRIZZO NODO SUCCESSIVO --- CICLICO
				  for(int j=1;j<=getnclient();j++) { //j:1-2-3
					  if(j!=3) {
						  System.out.println("GESTORE: sto mandando a "+h.get(j)+" la reference del nodo successivo" +h.get(j+1));
						  send(h.get(j), h.get(j+1)); //Invio al nodo i-esimo, la reference del nodo i+1-esimo
					  }
					  else {
						  System.out.println("GESTORE: sto mandando all'ultimo nodo "+h.get(j)+" la reference del primo nodo" +h.get(1));
						  send(h.get(j), h.get(1)); //All'ultimo nodo, mando la reference del primo nodo
					  }
				  }

				  
			  }
			  
		  }
		  return null;
	  };
		  
		 
		c.define(ACCEPTALL, g);
	    
	    
  }
}
