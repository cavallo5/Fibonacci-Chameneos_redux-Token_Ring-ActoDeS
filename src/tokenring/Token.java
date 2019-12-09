package tokenring;

import java.io.*;

public class Token {
	int contatore;
	
	public Token(){
		this.contatore=0;
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
}