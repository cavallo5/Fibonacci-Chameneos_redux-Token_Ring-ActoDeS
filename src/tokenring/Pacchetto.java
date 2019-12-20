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

public class Pacchetto {
	private Integer ID_mittente;
	private Integer ID_destinatario;
	private String messaggio;
	private Token token;
	long start_time;

	
	
	public Pacchetto(Integer a, Integer b,Token c, long d){
		this.ID_mittente=a;
		this.ID_destinatario=b;
		this.messaggio="Hello";
		this.token=c;
		this.start_time=d;
	}
	
	public Integer getID_mittente() {
		return this.ID_mittente;
	}
	public Integer getID_destinatario() {
		return this.ID_destinatario;
	}
	public String getmessaggio() {
		return this.messaggio;
	}
	public Token gettoken() {
		return this.token;
	}
	public long getstart_time() {
		return this.start_time;
	}
	


}
