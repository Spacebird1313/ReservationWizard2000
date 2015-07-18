package system;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class Reservatie implements Serializable
{

	private static final long serialVersionUID = 1L;
	private ArrayList<Plaats> plaatsen = new ArrayList<Plaats>();
	private String reservatieNaam = null;
	private int reservatieNummer = 0;
	private boolean betaalt = false;
	
	public Reservatie(String reservatieNaam, int reservatieNummer)
	{
		this.reservatieNaam = reservatieNaam;
		this.reservatieNummer = reservatieNummer;
		this.betaalt = false;
	}
	
	public void voegPlaatsToe(Plaats plaats)	//Vast toewijzen van een plaats aan een reservatie
	{
		plaats.wijzigBeschikbaarheid(2);
		this.plaatsen.add(plaats);
	}
	
	public void plaatsToewijzen(Plaats plaats)	//Tijdelijk toekennen van een plaats aan een reservatie
	{
		this.plaatsen.add(plaats);
	}
	
	public void plaatsVerwijderen(Plaats plaats)
	{
		plaats.wijzigBeschikbaarheid(1);
		this.plaatsen.remove(plaats);
	}
	
	public void betaalStatusAanpassen(boolean status)
	{
		this.betaalt = status;
	}
	
	public void reservatieNaamAanpassen(String naam)
	{
		this.reservatieNaam = naam;
	}
	
	public String reservatieNaamOpvragen()
	{
		return reservatieNaam;
	}
	
	public boolean betaalStatusOpvragen()
	{
		return betaalt;
	}
	
	public double rekeningOpvragen()
	{
		double rekening = 0;
		Iterator<Plaats> iterator = plaatsen.iterator();
		while(iterator.hasNext())
		{
			Plaats plaats = iterator.next();
			rekening = rekening + plaats.prijsOpvragen();
		}
		return rekening;
	}
	
	public int reservatieNummerOpvragen()
	{
		return reservatieNummer;
	}
	
	public ArrayList<Plaats> plaatsenOpvragen()
	{
		return plaatsen;
	}
}
