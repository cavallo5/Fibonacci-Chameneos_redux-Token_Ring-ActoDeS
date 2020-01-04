package chameneos_redux;

/**
 * Comportamento del centro commerciale 
 * @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
 */

//Librerie utilizzate
import java.util.Hashtable;
import it.unipr.sowide.actodes.actor.Behavior;
import it.unipr.sowide.actodes.actor.CaseFactory;
import it.unipr.sowide.actodes.actor.MessageHandler;
import it.unipr.sowide.actodes.registry.Reference;


public final class CentroCommerciale extends Behavior{
	private static final long serialVersionUID = 1L;
	/**
	 * Hashtable in cui salvo gli chameneos che vengono al centro commerciale e le loro reference
	 */
	Hashtable<Integer, Reference> h = new Hashtable<Integer, Reference>();
	/**
	 * Integer che conta il numero chameneos nel centro commerciale
	 */
	Integer numero_chameneos;
	/**
	 * interi usati per scegliere gli chameneos da far incontrare
	 */
	int x,y;
	/**
	 * Reference degli chameneos che si devono incontrare 
	 */
	Reference ChameneosA, ChameneosB;
	/**
	 * Numero di chameneos che possono entrare contemporaneamente nel centro commerciale
	 */
	static int chameneos_totali=6; 


	/**
	  * Funzione che sceglie il primo chameneos da far incontrare
	  * @return il numero dello chameneos da far incontrare
	 */
	public int primochameneos() {
		//Scelta primo chameneos
		Boolean cham1=false;
		while(!cham1) {
			x=(int)(Math.random()*6); //Numero casuale da 0 a 6
			if(h.containsKey(x)) {
				cham1=true;
			}
		}
		return x;
	}
	
	/**
	  * Funzione che sceglie il secondo chameneos da far incontrare
	  * @param x il numero dello chameneos già scelto
	  * @return il numero dello chameneos da far incontrare
	 */
	public int secondochameneos(int x) {
		//Scelta secondo chameneos
		Boolean cham2=false;
		while(!cham2) {
			y=(int)(Math.random()*6); //Numero casuale da 0 a 6
			if(h.containsKey(y) && y!=x) {
				cham2=true;
			}
		}
		return y;
	}
	

	/** {@inheritDoc} **/
	@Override
	public void cases(final CaseFactory c)
	{
		MessageHandler k = (m) -> {
		this.numero_chameneos=0; //all'apertura del centro commerciale, ci sono 0 chameneos
		System.out.println("IL CENTRO COMMERCIALE E' APERTO");
		send(APP, "CENTROCOMMERCIALE_APERTO"); //il centro commerciale è aperto		  
		return null;
	};

	    c.define(START, k);
		  
	   MessageHandler g = (m) -> {
	      	if (m.getContent() instanceof Colore) { //se arriva uno chameneos all'interno del centro commerciale
	      		h.put(numero_chameneos,(Reference)m.getSender()); //salvataggio nell'hashtable
	      		numero_chameneos++; //incremento il numero di chameneos all'interno del centro commerciale		
	      	}
	      	if(numero_chameneos==chameneos_totali) { //se ho raggiunto il limite massimo
	      		while(h.size()!=0) {
	      			//Incontro tra 2 chameneos casuali
	      			x=primochameneos();
	      			y=secondochameneos(x);
	      			ChameneosA=h.get(x);
	      			ChameneosB=h.get(y);
	      			
	      			send(ChameneosA,ChameneosB); //invio allo chameneosA la reference dello chameneosB
	      			send(ChameneosB,ChameneosA); //invio allo chameneosB la reference dello chameneosA
	      			h.remove(x); //rimuovo dall'hashtable la reference dello chameneosA
	      			h.remove(y); //rimuovo dall'hashtable la reference dello chameneosB
	      			//2 chameneos sono usciti dal centro commerciale
	      			numero_chameneos--;
	      			numero_chameneos--;
	      		}
	      	}	      	
		   return null;
	   };
	
	   c.define(ACCEPTALL, g);
	  }
}

