package fibonacci;

import java.math.BigInteger;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
//import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;


public final class Client extends Behavior
{
  private static final long serialVersionUID = 1L;
  private int x;
  private int M;
  //private BigInteger risultato;

  

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  //Numero casuale tra 0 e M
	  M=100;
	  x = (int)(Math.random()*M -1); 
	  System.out.println("CLIENT: numero scelto da inviare al server "+x+" ");
	  
	  
	  //Invio al server di x
	  MessageHandler k = (m) -> {
		  System.out.println("CLIENT: invio di "+x+" al server");
		  //Invio numero da calcolare
		  send(APP, x);
		  
		  
		  return null;
	  };

	 
    c.define(START, k);

    
    MessageHandler g = (m) -> {
    	if (m.getContent() instanceof BigInteger) {
            System.out.println("CLIENT: ricevo il risultato "+m.getContent());
            System.out.println("CLIENT: SHUTDOWN");
            return Shutdown.SHUTDOWN; 
    	}
        return null;
	  };
    
	    
	c.define(ACCEPTALL, g);
 
    
  }
}
