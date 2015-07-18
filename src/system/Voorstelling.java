package system;

import java.io.Serializable;
import java.util.ArrayList;

public class Voorstelling implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Zaal zaal;
	private ArrayList<Reservatie> reservaties = new ArrayList<Reservatie>();
	private String naam;
	private String tijd;
	private int duur;
	
	public Voorstelling()
	{
		this.zaal = null;
		this.naam = "";
		this.tijd = "";
		this.duur = 0;
	}
	public Voorstelling(Zaal zaal, String naam, String tijd, int duur)
	{
		this.zaal = zaal;
		this.naam = naam;
		this.tijd = tijd;
		this.duur = duur;
	}
	
	public void reservatieToevoegen(Reservatie reservatie)
	{
		this.reservaties.add(reservatie);
	}
	
	public void reservatieVerwijderen(Reservatie reservatie)
	{
		this.reservaties.remove(reservatie);
	}
	
	public void voorstellingsEigenschappenWijzigen(String naam, String tijd, int duur)
	{
		this.naam = naam;
		this.tijd = tijd;
		this.duur = duur;
	}
	
	public void zaalWijzigen(Zaal zaal)
	{
		this.reservaties = new ArrayList<Reservatie>();
		this.zaal = zaal;
	}
	
	public String voorstellingsNaamOpvragen()
	{
		return naam;
	}
	
	public String tijdOpvragen()
	{
		return tijd;
	}
	
	public int duurOpvragen()
	{
		return duur;
	}
	
	public Zaal zaalOpvragen()
	{
		return zaal;
	}
	
	public ArrayList<Reservatie> reservatieOpvragen()
	{
		return reservaties;
	}
}
