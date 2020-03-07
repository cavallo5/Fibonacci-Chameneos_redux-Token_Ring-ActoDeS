package tokenring;

/**
 * Initiator 
 */

import it.unipr.sowide.actodes.actor.Actor;
import it.unipr.sowide.actodes.configuration.Builder;
import it.unipr.sowide.actodes.configuration.Configuration;
import it.unipr.sowide.actodes.controller.SpaceInfo;
import it.unipr.sowide.actodes.executor.Executor;
import it.unipr.sowide.actodes.executor.passive.OldScheduler;
import it.unipr.sowide.actodes.service.logging.Logger;
import it.unipr.sowide.actodes.service.naming.Namer;

public final class Initiator extends Builder
{
	/**
	 * Numero nodi della token ring
	 */
	int numeronodi=500;
 
  /** {@inheritDoc} **/
  @Override
  public void build(final Executor<? extends Actor> e)
  {
	  	e.actor(new Gestore());	  	
	  	for(int i=1;i<=numeronodi;i++) { //Creazione dei 500 nodi
	  		e.actor(new Nodo(i));
	  	}
  }

  /**
   * Starts an actor space running the virtual actors example.
   *
   * @param v  the arguments.
   *
   * It does not need arguments.
   *
  **/
  public static void main(final String[] v)
  {

    Configuration c =  SpaceInfo.INFO.getConfiguration();
    c.addService(new Namer());
    c.setFilter(Logger.ACTIONS);
    c.setExecutor(new OldScheduler(new Initiator()));
    c.start();
  }
}


