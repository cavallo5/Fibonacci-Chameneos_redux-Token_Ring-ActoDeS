package chameneos_redux;

public class Colore
{
	public int colore_charmeneox;
	
	private static final int intblu = 0 ;
	public static final Colore BLU = new Colore(intblu); 
	private static final int introsso = 1 ;
	public static final Colore ROSSO = new Colore(introsso);
	private static final int intgiallo = 2; 
	public static final Colore GIALLO = new Colore(intgiallo); 
	
	private Colore(int valore) 
	{
		colore_charmeneox = valore %3 ;
	}
	
	public Colore Complementare(Colore C) {
		if ( colore_charmeneox == C. colore_charmeneox )
		{ 
			return new Colore(colore_charmeneox ); 
		}
		else return new Colore( 3 - colore_charmeneox - C.colore_charmeneox ); 
	}
	
	public String toString (){
		if ( colore_charmeneox == intblu )
		{
			return "BLU";
		}
		else if ( colore_charmeneox == introsso ) 
		{
			return "ROSSO"; 
		}
		else //intgiallo
		{
			return "GIALLO"; 
		}
	}
}
