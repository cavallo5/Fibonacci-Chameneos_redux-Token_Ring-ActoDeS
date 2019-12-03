package fibonacci;

import java.io.*;

public class Memoria {
	int numeroclient=10;
	String numero;
	String risultato;
	//String[] elapsedtime;
	//float tempototale;
	
	public Memoria(){
		this.numero="";
		this.risultato="";
		//this.elapsedtime= new String[a];
	}
	
	public String getnumero() {
		return this.numero;
	}
	public String getrisultato() {
		return this.risultato;
	}
	public void setnumero(String a) {
		this.numero=a;
	}
	public void setrisultato(String a) {
		this.risultato=a;
	}
	


}
