package system;

import java.io.Serializable;

public class Plaats implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int beschikbaar = 0;							//Onbeschikbaar = 0; Vrij = 1; Bezet = 2
	private double prijs = 0;
	private int plaatsNummer = 0;
	private int rijNummer = 0;
	
	public Plaats(int beschikbaarheid, double prijs, int rijNummer, int plaatsNummer)
	{
		this.beschikbaar = beschikbaarheid;
		this.prijs = prijs;
		this.rijNummer = rijNummer;
		this.plaatsNummer = plaatsNummer;
	}
	
	public void wijzigBeschikbaarheid(int beschikbaarheid)
	{
		this.beschikbaar = beschikbaarheid;
	}
	
	public void prijsInstellen(double prijs)
	{
		this.prijs = prijs;
	}
	
	public int beschikbaarheidOpvragen()
	{
		return beschikbaar;
	}
	
	public double prijsOpvragen()
	{
		return prijs;
	}
	
	public int plaatsOpvragen()
	{
		return plaatsNummer;
	}
	
	public int rijOpvragen()
	{
		return rijNummer;
	}
}
