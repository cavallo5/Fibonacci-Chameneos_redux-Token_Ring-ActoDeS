package helloworld;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.MessagePattern;
import it.unipr.sowide.actodes.actor.Shutdown;
import it.unipr.sowide.actodes.configuration.Configuration;
import it.unipr.sowide.actodes.controller.SpaceInfo;
import it.unipr.sowide.actodes.examples.fibonacci.Fibonacci;
import it.unipr.sowide.actodes.examples.simulation.PhilosopherState;
import it.unipr.sowide.actodes.executor.passive.NoCycleProcessing;
import it.unipr.sowide.actodes.executor.passive.OldScheduler;
import it.unipr.sowide.actodes.filtering.constraint.IsInstance;
import it.unipr.sowide.actodes.service.logging.ConsoleWriter;
import it.unipr.sowide.actodes.service.logging.Logger;

public final class helloworld extends Behavior {
	private static final long serialVersionUID = 1L;
	private String parola;
	
	 public helloworld(String word)
	  {
	    this.parola = word;
	  }
	 
	 public String getparola() {
		 return this.parola;
	 }




	@Override
	public void cases(CaseFactory c) {
		// TODO Auto-generated method stub
		System.out.println(this.getparola());	
	}
	
	

	public static void main(final String[] v) {
		// TODO Auto-generated method stub
		
		Configuration c =  SpaceInfo.INFO.getConfiguration();
		c.setFilter(Logger.ACTIONS);
		c.setLogFilter(new NoCycleProcessing());
		c.addWriter(new ConsoleWriter());
		c.setExecutor(new OldScheduler(new helloworld("Hello World")));
		c.start();
	}

}


