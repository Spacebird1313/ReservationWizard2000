package system;

public class Gebruiker
{
	private String gebruikersnaam;
	private int userLevel;
	
	public Gebruiker(String naam)
	{
		this.gebruikersnaam = naam;
		this.userLevel = 1;
	}
	
	public Gebruiker(String naam, int userLevel)
	{
		this.gebruikersnaam = naam;
		this.userLevel = userLevel;
	}
	
	public String naamOpvragen()
	{
		return gebruikersnaam;
	}
	
	public int userLevelOpvragen()
	{
		return userLevel;
	}
}
