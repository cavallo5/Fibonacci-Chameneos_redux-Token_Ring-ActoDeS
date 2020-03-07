package chameneos_redux;

/**
 * Initiator 
 */

//Librerie importate
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
	 * Numero chameneos
	 */
	static int c=6; 
	/**
	 * Numero viaggi di ogni chameneos
	 */
	static int numeroviaggi=2; 
	/**
	 * Possibili colori di ogni chameneos usati per creare gli chameneos
	 */
	static Colore [] Colori = {Colore.GIALLO,Colore.BLU,Colore.ROSSO};



  /** {@inheritDoc} **/
  @Override
  public void build(final Executor<? extends Actor> e)
  {
	System.out.println("------------------");
	System.out.println("CHAMENEOS_REDUX ACTODES");
	System.out.println("------------------");
	e.actor(new CentroCommerciale()); 
	//Creazione c Charmeneos
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
    c.setExecutor(new OldScheduler(new Initiator()));

    c.start();
  }
}


