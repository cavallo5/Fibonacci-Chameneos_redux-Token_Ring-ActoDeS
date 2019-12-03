package fibonacci;

import java.math.BigInteger;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;
import fibonacci.Memoria;




public final class Client extends Behavior
{
  private static final long serialVersionUID = 1L;
  private int x;
  private BigInteger y;
  private int M;
  //private BigInteger risultato;
  Reference indirizzo_server;
  Reference indirizzo_manager;
  private static Memoria memoria;
  
  
  

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  //Invio al server di x
	  MessageHandler k = (m) -> {
		  System.out.println("Il server si dichiara ai client");
		  memoria=new Memoria(); //ogni file ha un oggetto Memoria nel quale incapsula i dati
		  return null;
	  };

	 
    c.define(START, k);

    
    MessageHandler g = (m) -> {
    	//if (m.getContent() instanceof String) {
    	if (m.getContent()=="SERVER") {
    		//Client salva indirizzo server
    		indirizzo_server=m.getSender();
    		M=100;
    	    x = (int)(Math.random()*M -1); 
    	    System.out.println("CLIENT: numero scelto da inviare al server "+x+" ");
    	    //Invio numero da calcolare
    	    memoria.setnumero(String.valueOf(x));
            send(indirizzo_server, x);
    	return null;	
    	}
    	if (m.getContent()=="MANAGER") {
    		//Client salva indirizzo manager
    		indirizzo_manager=m.getSender();
    	return null;	
    	}
  
    	if (m.getContent() instanceof BigInteger) {
            System.out.println("CLIENT: ricevo il risultato "+m.getContent());
            y=(BigInteger) m.getContent(); //BigInteger contenente il risultato 
    	    memoria.setrisultato(y.toString());
            //invio dati al manager
            send(indirizzo_manager, memoria);
            
            System.out.println("CLIENT: SHUTDOWN");
            return Shutdown.SHUTDOWN; 
    	}
        return null;
	  };
    
	    
	c.define(ACCEPTALL, g);
 
    
  }
}
