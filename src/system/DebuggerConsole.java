package system;

import GUI.*;

import java.util.ArrayList;

import javax.swing.JFrame;

public class DebuggerConsole
{	
	private boolean runTest = false;													//Voer gedefinieerde testen uit.
	private boolean forceWindowOpen = false;											//Open een GUI-frame gedefinieerd door forceWindow
	
	private ReservationWizard2000 RW2000;
	
	public DebuggerConsole(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		
		if(runTest)
		{
			System.out.println("Test: ReservationWizard2000 - V1.0");
			
			//Uit te voeren testen
			
			this.gebruikerVerwijderen();
			this.gebruikerLogin();
			this.gebruikerAanmaken();
			this.wachtwoordWijzigen();
			//this.gebruikerVerwijderen();
			
			this.voorstellingVerwijderen();
			
			this.zalenVerwijderen();
			this.zaalAanmaken();
			//this.zalenOpvragen();
			
			this.voorstellingAanmaken();
			this.voorstellingOpvragen();
			this.creerLayoutTestzaal();
			Voorstelling temp = this.voorstellingOpvragenVoorTest(2);
			Plaats[] plaatsen = this.creeerDynamischeReservatie(temp, 410);
			this.selectieReserveren(temp, plaatsen);
			this.voorstellingOpvragen();
			this.betalen(temp, temp.reservatieOpvragen().get(0), temp.reservatieOpvragen().get(0).rekeningOpvragen());
			System.out.print("");
			/*
			this.reserveren();
			this.voorstellingOpvragen();
			//this.reservatieVerwijderen();
			//this.voorstellingOpvragen();
			*/
			/*
			this.alleReservatieVerwijderen();	//verwijderd alle reservaties van een voorstelling
			this.voorstellingOpvragen();
			*/
			/*
			this.zalenOpvragen();
			this.zalenVerwijderen();
			this.zalenOpvragen();
			this.voorstellingVerwijderen();
			this.voorstellingOpvragen();
			*/	
		}
		
		if(forceWindowOpen)
		{
			//Open een GUI-frame
			JFrame forceWindow = new ZaalWijzigWindow(RW2000);
			forceWindow.setTitle("!=DEBUG-MODE=! " +forceWindow.getTitle());
			forceWindow.setVisible(true);
		}
			
	}
	
	//LoginManager - testmethodes
	public void gebruikerLogin()
	{
		System.out.println("Test: gebruikerLogin: Login als gebruiker: Admin correct, Admin foutief, Oliver onbestaand, Loket correct");
		RW2000.inloggen("Admin", "Wachtwoord");
		RW2000.inloggen("Admin", "Nice try Oliver");
		RW2000.inloggen("Oliver", "Ken ik je wel?");
		RW2000.inloggen("Loket", "Wachtwoord");
	}
	
	public void gebruikerAanmaken()
	{
		System.out.println("Test: gebruikerAanmaken: Maak nieuwe gebruikers aan: Anna - Cold, Elsa - Frozen, Olaf - Snowman, Admin - Wachtwoord (Userlevel 3), Admin - Wachtwoord (Userlevel 1)");
		RW2000.gebruikerToevoegen("Anna", "Cold", 1);
		RW2000.gebruikerToevoegen("Elsa", "Frozen", 3);
		RW2000.gebruikerToevoegen("Olaf", "Snowman", 2);
		RW2000.gebruikerToevoegen("Admin", "Wachtwoord", 3);
		RW2000.gebruikerToevoegen("Admin", "Wachtwoord", 1);
	}
	
	public void gebruikerVerwijderen()
	{
		System.out.println("Test: gebruikerVerwijderen: verwijder de nieuw aangemaakte gebruikers: Anna, Elsa, Olaf, Jos onbestaand");
		RW2000.gebruikerVerwijderen("Anna");
		RW2000.gebruikerVerwijderen("Elsa");
		RW2000.gebruikerVerwijderen("Olaf");
		RW2000.gebruikerVerwijderen("Jos");
	}
	
	public void wachtwoordWijzigen()
	{
		System.out.println("Test: wachtwoordWijzigen: wijzig de wachtwoord van de gebruikers: Anna - Frozen (Correct), Elsa - Cold (Incorrect), Jos onbestaand, Olaf - Christmas (authenticatie vrij)");
		RW2000.wachtwoordWijzigen("Anna", "Cold", "Frozen", 1);
		RW2000.wachtwoordWijzigen("Elsa", "Fout", "Cold", 1);
		RW2000.wachtwoordWijzigen("Jos", "Probeer maar", "Dat is pech hebben!", 1);
		RW2000.wachtwoordWijzigen("Olaf", "", "Christmas", 0);
	}
	
