package tokenring;

//import java.io.*;
//import java.math.BigInteger;
//import it.unipr.sowide.actodes.actor.Behavior;
//import it.unipr.sowide.actodes.actor.CaseFactory;
//import it.unipr.sowide.actodes.actor.MessageHandler;
//import it.unipr.sowide.actodes.actor.Shutdown;
//import it.unipr.sowide.actodes.interaction.Status;
import it.unipr.sowide.actodes.registry.Reference;
//import it.unipr.sowide.actodes.service.naming.content.Bind;
//import it.unipr.sowide.actodes.service.naming.content.Lookup;
//import it.unipr.sowide.actodes.service.naming.content.Subscribe;

public class Memoria {
	Integer ID;
	Reference indirizzo;
	
	
	
	public Memoria(){
		this.ID=0;
	}
	
	public int getID() {
		return this.ID;
	}
	public Reference getindirizzo() {
		return this.indirizzo;
	}
	
	public void setID(int a) {
		this.ID=a;
	}
	
	public void setindirizzo(Reference a) {
		this.indirizzo=a;
	}


}
