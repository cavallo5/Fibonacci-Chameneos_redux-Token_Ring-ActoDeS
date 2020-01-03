package fibonacci;

/**
 * Manager che si occupa di generare il file di output
 * @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
 *
 */

//Librerie importate
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
import fibonacci.Memoria;


public final class Manager extends Behavior
{
  private static final long serialVersionUID = 1L;
  /**
   *  Numero dei client
   */
  private int nclient; //numero di client
  /**
   *  Variabile per generare file di output
   */
  FileWriter w; 
  /**
   *  Array di stringhe in cui vengono salvati i numeri scelti dai client
   */
  String[] numeri; //vengono salvati i numeri per ogni client
  /**
   *  Array di stringhe in cui vengono salvati i risultati ottenuti dall'algoritmo di Fibonacci
   */
  String[] risultati; //vengono salvati i risultati per ogni client
  /**
   *  Array di stringhe in cui vengono salvati gli elapsed time di ogni client
   */
  String[] elapsedtime; //vengono salvati gli elapsedtime di ogni client
  /**
   *  Variabile contatore
   */
  int contatoreclient; //variabile contatore
  /**
   *  Memoria del manager
   */
  Memoria memoria_manager; //memoria del manager
  /**
   *  Variabile usata per calcolare il tempo totale impiegato (somma di tutti gli elapsed time)
   */
  long tempototale; //variabile aggiuntiva, usata per calcolare il tempo totale

  /**
   *  Costruttore
   *  @param a numero dei client
   */
  public Manager(int a) {
	this.nclient=a;
	this.numeri=new String[nclient];
	this.risultati=new String[nclient];
	this.elapsedtime = new String[nclient];
	this.setcontatoreclient(1);
	this.memoria_manager=new Memoria();
	this.settempototale(0);
  }
  
  //Metodi get
  /**
   * Restituisce il numero client
   * @return il numero di client
   */
    public int getnclient() {
    	return nclient;
    }
    /**
     * Restituisce il contatore client
     * @return il contatore dei client
     */
    public int getcontatoreclient() {
    	return this.contatoreclient;
    }
    /**
     * Metodo set della variabile contatoreclient
     * @param a int con il quale settare il contatoreclient  
     */
    private void setcontatoreclient(int a) {
    	this.contatoreclient=a;
    }
    /**
     * Metodo set della variabile contatoreclient
     * @param a int con il quale settare il tempo totale
     */
    private void settempototale(int a) {
    	this.tempototale=a;
    }
    /**
     * Funzione che crea il file di output output_Fibonacci.txt
     * @throws IOException eccezione in caso di errore della scrittura del file
     */
    public void creafile() throws IOException{
		w=new FileWriter("output_Fibonacci.txt"); //Nome del file txt di output
		BufferedWriter b;
        b=new BufferedWriter (w);
        b.write("Fibonacci ActoDeS \n");
        for(int i=0;i<nclient;i++) {
        	b.write((i+1)+")"+" Fib("+this.numeri[i]+")="+this.risultati[i]+" impiegando "+this.elapsedtime[i]+" µs \n");
        }
    	b.write("Tempo totale impiegato: "+this.tempototale+" µs \n");
        b.flush();
	}
    
    /**
     * Comportamento del Manager
     */
    @Override
    public void cases(final CaseFactory c)
    {
    	MessageHandler k = (m) -> {
    		send(APP, "MANAGER"); //Il manager si dichiara ai client
    		return null;
    	};
    	c.define(START, k);
    	MessageHandler g = (m) -> {
    		if (m.getContent() instanceof Memoria) { //se arriva un oggetto di tipo Memoria (dal client)
    			memoria_manager=(Memoria)m.getContent(); //ne salvo una copia nella mia memoria
    			
    			numeri[contatoreclient-1]=memoria_manager.getnumero(); //salvo il numero nell'array numeri[]
    			risultati[contatoreclient-1]=memoria_manager.getrisultato(); //salvo il risultato nell'array risultati[]
    			elapsedtime[contatoreclient-1]=memoria_manager.getelapsedtime(); //salvo l'elapsedtime nell'array elapsedtime[]
    			
    			this.tempototale=this.tempototale+Long.parseLong(memoria_manager.getelapsedtime()); //aggiorno il tempo totale
    			if(this.getcontatoreclient()==this.getnclient()) { //Se ho ricevuto i dati da tutti i client
    				try {
    					System.out.println("MANAGER: creazione file output_Fibonacci.txt");
    					creafile(); //creazione file di output
    					//System.out.println("MANAGER: SHUTDOWN");
    					return Shutdown.SHUTDOWN; //shutdown dopo aver generato il file di output
    					} catch (IOException e) {
    						e.printStackTrace();
    						}
    				}
    			contatoreclient++; //aggiorno la variabile contatore
    			}
    		return null;
	  };
		  
	  c.define(ACCEPTALL, g);   
	    
  }
}