	//Zaal - testmethodes
	public void zaalAanmaken()
	{
		System.out.println("Test: zaalAanmaken: Een nieuwe zalen toevoegen: Artesis - Antwerpen, GameCenter - Sint-Lenaarts, Club69 - Grobbendonk.");
		RW2000.zaalAanmaken("Artesis", "Antwerpen", 20, 30);
		RW2000.zaalAanmaken("GameCenter", "Sint-Lenaarts", 10, 10);
		RW2000.zaalAanmaken("Club69", "Grobbendonk", 10, 15);
		RW2000.zaalAanmaken("TestZaal", "DebugSpace", 20, 49);
	}
	
	public void zalenOpvragen()
	{
		System.out.println("Test: zalenOpvragen: De zaalnaam en locatie van de zalen worden opgevraagd.");
		ArrayList<Zaal> zalen = RW2000.zalenOpvragen();
		@SuppressWarnings("unused")
		Plaats plaats = null;
		for(int i = 0; i < zalen.size(); i++)
		{
			System.out.println("Er zijn momenteel " + zalen.size() + " zalen.");
			System.out.print(zalen.get(i).zaalNaamOpvragen() + " - ");
			System.out.println(zalen.get(i).locatieOpvragen());
			System.out.println("Zaal Layout:");
			// Enkel voor vierkant zalen (creeert een diagonale rij van onbeschikbare plaatsen)	
			/*for (int j = 0; j < zalen.get(i).rijenOpvragen(); j++)
			{
				plaats = zalen.get(i).plaatsOpvragen(j, j);
				plaats.wijzigBeschikbaarheid(0);
			}
			*/
			zaalLayoutOpvragen(zalen.get(i));
		}
	}
	
	public void zalenVerwijderen()
	{
		System.out.println("Test: zalenVerwijderen: De zalen worden verwijderd.");
		ArrayList<Zaal> zalen = RW2000.zalenOpvragen();
		int size = RW2000.zalenOpvragen().size();
		for(int i = 0; i < size; i++)
		{
			System.out.println("Verwijder zaal: " + zalen.get(i).zaalNaamOpvragen());
			RW2000.zaalVerwijderen(RW2000.zalenOpvragen().get(0));
		}
	}
	
	//Voorstelling - testmethodes
	public void voorstellingAanmaken()
	{
		System.out.println("Test: voorstellingAanmaken: Een nieuwe voorstelling toevoegen: Blackjack Toernooi - GameCenter - 1 uur - maandag 9 december.");
		System.out.println("+ Een nieuwe voorstelling toevoegen: Dries&Co on Tour - GameCenter - 1 uur - maandag 10 december.");
		RW2000.voorstellingAanmaken(RW2000.zalenOpvragen().get(1), "Blackjack Toernooi", "09-12-2013 14:50", 60);
		RW2000.voorstellingAanmaken(RW2000.zalenOpvragen().get(1), "Dries&Co on Tour", "10-12-2013 19:20", 60);
		RW2000.voorstellingAanmaken(RW2000.zalenOpvragen().get(3), "TestVoorstelling", "NOW", 0);
		System.out.println("Wijzig eigenschappen de zaaleigenschappen van Dries&Co on Tour met: Sportpaleis - Antwerpen ");
		//vanaf nu zo wijzigingen doorvoeren
		for (int i = 0; i < RW2000.voorstellingenOpvragen().size(); i++)
		{
			if (RW2000.voorstellingenOpvragen().get(i).zaalOpvragen().zaalNaamOpvragen().equals("GameCenter"))
			{
				RW2000.voorstellingenOpvragen().get(i).zaalOpvragen().zaalEigenschappenWijzigen("Sportpaleis", "Antwerpen");
				RW2000.saveReservaties();
			}
		}
		RW2000.saveReservaties();
		RW2000.saveZalen();
	}
	
	public void voorstellingOpvragen()
	{
		System.out.println("Test: voorstellingOpvragen: De voorstellingsgegevens worden opgevraagd.");
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		for(int i = 0; i < voorstellingen.size(); i++)
		{
			System.out.print(voorstellingen.get(i).voorstellingsNaamOpvragen() + " - ");
			System.out.print(voorstellingen.get(i).zaalOpvragen().zaalNaamOpvragen() + " - ");
			System.out.print(voorstellingen.get(i).duurOpvragen() + " min - ");
			System.out.println(voorstellingen.get(i).tijdOpvragen());
			zaalLayoutOpvragen(voorstellingen.get(i).zaalOpvragen());
		}
	}
	
