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


public final class Server extends Behavior
{
  private static final long serialVersionUID = 1L;
  private int y; //variabile in cui salvo il numero che ricevo dal client
  private BigInteger z; //variabile in cui salvo il numero di Fibonacci calcolato

  
 	public BigInteger fibonacci(int n) {
		if (n <= 0)
			return BigInteger.valueOf(0);
		if (n == 1)
			return BigInteger.valueOf(1);

		BigInteger a = BigInteger.valueOf(0);
		BigInteger b = BigInteger.valueOf(1);
		BigInteger c;

		while (n > 1) {
			c = b;
			b = a.add(b);
			a = c;
			n--;
		}
		return b;
	}

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  //System.out.println("Server avviato");
	  MessageHandler k = (m) -> {
		  System.out.println("SERVER: ricevo il messaggio dal client contenente "+ m.getContent());
	      y=(int) m.getContent();
	      //Calcolo Fibonacci
	      z=fibonacci(y);
		  System.out.println("SERVER: il numero di Fibonacci di " +y+" è "+z);
		  
		  System.out.println("SERVER: invio al client del risultato");

		  send(m.getReceiver(), z);
	      
		  return null;
	    };
	    
	    
	    MessageHandler l = (m) -> {
	      onReceive(ACCEPTALL, k);
	      return null;
	    };
	    
	    c.define(START, l);
	 
  }
}
