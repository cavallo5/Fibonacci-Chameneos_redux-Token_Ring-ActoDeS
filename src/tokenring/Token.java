package tokenring;

import java.io.*;

public class Token {
	int contatore;
	int limitepassi;
	
	public Token(){
		this.contatore=0;
		this.limitepassi=100;
	}
	
	public int getcontatore() {
		return this.contatore;
	}
	public void setcontatore(int a) {
		this.contatore=a;
	}
	public void incrementatoken() {
		this.contatore++;
	}
	
	public boolean controllapassi() {
		if(this.contatore==this.limitepassi) {
			return true;
		}
		else return false;
	}
}