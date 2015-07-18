package system;

import java.util.*;

public class ReservationWizard2000
{
	private boolean debug;
	private Gebruiker loggedIn = null;
	private LoginManager loginM;
	private ReservatieManager reservatieM;
	private BetalingsManager betalingM;
	private AutoPlaatsManager autoPlaatsM;
	
	public ReservationWizard2000(boolean debugMode)
	{		
		this.debug = debugMode;
		
		if (this.debugMode())	
			System.out.println("Debug modus actief: " + Calendar.getInstance().getTime());
		
		this.loginM = new LoginManager(this);
		this.reservatieM = new ReservatieManager(this);
		this.betalingM = new BetalingsManager(this);
		this.autoPlaatsM = new AutoPlaatsManager();
		this.filesLaden();
	}
	
	public boolean debugMode()
	{
		return debug;
	}
	
	//Inlog - methodes
	public boolean inloggen(String naam, String pass)
	{
		this.loggedIn = loginM.checkLogin(naam, pass);
		
		if(this.loggedIn != null)
			return true;
		else
			return false;
	}
	
	public void uitloggen()
	{
		this.loggedIn = null;
	}
	
	public boolean gebruikerToevoegen(String naam, String pass, int userLevel)
	{
		return this.loginM.addLogin(naam, pass, userLevel);
	}
	
	public void gebruikerVerwijderen(String naam)
	{
		this.loginM.delLogin(naam);
	}
	
	public Gebruiker getCurrentLogedIn()
	{
		return loggedIn;
	}
	
	public boolean wachtwoordWijzigen(String naam, String oudPass, String nieuwPass, int authenticatieVereist)
	{
		return this.loginM.changePass(naam, oudPass, nieuwPass, authenticatieVereist);
	}
	
	public ArrayList<Gebruiker> gebruikersOpvragen()
	{
		return this.loginM.getUsers();
	}
	
	//Zaal - methodes
	public void zaalAanmaken(String zaalNaam, String locatie, int aantalRijen, int maxAantalPlaatsen)
	{
		reservatieM.zaalToevoegen(new Zaal(zaalNaam, locatie, aantalRijen, maxAantalPlaatsen));
	}
	
	public void zaalToevoegen(Zaal zaal)
	{
		reservatieM.zaalToevoegen(zaal);
	}
	
	public ArrayList<Zaal> zalenOpvragen()
	{
		return reservatieM.zalenLaden();
	}
	
	public void zaalVerwijderen(Zaal zaal)
	{
		reservatieM.zaalVerwijderen(zaal);
	}
	
	//Voorstelling - methodes
	public void voorstellingAanmaken(Zaal zaal, String naam, String tijd, int duur)
	{
		reservatieM.voorstellingToevoegen(new Voorstelling(zaal, naam, tijd, duur));
	}
	
	public ArrayList<Voorstelling> voorstellingenOpvragen()
	{
		return reservatieM.voorstellingenLaden();
	}
	
	public void voorstellingVerwijderen(Voorstelling voorstelling)
	{
		reservatieM.voorstellingVerwijderen(voorstelling);
	}

	//Opstart - methodes
	public void filesLaden()
	{
		if(reservatieM.loadZalen() == false)															//Controleer of zalen reeds bestaan en laad deze in
		{
			if(debugMode())
				System.out.println("Zalenbestand wordt aangemaakt.");
			reservatieM.saveZalen();
		}
		
		if(reservatieM.loadReservaties() == false)														//Controleer of reservaties reeds bestaan en laad deze in
			if(debugMode())
				System.out.println("Reservatiebestand wordt aangemaakt.");
			reservatieM.saveReservaties();
	}
	
	//Opslaan - methodes
	public void saveZalen()
	{
		reservatieM.saveZalen();
	}
	
	public void saveReservaties()
	{
		reservatieM.saveReservaties();
	}
	
	public Observable saveLoadObserverOpvragen()
	{
		return this.reservatieM.observerableOpvragen();
	}
	
	public void shutdown()
	{
		reservatieM.saveZalen();
		reservatieM.saveReservaties();
		
		if(debugMode())
		{
			System.out.println("Systeem wordt afgesloten.");
			System.out.println("Debug modus geëindigd: " + Calendar.getInstance().getTime());
		}
		
		System.exit(0);
	}
	
	//Betalingsterminal - methodes
	public boolean betalen(Reservatie reservatie, Double betaaldBedrag)
	{
		if(betalingM.betalen(reservatie, betaaldBedrag))
		{
			this.saveReservaties();
			return true;
		}
		return false; 
	}
	
	public void rekeningAfdrukken(Voorstelling voorstelling, Reservatie reservatie)
	{
		betalingM.rekeningAfdrukken(voorstelling, reservatie);
	}
	
	//Plaatsselector - methodes
	public Plaats[] automatischePlaatsSelector(Voorstelling voorstelling, int aantal)
	{
		if(debugMode())
			System.out.println("Automatische plaatstoekenning: " + aantal + " plaatsen.");
		return autoPlaatsM.automatischeReservatie(voorstelling, aantal);
	}
}
