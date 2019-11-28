package comunicazione;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
import it.unipr.sowide.actodes.registry.Reference;
import it.unipr.sowide.actodes.service.naming.content.Lookup;
import it.unipr.sowide.actodes.service.naming.content.Subscribe;


public final class Server extends Behavior
{
  private static final long serialVersionUID = 1L;


  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  System.out.println("Server: Hello World");
  }
}
