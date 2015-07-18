package system;

import java.io.Serializable;

public class Zaal implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Plaats[][] plaatsen;
	private String locatie = null;
	private String zaalNaam = null;
	private int rijen = 0;
	private int plaatsenPerRij = 0;

	public Zaal(int aantalRijen, int maxAantalPlaatsen)
	{
		this.zaalNaam = "";
		this.locatie = "";
		this.plaatsen = new Plaats[aantalRijen][maxAantalPlaatsen];
		this.rijen = aantalRijen;
		this.plaatsenPerRij = maxAantalPlaatsen;
		//Vul de zaal met stoelen
		for (int i = 0; i < this.rijenOpvragen(); i++)
		{
			for	(int j = 0; j < this.plaatsenPerRij(); j++)
			{	
				this.plaatsToevoegen(1 , 20, i, j);
			}
		}
	}
	
	public Zaal(String zaalNaam, String locatie, int aantalRijen, int maxAantalPlaatsen)
	{
		this.zaalNaam = zaalNaam;
		this.locatie = locatie;
		this.plaatsen = new Plaats[aantalRijen][maxAantalPlaatsen];
		this.rijen = aantalRijen;
		this.plaatsenPerRij = maxAantalPlaatsen;
		//Vul de zaal met stoelen
		for (int i = 0; i < this.rijenOpvragen(); i++)
		{
			for	(int j = 0; j < this.plaatsenPerRij(); j++)
			{	
				this.plaatsToevoegen(1 , 20, i, j);
			}
		}
	}
	
	public Zaal(String zaalNaam, String locatie, int aantalRijen, int maxAantalPlaatsen, double prijs)
	{
		this.zaalNaam = zaalNaam;
		this.locatie = locatie;
		this.plaatsen = new Plaats[aantalRijen][maxAantalPlaatsen];
		this.rijen = aantalRijen;
		this.plaatsenPerRij = maxAantalPlaatsen;
		//Vul de zaal met stoelen
		for (int i = 0; i < this.rijenOpvragen(); i++)
		{
			for	(int j = 0; j < this.plaatsenPerRij(); j++)
			{	
				this.plaatsToevoegen(1 , prijs, i, j);
			}
		}
	}
	
	public void plaatsToevoegen(int beschikbaarheid, double prijs, int rijNummer, int plaatsNummer)
	{
		this.plaatsen[rijNummer][plaatsNummer] = new Plaats(beschikbaarheid, prijs, rijNummer, plaatsNummer);
	}
	
	public void wijzigBeschikbaarheid(int rijNummer, int plaatsNummer, int beschikbaarheid)
	{
		plaatsen[rijNummer][plaatsNummer].wijzigBeschikbaarheid(beschikbaarheid);
	}
	
	public void zaalEigenschappenWijzigen(String zaalNaam, String locatie)
	{
		this.zaalNaam = zaalNaam;
		this.locatie = locatie;
	}
	
	public Plaats plaatsOpvragen(int rij, int plaats)
	{
		return plaatsen[rij][plaats];
	}
	
	public Plaats[][] zaalLayoutOpvragen()
	{
		return plaatsen;
	}
	
	public String zaalNaamOpvragen()
	{
		return zaalNaam;
	}
	
	public String locatieOpvragen()
	{
		return locatie;
	}
	
	public int rijenOpvragen()
	{
		return rijen;
	}
	
	public int plaatsenPerRij()
	{
		return plaatsenPerRij;
	}
	
	public int beschikbaarheidOpvragen()									//Geef het aantal beschikbare plaatsen terug
	{
		int vrijePlaatsen = 0;
		for (int i = 0; i < this.rijen; i++)
		{
			for	(int j = 0; j < this.plaatsenPerRij; j++)
			{	
				if (plaatsen[i][j] != null)
				{							
					switch(plaatsen[i][j].beschikbaarheidOpvragen())
					{
						case 1:
							vrijePlaatsen++;
							break;
						default:
							break;	
					}
				}
			}
		}
		return vrijePlaatsen;
	}
	
	public int aantalPlaatsenOpvragen()										//Geef het totaal aantal plaatsen terug
	{
		int aantalPlaatsen = 0;
		for (int i = 0; i < this.rijen; i++)
		{
			for	(int j = 0; j < this.plaatsenPerRij; j++)
			{	
				if (plaatsen[i][j] != null)
				{							
					switch(plaatsen[i][j].beschikbaarheidOpvragen())
					{
						case 1:
							aantalPlaatsen++;
							break;
						case 2:
							aantalPlaatsen++;
							break;
						default:
							break;	
					}
				}
			}
		}
		return aantalPlaatsen;
	}
}
