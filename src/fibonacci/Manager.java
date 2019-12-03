package fibonacci;

import java.math.BigInteger;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
//import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;
import it.unipr.sowide.actodes.actor.Shutdown;
import fibonacci.Memoria;
import fibonacci.Client;



public final class Manager extends Behavior
{
  private static final long serialVersionUID = 1L;
  private int nclient=10;

    int getnclient() {
    	return nclient;
    }
    

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  
	  
	  //Invio al server di x
	  MessageHandler k = (m) -> {
		  System.out.println("Il master si dichiara ai client");
		  send(APP, "MANAGER");		  
		  return null;
	  };

	 
    c.define(START, k);

	MessageHandler g = (m) -> {
		
		  if (m.getContent() instanceof Memoria) {		  
			 System.out.println("MANAGER: BINGO");
		  }
		  return null;
	  };
		  
	  c.define(ACCEPTALL, g);   
	    
  }
}
