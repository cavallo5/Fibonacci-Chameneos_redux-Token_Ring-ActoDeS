package fibonacci;

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
  private int nclient=10;
  FileWriter w;
  String[] numeri=new String[nclient];
  String[] risultati=new String[nclient];
  String[] elapsedtime = new String[nclient];
  int contatoreclient=1;
  Memoria memoria_manager=new Memoria();
  long tempototale=0;



    int getnclient() {
    	return nclient;
    }
    
    public void creafile() throws IOException{
		w=new FileWriter("output_Fibonacci.txt");
		BufferedWriter b;
        b=new BufferedWriter (w);
        b.write("Fibonacci ActoDeS \n");
        for(int i=0;i<nclient;i++) {
        	b.write((i+1)+")"+" Il numero di Fibonacci di "+this.numeri[i]+" è "+this.risultati[i]+" impiegando "+this.elapsedtime[i]+" µs \n");
        }
    	b.write("Tempo totale impiegato: "+this.tempototale+" µs \n");
        
        b.flush();
	}
    

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  
	  //Invio al server di x
	  MessageHandler k = (m) -> {
		  System.out.println("Il master si dichiara ai client");
		  send(APP, "MANAGER");		  
		  return null;
	  };

	 
    c.define(START, k);

	MessageHandler g = (m) -> {
		  if (m.getContent() instanceof Memoria) {
			  memoria_manager=(Memoria)m.getContent();
			  //System.out.println(memoria_manager.getnumero());
			  //System.out.println(memoria_manager.getrisultato());
			  numeri[contatoreclient-1]=memoria_manager.getnumero();
			  risultati[contatoreclient-1]=memoria_manager.getrisultato();
			  elapsedtime[contatoreclient-1]=memoria_manager.getelapsedtime();
			  tempototale=tempototale+(memoria_manager.gettempototale());
			  //System.out.println(contatoreclient-1);

			  if(contatoreclient==nclient) {
              	try {
      			    System.out.println("MANAGER: creazione file di output");
					creafile();
					System.out.println("MANAGER: SHUTDOWN");
		            return Shutdown.SHUTDOWN; 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
			  contatoreclient++;
		  }
		  return null;
	  };
		  
	  c.define(ACCEPTALL, g);   
	    
  }
}