	public void voorstellingVerwijderen()
	{
		System.out.println("Test: voorstellingVerwijderen: De voorstellingen worden verwijderd.");
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		int size = RW2000.voorstellingenOpvragen().size();
		for(int i = 0; i < size; i++)
		{
			System.out.println("Verwijder voorstelling: " + voorstellingen.get(i).voorstellingsNaamOpvragen());
			RW2000.voorstellingVerwijderen(RW2000.voorstellingenOpvragen().get(0));
		}
	}
	
	public Plaats[][] zaalLayoutOpvragen(Zaal zaal)
	{
			Plaats plaats = null;
		
				for (int i = 0; i < zaal.rijenOpvragen(); i++)
				{
					for	(int j = 0; j < zaal.plaatsenPerRij(); j++)
					{	
						plaats = zaal.plaatsOpvragen(i, j);
						if (plaats != null)
						{							
							switch(plaats.beschikbaarheidOpvragen())
							{
							case 0: System.out.print(" "); break;
							case 1: System.out.print("A"); break;
							case 2: System.out.print("U"); break;
							}
						}
						else
						{
							System.out.print("X");
						}
					}
					System.out.println();
				}
		return zaal.zaalLayoutOpvragen();
	}
	
	//Reservatie - testmethodes
	@SuppressWarnings("unused")
	private void reserveren() {
		System.out.println("Test: Reserveren.");
		Reservatie reservatie = new Reservatie("Janssens", 1);
		//selecteer een voorstelling
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		Voorstelling voorstelling = voorstellingen.get(1);
		//je hebt dan de zaal ook m.b.v. voorstelling.zaalOpvragen()
		//dus dan vraag je van de zaal de plaatsen
		Plaats[][] plaatsen = voorstelling.zaalOpvragen().zaalLayoutOpvragen();
		//Dan moet er een methode zijn (nog te schrijven) die enkel als de plaats die geselecteerd wordt beschikbaar is deze toevoegd aan de reservaties en de beschikbaarheid verandert.
		//in dit voorbeeld plaats 2, 2
		if (plaatsen[2][2].beschikbaarheidOpvragen() == 1 && plaatsen[4][6].beschikbaarheidOpvragen() == 1)
		{	
			reservatie.voegPlaatsToe(plaatsen[2][2]);
			reservatie.voegPlaatsToe(plaatsen[4][6]);
			voorstelling.reservatieToevoegen(reservatie);
			voorstelling.zaalOpvragen().wijzigBeschikbaarheid(2, 2, 2);
			voorstelling.zaalOpvragen().wijzigBeschikbaarheid(4, 6, 2);
			RW2000.saveReservaties();
		}
	}
	
	@SuppressWarnings("unused")
	private void reservatieVerwijderen() {
		System.out.println("Test: Reservatie verwijderen.");
		//selecteer een voorstelling
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		Voorstelling voorstelling = voorstellingen.get(1);
		//Vraag een lijst van de reservaties en selecteer hieruit de gewenste
		ArrayList<Reservatie> lijst = voorstelling.reservatieOpvragen();
		//Verander dan de status van alle stoelen in de reservatie (hier reservatie 1)
			Reservatie temp = lijst.get(0);
			ArrayList<Plaats> plaatsen = temp.plaatsenOpvragen();
			for (int j = 0; j < plaatsen.size(); j++)
				{
					Plaats plaats = plaatsen.get(j);
					voorstelling.zaalOpvragen().wijzigBeschikbaarheid(plaats.rijOpvragen(), plaats.plaatsOpvragen(), 1);
				}
			//Zeer belangerijk dat dit op het einde van de lus gebeurt.
			voorstelling.reservatieVerwijderen(lijst.get(0));	//Let erop dat deze nummer hetzelfde is als die bovenaan
			RW2000.saveReservaties();
	}
	
	@SuppressWarnings("unused")
	private void alleReservatieVerwijderen() {
		System.out.println("Test: Reservatie verwijderen.");
		//selecteer een voorstelling
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		Voorstelling voorstelling = voorstellingen.get(1);
		//Vraag een lijst van de reservaties en selecteer hieruit de gewenste
		ArrayList<Reservatie> lijst = voorstelling.reservatieOpvragen();
		//Verander dan de status van alle stoelen in alle reservatie
		System.out.println(lijst.size());
			for (int i = 0; i < lijst.size(); i++)
			{
				Reservatie temp = lijst.get(i);
				ArrayList<Plaats> plaatsen = temp.plaatsenOpvragen();
				for (int j = 0; j < plaatsen.size(); j++)
					{
						Plaats plaats = plaatsen.get(j);
						voorstelling.zaalOpvragen().wijzigBeschikbaarheid(plaats.rijOpvragen(), plaats.plaatsOpvragen(), 1);
					}
				//Zeer belangerijk dat dit op het einde van de lus gebeurt.
				voorstelling.reservatieVerwijderen(lijst.get(i));
				RW2000.saveReservaties();
			}
	}
	
