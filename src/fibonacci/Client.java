package fibonacci;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
import it.unipr.sowide.actodes.service.naming.content.Bind;
import it.unipr.sowide.actodes.service.naming.content.Lookup;
import it.unipr.sowide.actodes.service.naming.content.Subscribe;


public final class Client extends Behavior
{
  private static final long serialVersionUID = 1L;
  private int x;
  private int M;
  


  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  //Numero casuale tra 0 e M
	  M=100;
	  x = (int)(Math.random()*M -1); 
	  System.out.println("Numero scelto da inviare al server: "+x+" ");
	  
	  //Invio al server di x
	  MessageHandler k = (m) -> {
		  System.out.println("Client invia x al server");
		  send(APP, x);
		  return null;
	};
	
    c.define(START, k);
	
  }
}
