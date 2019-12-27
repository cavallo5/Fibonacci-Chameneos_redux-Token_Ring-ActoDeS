package chameneos_redux;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;

import java.util.concurrent.TimeUnit;

//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;
import chameneos_redux.Colore;
import chameneos_redux.IdChameneos;


public final class Chameneos extends Behavior{
	private static final long serialVersionUID = 1L;

	static Colore [] Colori = {Colore.GIALLO,Colore.BLU,Colore.ROSSO};
	private IdChameneos id;
	private Colore coloreA, coloreB;
	private int numeroviaggi;
	public int contatoreviaggi;
	Reference indirizzocharmeneos;
	Reference indirizzocharmeneosincontrato;
	Reference indirizzocentrocommerciale;
	int x=0;
	long start_time, end_time, time_used=0;

	
	
	//public Chameneos(IdChameneos id , Colore c, int d) 
	public Chameneos(IdChameneos id , Colore b, int c) 
	{
		this.id=id; 
		this.coloreA=b;
		this.numeroviaggi=c;
	}
	
	public Reference getindirizzocharmeneosincontrato() {
		return this.indirizzocharmeneosincontrato;
	}
	
	public Reference getindirizzocentrocommerciale() {
		return this.indirizzocentrocommerciale;
	}
	public Reference getindirizzocharmeneos() {
		return this.indirizzocharmeneos;
	}
	
	public void setindirizzocharmeneos(Reference a) {
		this.indirizzocharmeneos=a;
	}
	
	private void Messaggio(String Mess) 
	{
		System.out. println ( "(" + id . toString () + ") Sono " +
		coloreA.toString () + " e " + Mess);
	}
	private void Riposo()
	{
		Messaggio("sono a casa");
	}
	private void Vadoalcentrocommerciale() 
	{
		Messaggio("vado nel centro commerciale");
	}
	private void Mutazione()
	{
		Messaggio("sto avendo una mutazione dopo aver incontrato un'altra creatura di colore "+coloreB);
		this.coloreA = coloreA.Complementare(coloreB);
		Messaggio("torno a casa dopo aver fatto la mutazione");
	}
	
	
	/** {@inheritDoc} **/
	 @Override
	 public void cases(final CaseFactory c)
	 {
		  MessageHandler k = (m) -> {
			  setindirizzocharmeneos(this.getReference());
			  this.contatoreviaggi=0;
			  Riposo();
	    	  //Messaggio("sono di colore "+this.coloreA+ " e sono a casa");

			  return null;
		  };

        start_time=System.nanoTime(); //tempo in ns
        System.out.println(start_time);
	    c.define(START, k);

	    MessageHandler g = (m) -> {
	    	//Arriva messaggio di inizializzazione dal centrocommerciale
	    	if (m.getContent()=="CENTROCOMMERCIALE_APERTO") {
	    		this.indirizzocentrocommerciale=m.getSender();
	    		Vadoalcentrocommerciale();
				send(this.indirizzocentrocommerciale, coloreA);	
	    	}
	    	
	    	//CHAMENEOS SI INCONTRA CON IL SECONDO CHAMENEOS
	    	if (m.getContent() instanceof Reference && m.getSender()==indirizzocentrocommerciale) {
	    		indirizzocharmeneosincontrato=(Reference) m.getContent();
	    		send(indirizzocharmeneosincontrato,coloreA);
	    	}
	    		
	    		
	    	if (m.getContent() instanceof Colore && m.getSender()==indirizzocharmeneosincontrato){
	    		this.coloreB=(Colore)m.getContent();
	    		Mutazione();  
	    		this.contatoreviaggi++;
	    		Riposo();
	    		if(contatoreviaggi<numeroviaggi) {
		    		Vadoalcentrocommerciale();
					send(this.indirizzocentrocommerciale, coloreA);	
	    		}else {
	    			Messaggio("vado a dormire");
	                end_time= System.nanoTime(); //tempo in ns dopo aver ricevuto la risposta del server
	                time_used= (long) ((end_time - start_time)/1000F); //tempo impiegato in 􏱇µs tra invio e risposta del server
	               Messaggio("ho impiegato: "+time_used+" ns");
	    			return Shutdown.SHUTDOWN;
	    		}
	    	}
	    	
	    	
	    	return null;
	    };
	    	
	    	
	    	
	    	
	    	
	    
	    
	    /*
	    MessageHandler g = (m) -> {
	    	//Arriva messaggio di inizializzazione dal centrocommerciale
	    	if (m.getContent()=="CENTROCOMMERCIALE_APERTO") {
	    		this.indirizzocentrocommerciale=m.getSender();
	    		Vadoalcentrocommerciale();
	    		this.casa=false;
				send(this.indirizzocentrocommerciale, coloreA);	
	    	}
	    	//Nel centro commerciale ho incontrato una creatura ed effettuo la mutazione	    	

	    	
	    	
	    
	    	if(m.getContent() instanceof Colore && m.getSender()==indirizzocentrocommerciale) {
	    		this.coloreB=(Colore)m.getContent();
	    		Mutazione();  
	    		this.contatoreviaggi++;
	    		this.casa=true;

	    			    		
	    		//Probabilità del 50% di riposarsi o andare al centro commerciale
	    		x=(int)(Math.random()*2); //Numero casuale da 0 a 1
	    		if(x==0) { //RIMANGO A CASA
		    		Riposo();
	    		}else if(x==1) { //Vado al centro commerciale se devo fare ancora viaggi
	    			if(contatoreviaggi<numeroviaggi) {
		    			Vadoalcentrocommerciale();
			    		this.casa=false;
						send(this.indirizzocentrocommerciale, coloreA);
		    		}else {
		    			Messaggio("ho terminato la spesa, rimango a casa");
			    		this.casa=true;
		    			return Shutdown.SHUTDOWN;
		    		}
	    		}
	    	}
	    	
	    	if (m.getContent()=="CENTROCOMMERCIALE_LIBERO" && this.casa==true && this.contatoreviaggi<this.numeroviaggi) {
	    		System.out.println("CAZZO IN CULO");	
	    		Vadoalcentrocommerciale();
				send(this.indirizzocentrocommerciale, coloreA);

	    	}
	  
	    	
	    	
	    	
	        return null;
		  };
	    */
		c.define(ACCEPTALL, g);
	 
	    
	  }

}