	private Plaats[] creeerDynamischeReservatie(Voorstelling voorstelling, int aantal)
	{
		System.out.println("Test: Dynamisch reservatie.");
		Plaats[][] plaatsen = voorstelling.zaalOpvragen().zaalLayoutOpvragen();
		Plaats[] selectie = new Plaats[aantal];
		ArrayList<ArrayList<Plaats>> bin = new ArrayList<ArrayList<Plaats>>(); 
		Plaats center;
		ArrayList<Plaats> chunk = new ArrayList<Plaats>();
		int checked[][] = new int[plaatsen.length][plaatsen[1].length];
		int doNotExecute = 0;
		for (int i = 0; i < voorstelling.zaalOpvragen().rijenOpvragen(); i++)
		{
			for (int j = 0; j < voorstelling.zaalOpvragen().plaatsenPerRij(); j++)
			{
				if (plaatsen[i][j].beschikbaarheidOpvragen() == 1)
				{
					if (checked[i][j] == 1)	
					{
						doNotExecute++;
					}
					if (doNotExecute == 0) 
					{
						center = plaatsen[i][j];
						chunk = new ArrayList<Plaats>();
						this.checkArea(plaatsen, center, chunk, checked);
						bin.add(chunk);
					}
					doNotExecute = 0;
				}
				
			}
		}
		int seq = 0;
		//check voor aantal plaatsen op eenzelfde rij op volgorde, beginnende van de voorste rij
		System.out.print(voorstelling.zaalOpvragen().rijenOpvragen() + " "+ voorstelling.zaalOpvragen().plaatsenPerRij());
		for (int i = voorstelling.zaalOpvragen().rijenOpvragen()-1; i >= 0 ; i--)
		{
			for (int j = 0; j < voorstelling.zaalOpvragen().plaatsenPerRij(); j++)
			{
				if (plaatsen[i][j].beschikbaarheidOpvragen() == 1)
				{
					if (seq >= aantal)
						return selectie;
					selectie[seq] = plaatsen[i][j];
					seq++;
				}
				else
					seq = 0;
			}
			seq = 0;
		}
		seq = 0;
		
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		ArrayList<ArrayList<Plaats>> temp = new ArrayList<ArrayList<Plaats>>();
		int theOne = -1, size = 999999999;
		int bestFit = -1;
		for (int i = 0; i < bin.size(); i++)
		{
			if (bin.get(i).size() >= aantal)
			{
				sizes.add(bin.get(i).size());
				temp.add(bin.get(i));
			}
		}
		for (int i = 0; i < sizes.size(); i++)
		{
			if (sizes.get(i) == aantal)
				theOne = i;
			if (sizes.get(i) < size)
			{
				bestFit = i;
				size = sizes.get(i);
			}
		}
		if (theOne != -1)
		{
			temp.get(theOne);
			for (int i = 0; i< temp.get(theOne).size(); i++)
			{
				if (seq >= aantal)
					return selectie;
				selectie[seq] =  temp.get(theOne).get(i);
				seq++;
			}
		}
		if (bestFit != -1)
		{
			temp.get(bestFit);
			for (int i = 0; i <= aantal; i++)
			{
				if (seq >= aantal)
					return selectie;
				selectie[seq] = temp.get(bestFit).get(i);
				seq++;
			}
		}
		
		Integer[][] sortedIndexes = new Integer[2][bin.size()];
		for (int i = 0; i < bin.size(); i++)
		{
			sizes.add(bin.get(i).size());
			temp.add(bin.get(i));
		}
		for (int i = 0; i < bin.size(); i++) {
			sortedIndexes[0][i] = i;
		}
		sortedIndexes[1] = sizes.toArray(sortedIndexes[1]);
		bubbleSort(sortedIndexes);
		
		for (int i = 0; i < bin.size(); i++) 
		{
			for (int k = 0; k < sortedIndexes[1][i]; k++)
			{
				temp.get(sortedIndexes[0][i]);
				if (seq >= aantal)
					return selectie;
				selectie[seq] = temp.get(sortedIndexes[0][i]).get(k);
				seq++;
			}
			if (seq >= aantal)
				return selectie;
		}
		return null;		
	}
	
