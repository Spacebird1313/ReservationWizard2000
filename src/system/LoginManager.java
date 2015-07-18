package system;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

public class LoginManager
{
	private ReservationWizard2000 RW2000;
	private String configfileLocation = "data/config.login";
	
	public LoginManager(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
	}
	
	public boolean addLogin(String naam, String pass, int userLevel)										//Voeg een gebruiker toe aan het systeem
	{
		Properties prop = new Properties();
		File loginFile = new File(this.configfileLocation);
		
		try
		{
			prop.load(new FileInputStream(loginFile));
			if(prop.getProperty(naam) == null)
			{
				prop.setProperty(naam, encodePass(pass) + userLevel);
				prop.store(new FileOutputStream(this.configfileLocation), null);
			}
			else
			{
				if(RW2000.debugMode())
					System.out.println("Gebruiker: " + naam + " bestaat reeds.");
				return false;
			}
		}
		catch (IOException IOEX)
		{
			if (RW2000.debugMode())
				System.out.println("Kan config.login file niet aanpassen.");
			return false;
		}
		
		if(RW2000.debugMode())
			System.out.println("Gebruiker: " + naam + " is aangemaakt.");
		return true;
	}
	
	public boolean changePass(String naam, String oldPass, String newPass, int authenticationRequired)		//Wijzig het paswoord van een gebruiker
	{
		Properties prop = new Properties();
		File loginFile = new File(this.configfileLocation);
		String userPass = null;
		String userLevel = null;
		
		try
		{
			prop.load(new FileInputStream(loginFile));
			if(prop.getProperty(naam) != null)
			{
				userPass = prop.getProperty(naam).toString();
				userLevel = userPass.substring(userPass.length() - 1);
				userPass = userPass.substring(0, userPass.length() - 1);
				if(authenticationRequired == 0)
				{
					prop.setProperty(naam, encodePass(newPass) + userLevel);
					prop.store(new FileOutputStream(this.configfileLocation), null);
				}
				else if(userPass.equals(encodePass(oldPass)))
				{
					prop.setProperty(naam, encodePass(newPass) + userLevel);
					prop.store(new FileOutputStream(this.configfileLocation), null);
				}
				else
				{
					if(RW2000.debugMode())
						System.out.println("Het oude wachtwoord is incorrect.");
					return false;
				}
			}
			else
			{
				if(RW2000.debugMode())
					System.out.println("Gebruiker: " + naam + " bestaat niet.");
				return false;
			}
		}
		catch (IOException IOEX)
		{
			if (RW2000.debugMode())
				System.out.println("Kan config.login file niet aanpassen.");
			return false;
		}
		
		if(RW2000.debugMode())
			System.out.println("Wachtwoord met succes aangepast.");
		return true;
	}
	
	public boolean delLogin(String naam)																	//Verwijder een gebruiker uit het systeem
	{
		Properties prop = new Properties();
		File loginFile = new File(this.configfileLocation);
		
		try
		{
			prop.load(new FileInputStream(loginFile));
			if(prop.getProperty(naam) != null)
			{
				prop.remove(naam);
				prop.store(new FileOutputStream(this.configfileLocation), null);
			}
			else
			{
				if(RW2000.debugMode())
					System.out.println("Gebruiker: " + naam + " bestaat niet.");
				return false;
			}
		}
		catch (IOException IOEX)
		{
			if (RW2000.debugMode())
				System.out.println("Kan config.login file niet aanpassen.");	
			return false;
		}
		
		if(RW2000.debugMode())
			System.out.println("Gebruiker: " + naam + " is verwijderd.");
		return true;
	}
	
	public Gebruiker checkLogin(String naam, String pass)
	{
		Properties prop = new Properties();
		File loginFile = new File(this.configfileLocation);
		String userPass = null;
		String userLevel = null;
		
		try
		{
			prop.load(new FileInputStream(loginFile));
		}
		catch (IOException IOEX)
		{
			if (RW2000.debugMode())
				System.out.println("Kan config.login file niet laden.");	
			return null;
		}
		finally
		{
			if(prop.getProperty(naam) != null)
			{
				userPass = prop.getProperty(naam).toString();
				userLevel = userPass.substring(userPass.length() - 1);
				userPass = userPass.substring(0, userPass.length() - 1);
				
				if(userPass.equals(encodePass(pass)))
				{
					switch(Integer.parseInt(userLevel))
					{
						case 1 : if (RW2000.debugMode())
									System.out.println(naam + " is ingelogd als gebruiker.");
								 return new Gebruiker(naam);
						case 2 : if (RW2000.debugMode())
									System.out.println(naam + " is ingelogd als loketbediende.");
								 return new LoketBediende(naam);
						case 3 : if (RW2000.debugMode())
									System.out.println(naam + " is ingelogd als admin.");
								 return new Admin(naam);
						default : if (RW2000.debugMode())
									System.out.println("Inloggen gefaald. Er is geen UserLevel ingesteld voor " + naam + ".");
								 return null;
					}
				}
				else
				{
					if (RW2000.debugMode())
						System.out.println("Inloggen gefaald. Wachtwoord is incorrect.");		
					return null;
				}
			}
		}
		if (RW2000.debugMode())
			System.out.println("Inloggen gefaald. Kan gebruiker \"" + naam + "\" niet vinden.");
		return null;
	}
	
	public ArrayList<Gebruiker> getUsers()																		//Krijg alle gebruikers van het systeem
	{
		ArrayList<Gebruiker> gebruikers = new ArrayList<Gebruiker>();
		String userName, userLevel;
		Properties prop = new Properties();
		File loginFile = new File(this.configfileLocation);
		
		try
		{
			prop.load(new FileInputStream(loginFile));
			Iterator<Entry<Object, Object>> iterator = prop.entrySet().iterator();
			while(iterator.hasNext())
			{
				Map.Entry<Object, Object> gebruiker = (Entry<Object, Object>)iterator.next();
				
				userName = gebruiker.getKey().toString();
				userLevel = gebruiker.getValue().toString().substring(gebruiker.getValue().toString().length() - 1);
				switch(Integer.parseInt(userLevel))
				{
					case 1 : gebruikers.add(new Gebruiker(userName));
							 break;
					case 2 : gebruikers.add(new LoketBediende(userName));
							 break;
					case 3 : gebruikers.add(new Admin(userName));
							 break;
					default : break;
				}
			}
		}
		catch (IOException IOEX)
		{
			if (RW2000.debugMode())
				System.out.println("Kan config.login file niet aanpassen.");
			return null;
		}
		if(RW2000.debugMode())
			System.out.println("Users met succes geladen.");
		return gebruikers;
	}
	
	private String encodePass(String pass)
	{
		return new String(DatatypeConverter.printBase64Binary(pass.getBytes()));
	}
}
