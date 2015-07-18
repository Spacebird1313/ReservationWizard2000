package system;

public class LoketBediende extends Gebruiker
{
	public LoketBediende(String naam) 
	{
		super(naam, 2);
	}
	
	public LoketBediende(String naam, int userLevel) 
	{
		super(naam, userLevel);
	}
}
