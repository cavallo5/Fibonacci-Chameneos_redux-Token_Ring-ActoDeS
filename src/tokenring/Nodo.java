package tokenring;

import java.math.BigInteger;

import tokenring.Memoria;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;




public final class Nodo extends Behavior
{
  private static final long serialVersionUID = 1L;
  //int ID;
  //Reference indirizzo;
  Reference indirizzo_nodosuccessivo;
  Reference indirizzo_gestore;
  private Memoria memoria; //contiene ID e indirizzo
  
  
  
  public Nodo(Integer i)
  {
	  memoria=new Memoria(); //ogni file ha un oggetto Memoria nel quale incapsula i dati
	  this.memoria.ID=i;
  }


  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  
	  //SE I CLIENT DEVONO FARE QUALCOSA ALL'INIZIO, SCRIVERE QUI:
	  
	  MessageHandler k = (m) -> {
			this.memoria.setindirizzo(this.getReference());
			System.out.println("NODO: Indirizzo nodo: "+this.memoria.getID()+" : "+this.memoria.getindirizzo()+"");

			return null;
	  };

	 
    c.define(START, k);
	
    
    MessageHandler g = (m) -> {
    	//Arriva messaggio di inizializzazione dal gestore
    	if (m.getContent()=="GESTORE") {
    		this.indirizzo_gestore=m.getSender();
			System.out.println("NODO: Indirizzo gestore: "+this.indirizzo_gestore+"");
			
			//mando al Gestore il mio ID e il mio indirizzo
			 send(this.indirizzo_gestore, memoria);		  
		
			 return null;	
    	}
  
        return null;
	  };
    
	    
	c.define(ACCEPTALL, g);
 	
    
  }
}
