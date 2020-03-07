package fibonacci;

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
	*  Numero di client
	*/
	private int nclient=10; //Numero di client pari a 10

	@Override
	public void build(final Executor<? extends Actor> e)
	{
		System.out.println("------------------");
		System.out.println("FIBONACCI ACTODES");
		System.out.println("------------------");
		System.out.println("Numero client="+nclient);
		for(int i=0; i<nclient; i++){ //Creazione 10 client 
			e.actor(new Client()); 
			}
		e.actor(new Server(nclient)); //Creazione server
		e.actor(new Manager(nclient)); //Creazione manager
	}

	public static void main(final String[] v)
	{
		Configuration c =  SpaceInfo.INFO.getConfiguration();
		c.addService(new Namer());
		c.setFilter(Logger.ACTIONS);
		c.setExecutor(new OldScheduler(new Initiator()));
		c.start();
	}
}


