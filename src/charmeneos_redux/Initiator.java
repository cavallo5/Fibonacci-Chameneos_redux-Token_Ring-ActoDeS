package chameneos_redux;

import it.unipr.sowide.actodes.actor.Actor;
import it.unipr.sowide.actodes.configuration.Builder;
import it.unipr.sowide.actodes.configuration.Configuration;
import it.unipr.sowide.actodes.controller.SpaceInfo;
import it.unipr.sowide.actodes.executor.Executor;
import it.unipr.sowide.actodes.executor.passive.OldScheduler;
import it.unipr.sowide.actodes.service.logging.ConsoleWriter;
import it.unipr.sowide.actodes.service.logging.Logger;
import it.unipr.sowide.actodes.service.naming.Namer;


public final class Initiator extends Builder
{
	static int c=6; //numero charmeneos
	static int numeroviaggi=2; //numero viaggi di ogni charmeneos
	static Colore [] Colori = {Colore.GIALLO,Colore.BLU,Colore.ROSSO};



  /** {@inheritDoc} **/
  @Override
  public void build(final Executor<? extends Actor> e)
  {
	e.actor(new CentroCommerciale());
	//Creazione Charmeneos
	for( int i =0; i < c;i++)
	{
		e.actor(new Chameneos(new IdChameneos(i), Colori[i%3], numeroviaggi)); 
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
    //c.setLogFilter(new NoCycleProcessing());

    //c.addWriter(new ConsoleWriter());

    c.setExecutor(new OldScheduler(new Initiator()));

    c.start();
  }
}


