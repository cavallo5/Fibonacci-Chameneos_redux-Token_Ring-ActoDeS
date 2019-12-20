package tokenring;

import java.math.BigInteger;
import tokenring.Token;
import tokenring.Pacchetto;
import tokenring.Memoria;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;




public final class Nodo extends Behavior
{
  private static final long serialVersionUID = 1L;
  //int ID;
  //Reference indirizzo;
  Reference indirizzo_nodosuccessivo;
  Reference indirizzo_gestore;
  private Memoria memoria; //contiene ID e indirizzo
  Pacchetto pacchetto;
  private int M=500; //Numero client
  int x=0;
  Token token;
  long end_time, time_used=0;

  
  
  
  
  public Nodo(Integer i)
  {
	  memoria=new Memoria(); //ogni file ha un oggetto Memoria nel quale incapsula i dati
	  this.memoria.ID=i;
  }
  
  public Reference getindirizzo_nodosuccessivo(){
	  return this.indirizzo_nodosuccessivo;
  }


  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
	  
	  //SE I CLIENT DEVONO FARE QUALCOSA ALL'INIZIO, SCRIVERE QUI:
	  
	  MessageHandler k = (m) -> {
			this.memoria.setindirizzo(this.getReference());
			System.out.println("NODO: Indirizzo nodo: "+this.memoria.getID()+" : "+this.memoria.getindirizzo()+"");

			return null;
	  };

	 
    c.define(START, k);
	
    
    MessageHandler g = (m) -> {
    	//Arriva messaggio di inizializzazione dal gestore
    	if (m.getContent()=="GESTORE") {
    		this.indirizzo_gestore=m.getSender();
			//System.out.println("NODO: Indirizzo gestore: "+this.indirizzo_gestore+"");
			
			//mando al Gestore il mio ID e il mio indirizzo
			 send(this.indirizzo_gestore, memoria);		  
		
			 return null;	
    	}
    	
    	//Gestore mi fornisce l'indirizzo del nodo successivo per creare la token ring
    	if (m.getContent() instanceof Reference) {
    		this.indirizzo_nodosuccessivo=(Reference) m.getContent();
			//System.out.println("Verifica: SONO IL NODO" +this.memoria.getindirizzo()+" e sono collegato a: "+this.indirizzo_nodosuccessivo+" ");
			
			return null;	
    	}
    	
    	//Nodo riceve Token
    	if (m.getContent() instanceof Token) {
			System.out.println("NODO "+this.memoria.getID()+": ho ricevuto il TOKEN");
			//Scelta casuale destinatario 
    	    x = (int)(Math.random()*M +1); //Tra 1 e M
    	    //Caso in cui sceglie se stesso come destinatario
    	    if(x==this.memoria.getID()) {
    	    	x++;
    	    	if(x==(M+1)){
    	    		x=1;
    	    	}
    	    }
			System.out.println("ID destinatario scelto:" +x);
			token=(Token) m.getContent();
			this.token.incrementatoken();
			pacchetto=new Pacchetto(this.memoria.getID(), x, token,System.nanoTime());
			System.out.println("NODO "+this.memoria.getID()+": invio il pacchetto al nodo successivo "+this.getindirizzo_nodosuccessivo());
			send(this.getindirizzo_nodosuccessivo(),pacchetto);
			
			return null;	
    	}
    	
    	if (m.getContent() instanceof Pacchetto) {
			System.out.println("NODO "+this.memoria.getID()+": ho ricevuto il pacchetto");
			pacchetto=(Pacchetto) m.getContent();
			System.out.println("ANALISI PACCHETTO:");
			System.out.println("INDIRIZZO MITTENTE: "+pacchetto.getID_mittente());
			System.out.println("INDIRIZZO DESTINATARIO: "+pacchetto.getID_destinatario());
			System.out.println("MESSAGGIO: "+pacchetto.getmessaggio());

			
			//SE IL PACCHETTO E' ARRIVATO A DESTINAZIONE
			if(pacchetto.getID_destinatario()==this.memoria.getID()) {
				System.out.println("NODO "+this.memoria.getID()+": pacchetto arrivato a destinazione");
	            end_time= System.nanoTime(); //tempo in ns dopo aver che il messaggio è arrivato a destinazione
	            time_used= (long) ((end_time - this.pacchetto.getstart_time())/1000F); //tempo impiegato in 􏱇µs tra invio e risposta del server
				System.out.println("TEMPO IMPIEGATO PER INVIARE IL PACCHETTO: "+time_used+"");
	            

				//SE IL TOKEN HA RAGGIUNTO T PASSI, NON FARE LA SEND, ALTRIMENTI FAI LA SEND AL NODO SUCCESSIVO
				//Il token deve continuare a girare
				if(!pacchetto.gettoken().controllapassi()) { //RESTITUISCE TRUE SE HA RAGGIUNTO IL LIMITE DI PASSI
					System.out.println("TOKEN PASSA AL NODO SUCCESSIVO");
					send(this.indirizzo_nodosuccessivo, pacchetto.gettoken());
				}
				else {
					System.out.println("TOKEN HA TERMINATO I PASSI");
				}
				//send(this.indirizzo_nodosuccessivo, pacchetto.gettoken());
				
			}else {
				//SE IL PACCHETTO NON E' ARRIVATO A DESTINAZIONE
				System.out.println("NODO "+this.memoria.getID()+": pacchetto non arrivato a destinazione");
				if(!pacchetto.gettoken().controllapassi()) { //RESTITUISCE TRUE SE HA RAGGIUNTO IL LIMITE DI PASSI
					System.out.println("TOKEN PASSA AL NODO SUCCESSIVO");
					send(this.getindirizzo_nodosuccessivo(),pacchetto);
				}
				else {
					System.out.println("IL TOKEN HA RAGGIUNTO IL LIMITE DI PASSI");
					
					
					 		  

				}
			}
    	}
    	
    	
        return null;
	  };
    
	    
	c.define(ACCEPTALL, g);
 	
    
  }
}
