package chameneos_redux;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;


public final class CentroCommerciale extends Behavior{
	private static final long serialVersionUID = 1L;
	Hashtable<Integer, Reference> h = new Hashtable<Integer, Reference>();
	Integer numero_chameneos; //numero chameneos nel centro commerciale
	int x,y;
	Reference ChameneosA, ChameneosB;
	//int [] casuali = {0,1,2,3,4,5,6};



	

	//private Colore ColoreA,ColoreB;
	//int numero_chameneos; //numero chameneox nel centro commerciale
	//Reference ChameneosA, ChameneosB;

	 
	  
	  /** {@inheritDoc} **/
	  @Override
	  public void cases(final CaseFactory c)
	  {
		  MessageHandler k = (m) -> {
			  this.numero_chameneos=0;
			  System.out.println("IL CENTRO COMMERCIALE E' APERTO");
			  send(APP, "CENTROCOMMERCIALE_APERTO");		  
			  return null;
			  
	  };

	    c.define(START, k);
		  
		  		
	    
	    
	    
	   MessageHandler g = (m) -> {
		   //Appena mi arrivano gli chameneos, ne salvo la Reference e il colore
	      	if (m.getContent() instanceof Colore) {
	      		h.put(numero_chameneos,(Reference)m.getSender());
	      		numero_chameneos++; //uno chameneos nel centro commerciale		
	      	}
	      	if(numero_chameneos==6) {
	      		System.out.println("TUTTI GLI CHAMENEOS SONO VENUTI NEL CENTRO COMMERCIALE");
	      		while(h.size()!=0) {
	      			x=(int)(Math.random()*6); //Numero casuale da 0 a 6
	      			y=(int)(Math.random()*6); //Numero casuale da 0 a 6
	      			if(h.containsKey(x) && h.containsKey(y) && x!=y) {
	      					System.out.println("Ricerca elemento "+x+" nell'hashtable");
	      					System.out.println("Ricerca elemento "+y+" nell'hashtable");

	      					ChameneosA=h.get(x);
	      					ChameneosB=h.get(y);
	      					send(ChameneosA,ChameneosB);
	      					send(ChameneosB,ChameneosA);
	      					h.remove(x);
	      					h.remove(y);
	      					numero_chameneos--;
	      					numero_chameneos--;

	      				}
	    		System.out.println("La dimensione dell'hash table è: "+h.size());
	            System.out.println("The set is: " + h.toString()); 
	      		}
	      	}
	      	
	      	
	      	
		   return null;
	   };
	
	   c.define(ACCEPTALL, g);
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    /*
	    MessageHandler g = (m) -> {
	    	

	      	//ARRIVA IL SECONDO CHAMENEOS
	      	if (m.getContent() instanceof Colore && numero_chameneos==1) { 
	    		this.numero_chameneos++;
	    		//System.out.println("Il numero di chameneos nel centro commerciale é: "+numero_chameneos);
	    		this.ChameneosB=m.getSender();
	    		this.ColoreB=(Colore)m.getContent();
	    		//System.out.println("E' arrivato uno chameneos di colore "+this.ColoreB+"");
	    	}
	      	
	      	//ARRIVA IL PRIMO CHAMENEOS
	    	if (m.getContent() instanceof Colore && numero_chameneos==0) { 
	    		this.numero_chameneos++;
	    		//System.out.println("Il numero di chameneos nel centro commerciale é: "+numero_chameneos);
	    		this.ChameneosA=m.getSender();
	    		this.ColoreA=(Colore)m.getContent();
	    		//System.out.println("E' arrivato uno chameneos di colore "+this.ColoreA+"");
	    	}
	    	
	    	if(numero_chameneos==2) {
	    		//INVIO I COLORI AI 2 CHAMENEOS PER LA MUTAZIONE
				send(this.ChameneosA, ColoreB);
				send(this.ChameneosB, ColoreA);
				numero_chameneos=0;
	    	}			  
	    	
			  return null;
		  };
		  
		  c.define(ACCEPTALL, g);
	     */
			 
	  }
}

