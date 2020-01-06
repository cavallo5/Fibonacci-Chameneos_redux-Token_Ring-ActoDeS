package tokenring;

/**
 * Nodo 
 * @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
 */

import tokenring.Token;
import tokenring.Pacchetto;
import tokenring.Memoria;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.registry.Reference;


public final class Nodo extends Behavior
{
  private static final long serialVersionUID = 1L;
  /**
   *  Reference del nodo successivo
   */
  Reference indirizzo_nodosuccessivo;
  /**
   *  Reference del gestore
   */
  Reference indirizzo_gestore;
  /**
   *  Memoria del nodo
   */
  private Memoria memoria; //contiene ID e indirizzo
  /**
   *  Pacchetto 
   */
  Pacchetto pacchetto;
  /**
   *  Estremo superiore dell'intervallo dei numeri scelto
   */
  private int M=500; //Anche pari al numero di client
  /**
   *  Intero in cui salvo il numero casuale scelto 
   */
  int x=0;
  /**
   *  Variabile d'appoggio in cui salvo il token in arrivo
   */
  Token token;
  /**
   *  Variabili per il calcolo dell'elapsed time
   */
  long end_time, time_used=0;

  
  /**
   *  Costruttore
   *  @param i id del nodo
   */
  public Nodo(Integer i)
  {
	  memoria=new Memoria(); //ogni file ha un oggetto Memoria nel quale incapsula i dati
	  this.memoria.ID=i;
  }
  
  /**
   * Restituisce la reference del nodo successivo
   * @return l'indirizzo del nodo successivo della token ring
   */
  public Reference getindirizzo_nodosuccessivo(){
	  return this.indirizzo_nodosuccessivo;
  }


  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {	  
	  MessageHandler k = (m) -> {
			this.memoria.setindirizzo(this.getReference()); //Nella memoria ogni client salva la sua reference
			System.out.println("NODO: Indirizzo nodo: "+this.memoria.getID()+" : "+this.memoria.getindirizzo()+"");
			return null;
	  };
    c.define(START, k);
	
    
    MessageHandler g = (m) -> {
    	if (m.getContent()=="GESTORE") { //Arriva messaggio di inizializzazione dal gestore
    		this.indirizzo_gestore=m.getSender(); //Salvo l'indirizzo del gestore
			 send(this.indirizzo_gestore, memoria); //mando al Gestore il mio ID e il mio indirizzo
			 return null;	
    	}
    	
    		if (m.getContent() instanceof Reference) { //Gestore mi fornisce l'indirizzo del nodo successivo per creare la token ring
    		this.indirizzo_nodosuccessivo=(Reference) m.getContent();			
			return null;	
    	}
    	
    	if (m.getContent() instanceof Token) { //Nodo riceve Token
    		System.out.println("NODO "+this.memoria.getID()+": ho ricevuto il Token");
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
			this.token.incrementatoken(); //incremento il contatore nel token
			pacchetto=new Pacchetto(this.memoria.getID(), x, token,System.nanoTime()); //creo pacchetto
			System.out.println("NODO "+this.memoria.getID()+": invio il pacchetto al nodo successivo ");
			send(this.getindirizzo_nodosuccessivo(),pacchetto);
			
			return null;	
    	}
    	
    	if (m.getContent() instanceof Pacchetto) { //Nodo riceve il pacchetto
			System.out.println("NODO "+this.memoria.getID()+": ho ricevuto il pacchetto");
			pacchetto=(Pacchetto) m.getContent();
			System.out.println("ANALISI PACCHETTO:");
			System.out.println("Indirizzo mittente: "+pacchetto.getID_mittente());
			System.out.println("Indirizzo destinatario: "+pacchetto.getID_destinatario());
			System.out.println("Messaggio: "+pacchetto.getmessaggio());

			
			//SE IL PACCHETTO E' ARRIVATO A DESTINAZIONE
			if(pacchetto.getID_destinatario()==this.memoria.getID()) {
				System.out.println("NODO "+this.memoria.getID()+": PACCHETTO ARRIVATO A DESTINAZIONE");
				
				//Calcolo elapsed time
				end_time= System.nanoTime(); //tempo in ns dopo aver che il messaggio è arrivato a destinazione
	            time_used= (long) ((end_time - this.pacchetto.getstart_time())/1000F); //tempo impiegato in 􏱇µs tra invio e risposta del server
				System.out.println("Tempo impiegato per inviare il pacchetto: "+time_used+" μs");
	            
				//SE IL TOKEN HA RAGGIUNTO T PASSI, NON FARE LA SEND, ALTRIMENTI FAI LA SEND AL NODO SUCCESSIVO
				//Il token deve continuare a girare
				if(!pacchetto.gettoken().controllapassi()) { //RESTITUISCE TRUE SE HA RAGGIUNTO IL LIMITE DI PASSI
					System.out.println("Token passa al nodo successivo");
					send(this.indirizzo_nodosuccessivo, pacchetto.gettoken()); //Invio solo il token al nodo successivo
				}
				else {
					System.out.println("TOKEN HA TERMINATO I PASSI");
				}				
			}else { //SE IL PACCHETTO NON E' ARRIVATO A DESTINAZIONE
				System.out.println("NODO "+this.memoria.getID()+": pacchetto non arrivato a destinazione");
				if(!pacchetto.gettoken().controllapassi()) { //RESTITUISCE TRUE SE HA RAGGIUNTO IL LIMITE DI PASSI
					System.out.println("Invio pacchetto al nodo successivo");
					send(this.getindirizzo_nodosuccessivo(),pacchetto);
				}
				else {
					System.out.println("Il token ha raggiunto il limite di passi"); //Token muore
				}
			}
    	}
    	
        return null;
	  };
    
	    
	c.define(ACCEPTALL, g);
 	
    
  }
}
