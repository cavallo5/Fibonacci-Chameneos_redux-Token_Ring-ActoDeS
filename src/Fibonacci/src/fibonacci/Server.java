package fibonacci;

/**
 * Server che si occupa di calcolare il numero di Fibonacci
 *
 */
//Librerie importate
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


public final class Server extends Behavior
{
  private static final long serialVersionUID = 1L;
  private int y; //variabile in cui salvo il numero che ricevo dal client
  private BigInteger z; //variabile in cui salvo il numero di Fibonacci calcolato
  private int i; //variabile contatore
  private int nclient; //numero client che comunicheranno con il server
  
  /**
   *  Costruttore
   *  @param a variabile contenente il numero di client, passato dall'Initiator
   */
  public Server(int a) {
	  this.nclient=a; 
	  this.i=0; 
	  this.y=0;
	  this.z=null;
  }
  
 //Metodi get
  /**
   * Restituisce nclient
   * @return il numero di client
  */
  public int getnclient() {
	  return this.nclient;
  }
  /**
   * Restituisce i
   * @return la variabile contatore dei client
  */
  public int geti() {
	  return this.i;
  }
  /**
   * Restituisce y
   * @return il numero ricevuto dal client
  */
  public int gety() {
	  return this.y;
  }
  /**
   * Restituisce z
   * @return il numero di Fibonacci calcolato
  */
  public BigInteger getz(){
	  return this.z;
  }
  
  //Metodi set
  /**
   * Metodo set della variabile a
   * @param a int con il quale settare la variabile y
   */
  private void sety(int a) {
	  this.y=a;
  }
  /**
   * Metodo set della variabile z
   * @param a BigInteger con il quale settare la variabile z
   */
  private void setz(BigInteger a){
	  this.z=a;
  }
  
  /**
   * Funzione che calcola il numero di Fibonacci del parametro passato
   * @param n numero per il calcolo del numero di Fibonacci 
   * @return il numero di Fibonacci calcolato
   */
  private BigInteger fibonacci(int n) {
		if (n <= 0) 
			return BigInteger.valueOf(0); //F(0)=0
		if (n == 1)
			return BigInteger.valueOf(1); //F(1)=1

		//Se n>1
		BigInteger a = BigInteger.valueOf(0);
		BigInteger b = BigInteger.valueOf(1);
		BigInteger c;
		
		//F(n)=F(n-1)+F(n-2)
		while (n > 1) {
			c = b;
			b = a.add(b);
			a = c;
			n--;
		}
		return b;
	}

  /**
   * Comportamento del Server
  */
  @Override
  public void cases(final CaseFactory c)
  {
	  MessageHandler k = (m) -> {
		  send(APP, "SERVER"); //Il server si dichiara ai client		  
		  return null;
	  };
	  
    c.define(START, k);
	  
	  	  MessageHandler g = (m) -> {	  
		  if (m.getContent() instanceof Number) { //se arriva una variabile intera (da un client)	  
			  System.out.println("SERVER: ricevo il messaggio dal client contenente "+ m.getContent());
		      this.sety((int) m.getContent());
		      //Calcolo il numero di Fibonacci
		      this.setz(fibonacci(y));
			  System.out.println("SERVER: il numero di Fibonacci di " +this.gety()+" è "+this.getz());
			  //Invio il risultato al client
			  //System.out.println("SERVER: invio al client il risultato");
			  send(m.getSender(), z);
			  
			  this.i++; //Incremento variabile contatore dei client
			  
			  if(this.geti() == (this.getnclient())) { //Se ho servito tutti i client, server effettua lo shutdown
				  //System.out.println("SERVER: SHUTDOWN");
				  return Shutdown.SHUTDOWN;
			  }
		  }
		  return null;
	  };
		  
		c.define(ACCEPTALL, g);
	
  }
}
