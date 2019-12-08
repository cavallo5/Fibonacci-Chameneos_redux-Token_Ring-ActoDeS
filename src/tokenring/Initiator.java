package tokenring;

import it.unipr.sowide.actodes.actor.Actor;
import it.unipr.sowide.actodes.configuration.Builder;
import it.unipr.sowide.actodes.configuration.Configuration;
import it.unipr.sowide.actodes.controller.SpaceInfo;
import it.unipr.sowide.actodes.executor.Executor;
import it.unipr.sowide.actodes.executor.passive.OldScheduler;
import it.unipr.sowide.actodes.service.logging.ConsoleWriter;
import it.unipr.sowide.actodes.service.logging.Logger;
import it.unipr.sowide.actodes.service.naming.Namer;

/**
 *
 * The {@code Initiator} class defines an application that describes
 * the interaction between a client and a server.
 *
 * This interaction is synchronized by the naming service.
 *
 * @see Nodo
 * @see Gestore
 *
**/

public final class Initiator extends Builder
{
 
  /** {@inheritDoc} **/
  @Override
  public void build(final Executor<? extends Actor> e)
  {
	//Mettere quello che voglio creare all'inizio, suppongo la Token Ring
	  	e.actor(new Gestore());
	    e.actor(new Nodo(1)); 
	    e.actor(new Nodo(2));
	    e.actor(new Nodo(3));
	
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
    //c.setLogFilter(new NoCycleProcessing());

    c.addWriter(new ConsoleWriter());

    c.setExecutor(new OldScheduler(new Initiator()));

    c.start();
  }
}