	private void bubbleSort(Integer arr[][])
	{
		int swap1 = 0;
		int swap2 = 0;
		
		for (int i = 0; i < ( arr[0].length - 1 ); i++) {
		      for (int j = 0; j < arr[0].length - i - 1; j++) {
		        if (arr[1][j] < arr[1][j+1])
		        {
		          swap1 = arr[0][j];
		          swap2 = arr[1][j];
		          arr[0][j] = arr[0][j+1];
		          arr[1][j] = arr[1][j+1];
		          arr[0][j+1] = swap1;
		          arr[1][j+1] = swap2;
		        }
		      }
		    }
	}
	
	private void checkArea(Plaats plaatsen[][], Plaats  center, ArrayList<Plaats> selectie, int[][] checked)
	{
		int i = center.rijOpvragen();
		int j = center.plaatsOpvragen();
		
		if (checked[i][j] != 1)
		{
			selectie.add(plaatsen[i][j]);
				checked[i][j] = 1;
					if (center.plaatsOpvragen() >= 1)
						if (checked[i][j-1] != 1)
							if (plaatsen[i][j-1].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i][j-1], selectie, checked);
							}
			
					if (center.rijOpvragen() >= 1)	
						if (checked[i-1][j] != 1)
							if (plaatsen[i-1][j].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i-1][j], selectie, checked);
							}
				
					if (center.plaatsOpvragen() < plaatsen[center.rijOpvragen()].length-1)
						if (checked[i][j+1] != 1)
							if (plaatsen[i][j+1].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i][j+1], selectie, checked);
							}
	
					if (center.rijOpvragen() < plaatsen.length-1)
						if (checked[i+1][j] != 1)
							if (plaatsen[i+1][j].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i+1][j], selectie, checked);
							}
			
		}
	}
	
	private void selectieReserveren(Voorstelling voorstelling, Plaats[] plaatsen)
	{
		for (int i = 0; i < plaatsen.length; i++)
		{
			voorstelling.zaalOpvragen().wijzigBeschikbaarheid(plaatsen[i].rijOpvragen(), plaatsen[i].plaatsOpvragen(), 2);
		}
		RW2000.saveReservaties();
		
		//vul naam van reservatie in voor de plaatsVoorstellen methode uit te voeren
		Reservatie reservatie = new Reservatie("PlaatsVoorstel", 2);
			for (int i = 0; i < plaatsen.length; i++) 
			{
				Plaats plaats = plaatsen[i];
				int j = plaats.rijOpvragen();
				int k = plaats.plaatsOpvragen();
				reservatie.voegPlaatsToe(plaats);				
				voorstelling.zaalOpvragen().wijzigBeschikbaarheid(j, k, 2);
			}
		voorstelling.reservatieToevoegen(reservatie);
		RW2000.saveReservaties();
	}
	
	public Voorstelling voorstellingOpvragenVoorTest(int index)
	{
		System.out.println("Test: voorstellingOpvragen: De voorstellingsgegevens worden opgevraagd.");
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		return voorstellingen.get(index);
	}
	
	public void creerLayoutTestzaal()
	{
		ArrayList<Voorstelling> voorstellingen = RW2000.voorstellingenOpvragen();
		Voorstelling voorstelling = voorstellingen.get(2);
		for (int i = 0; i < voorstelling.zaalOpvragen().rijenOpvragen(); i++)
		{
			for (int j = 0; j < voorstelling.zaalOpvragen().plaatsenPerRij(); j++)
			{
				if (i <= 2)
					if (j <= 7 || j >= 41)
						voorstelling.zaalOpvragen().wijzigBeschikbaarheid(i, j, 0);				
				if (i == 7 || i == 8)
					voorstelling.zaalOpvragen().wijzigBeschikbaarheid(i, j, 0);
				if (j == 23 || j == 24 || j == 25)
					voorstelling.zaalOpvragen().wijzigBeschikbaarheid(i, j, 0);
				if (i > 13)
					if (j >= 9 && j <= 14 || j >= 21 && j <= 27 || j >= 34 && j <= 39)
						voorstelling.zaalOpvragen().wijzigBeschikbaarheid(i, j, 0);
			}
		}
		RW2000.saveReservaties();
	}
	
	private boolean betalen(Voorstelling voorstelling, Reservatie reservatie, Double bedrag)
	{
		RW2000.rekeningAfdrukken(voorstelling, reservatie);
		return RW2000.betalen(reservatie, bedrag);
	}
}
